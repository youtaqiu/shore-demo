package sh.rime.demo.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import sh.rime.demo.event.message.DemoMessage;
import sh.rime.reactor.rabbitmq.consumer.RabbitMQReceiver;

import static sh.rime.demo.event.message.EventQueueNames.DEMO_EVENT;

@Component
@RequiredArgsConstructor
@Slf4j
public class DemoConsumer extends RabbitMQReceiver<DemoMessage> {
    @Override
    public Mono<Void> handle(DemoMessage event) {
        String message = event.getMessage();
        return Mono.fromRunnable(() -> log.info("Demo message: {}", message)).then();
    }

    @Override
    public String getQueue() {
        return DEMO_EVENT;
    }
}
