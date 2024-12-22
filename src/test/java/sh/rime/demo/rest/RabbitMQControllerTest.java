package sh.rime.demo.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import sh.rime.demo.domain.Account;
import sh.rime.demo.domain.AccountRepo;
import sh.rime.demo.event.message.DemoMessage;
import sh.rime.reactor.rabbitmq.producer.RabbitMQSender;
import sh.rime.reactor.test.ShoreWebFluxTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ShoreWebFluxTest(controllers = RabbitMQController.class)
class RabbitMQControllerTest {

    @Autowired
    private WebTestClient webTestClient;
    @MockitoBean
    private RabbitMQSender rabbitMQSender;
    @MockitoBean
    private AccountRepo accountRepo;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void requestShouldSuccess() {
        Account account = Account.builder()
                .username("rainbow")
                .nickName("rainbow")
                .build();

        when(accountRepo.findByUsername(any(String.class)))
                .thenReturn(Mono.just(account));
        webTestClient
                .get().uri("/mq")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code")
                .isEqualTo(200);

        verify(rabbitMQSender).send(any(DemoMessage.class));
        verify(accountRepo).findByUsername(any(String.class));
    }

}
