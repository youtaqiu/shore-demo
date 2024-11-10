package sh.rime.demo.service;

import cn.hutool.core.util.IdUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import sh.rime.reactor.commons.bean.R;
import sh.rime.reactor.s3.core.OssTemplate;
import sh.rime.reactor.s3.props.OssProperties;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static sh.rime.demo.enmus.ExceptionEnum.RESOURCE_FILE_TOO_LARGE;
import static sh.rime.demo.enmus.ExceptionEnum.UPLOAD_FILE_FAILED;
import static sh.rime.reactor.core.util.Asserts.isTrue;


/**
 * @author youta
 **/
@Service
@RequiredArgsConstructor
public class FileService {

    private final OssTemplate ossTemplate;
    private final OssProperties ossProperties;

    public Mono<String> upload(Mono<FilePart> filePartMono) {
        return filePartMono.flatMap(filePart -> DataBufferUtils.join(filePart.content())
                .flatMap(dataBuffer -> {
                    var bufferSize = dataBuffer.readableByteCount();
                    isTrue(bufferSize < 1024 * 1024 * 20, RESOURCE_FILE_TOO_LARGE);
                    byte[] bytes = new byte[bufferSize];
                    dataBuffer.read(bytes);
                    DataBufferUtils.release(dataBuffer);
                    InputStream stream = new ByteArrayInputStream(bytes);
                    String originalFilename = filePart.filename();
                    String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
                    String objName = IdUtil.fastSimpleUUID() + extension;
                    try {
                        ossTemplate.putObject(ossProperties.getBucketName(), objName, stream);
                    } catch (IOException e) {
                        return R.error(UPLOAD_FILE_FAILED);
                    }
                    return Mono.justOrEmpty(ossProperties.getCustomDomain().concat("/").concat(objName));
                }));
    }

}