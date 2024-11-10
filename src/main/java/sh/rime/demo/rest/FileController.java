package sh.rime.demo.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import sh.rime.demo.service.FileService;
import sh.rime.reactor.commons.annotation.Anonymous;
import sh.rime.reactor.commons.bean.R;
import sh.rime.reactor.log.annotation.Log;


/**
 * @author youta
 **/
@Tag(name = "file", description = "文件")
@RestController
@RequiredArgsConstructor
@RequestMapping("file")
public class FileController {

    private final FileService fileService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Log("上传图片")
    @Operation(summary = "上传图片", description = "上传图片")
    @Anonymous
    public Mono<R<String>> upload(@RequestPart("file") Mono<FilePart> filePart) {
        return this.fileService.upload(filePart)
                .flatMap(R::ok);
    }

}
