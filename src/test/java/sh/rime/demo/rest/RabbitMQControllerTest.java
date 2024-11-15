package sh.rime.demo.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import sh.rime.demo.event.message.DemoMessage;
import sh.rime.reactor.rabbitmq.producer.RabbitMQSender;
import sh.rime.reactor.test.ShoreWebFluxTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ShoreWebFluxTest(controllers = RabbitMQController.class)
class RabbitMQControllerTest {

    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    private RabbitMQSender rabbitMQSender;


    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void requestShouldSuccess() {
        webTestClient
                .get().uri("/mq")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code")
                .isEqualTo(200);

        verify(rabbitMQSender).send(any(DemoMessage.class));
    }

}
