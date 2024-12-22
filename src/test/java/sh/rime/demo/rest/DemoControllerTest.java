package sh.rime.demo.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import sh.rime.demo.domain.Account;
import sh.rime.demo.event.DemoUser;
import sh.rime.demo.service.DemoService;
import sh.rime.reactor.test.ShoreWebFluxTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ShoreWebFluxTest(controllers = DemoController.class)
class DemoControllerTest {

    @Autowired
    private WebTestClient webTestClient;
    @MockitoBean
    private DemoService demoService;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void requestShouldSuccess() {
        DemoUser demoUser = new DemoUser();
        demoUser.setAge(18);
        demoUser.setName("rime");
        Account account = Account.builder()
                .username("rime")
                .build();
        when(demoService.saveAccount(any(String.class)))
                .thenReturn(Mono.just(account));
        webTestClient
                .post()
                .uri("/demo")
                .body(Mono.just(demoUser), DemoUser.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.code")
                .isEqualTo(200);
        verify(demoService).saveAccount(any(String.class));
    }

}
