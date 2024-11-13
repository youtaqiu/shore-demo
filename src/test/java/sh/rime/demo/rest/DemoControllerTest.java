package sh.rime.demo.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import sh.rime.reactor.test.ShoreWebFluxTest;

@ShoreWebFluxTest(controllers = DemoController.class)
class DemoControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void requestShouldSuccess() {
        webTestClient
                .get()
                .uri("/demo")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .json("{\"code\":200,\"message\":\"success\"}");

    }

    @Test
    void requestShouldFailWith401() {
        webTestClient
                .get()
                .uri("/demo")
                .exchange()
                .expectStatus()
                .isUnauthorized();
    }
}
