package sh.rime.demo.event;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import sh.rime.demo.event.message.DemoMessage;
import sh.rime.demo.event.message.EventQueueNames;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DemoConsumerTest {

    @InjectMocks
    private DemoConsumer demoConsumer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHandle() {
        DemoMessage demoMessage = new DemoMessage();
        demoMessage.setMessage("Test message");

        Mono<Void> result = demoConsumer.handle(demoMessage);

        StepVerifier.create(result)
                .verifyComplete();

    }

    @Test
    void testGetQueue() {
        String queueName = demoConsumer.getQueue();
        assertEquals(EventQueueNames.DEMO_EVENT, queueName);
    }
}
