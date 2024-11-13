package sh.rime.demo.service;

import cn.hutool.core.util.IdUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import sh.rime.demo.enmus.ExceptionEnum;
import sh.rime.reactor.commons.exception.ServerException;
import sh.rime.reactor.s3.core.OssTemplate;
import sh.rime.reactor.s3.props.OssProperties;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.mockito.Mockito.*;

class FileServiceTest {

    private FileService fileService;

    @Mock
    private OssTemplate ossTemplate;

    @Mock
    private OssProperties ossProperties;

    @Mock
    private FilePart filePart;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        fileService = new FileService(ossTemplate, ossProperties);
    }

    @Test
    void testUploadSuccess() throws IOException {
        String bucketName = "test-bucket";
        String customDomain = "https://example.com";
        String fileName = "test_file.txt";
        String fileContent = "Hello, World!";
        String uuid = IdUtil.fastUUID();
        String objName = uuid + ".txt";

        when(ossProperties.getBucketName()).thenReturn(bucketName);
        when(ossProperties.getCustomDomain()).thenReturn(customDomain);
        when(filePart.filename()).thenReturn(fileName);
        when(filePart.content()).thenReturn(Flux.just(toDataBuffer(fileContent.getBytes(StandardCharsets.UTF_8))));
        when(IdUtil.fastSimpleUUID()).thenReturn(uuid);

        doNothing().when(ossTemplate).putObject(eq(bucketName), eq(objName), any(InputStream.class));

        Mono<String> result = fileService.upload(Mono.just(filePart));

        StepVerifier.create(result)
                .expectNext(customDomain + "/" + objName)
                .verifyComplete();

        verify(ossTemplate, times(1)).putObject(eq(bucketName), eq(objName), any(InputStream.class));
    }


    @Test
    void testUploadFileTooLarge() {
        byte[] largeContent = new byte[1024 * 1024 * 21]; // 21 MB

        when(filePart.content()).thenReturn(Flux.just(toDataBuffer(largeContent)));

        Mono<String> result = fileService.upload(Mono.just(filePart));

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof ServerException
                        && throwable.getMessage().equals(ExceptionEnum.RESOURCE_FILE_TOO_LARGE.message()))
                .verify();
    }

    @Test
    void testUploadFileFailed() throws IOException {
        String bucketName = "test-bucket";
        String customDomain = "https://example.com";
        String fileName = "test_file.txt";
        String fileContent = "Hello, World!";
        String uuid = IdUtil.fastUUID();
        String objName = uuid + ".txt";

        when(ossProperties.getBucketName()).thenReturn(bucketName);
        when(ossProperties.getCustomDomain()).thenReturn(customDomain);
        when(filePart.filename()).thenReturn(fileName);
        when(filePart.content()).thenReturn(Flux.just(toDataBuffer(fileContent.getBytes(StandardCharsets.UTF_8))));
        mockStatic(IdUtil.class);
        when(IdUtil.fastSimpleUUID()).thenReturn(uuid);

        // 模拟上传失败，抛出 IOException
        doThrow(new IOException("Upload failed")).when(ossTemplate).putObject(eq(bucketName), eq(objName), any(InputStream.class));

        // 调用方法
        Mono<String> result = fileService.upload(Mono.just(filePart));

        // 验证异常结果
        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof ServerException
                        && throwable.getMessage().equals(ExceptionEnum.UPLOAD_FILE_FAILED.message()))
                .verify();

        verify(ossTemplate, times(1)).putObject(eq(bucketName), eq(objName), any(InputStream.class));
    }

    private DataBuffer toDataBuffer(byte[] bytes) {
        DataBuffer buffer = new DefaultDataBufferFactory().allocateBuffer(bytes.length);
        buffer.write(bytes);
        return buffer;
    }
}
