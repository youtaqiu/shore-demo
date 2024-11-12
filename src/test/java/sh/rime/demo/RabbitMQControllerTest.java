package sh.rime.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import sh.rime.demo.event.message.DemoMessage;
import sh.rime.demo.rest.RabbitMQController;
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
    void testSendMsg() {
        webTestClient
                .get().uri("/mq")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .json("{\"code\":200,\"message\":\"success\",\"success\":true}");

        verify(rabbitMQSender).send(any(DemoMessage.class));
    }

    @Test
    void requestShouldFailWith401() {
        webTestClient
                .get().uri("/mq")
                .exchange()
                .expectStatus().isUnauthorized();
    }
}
