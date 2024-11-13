package sh.rime.demo.rest;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import sh.rime.demo.service.FileService;
import sh.rime.reactor.test.ShoreWebFluxTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ShoreWebFluxTest(controllers = FileController.class)
class FileControllerTest {

    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    private FileService fileService;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @SuppressWarnings("unchecked")
    void testUpload() {
        FilePart filePart = Mockito.mock(FilePart.class);
        when(fileService.upload(any(Mono.class))).thenReturn(Mono.just("https://r.rime.sh/1.jpg"));
        webTestClient
                .post()
                .uri("/file")
                .contentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA)
                .bodyValue(filePart)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.data").isEqualTo("https://r.rime.sh/1.jpg");
        verify(fileService).upload(any(Mono.class));
    }

}
