package sh.rime.demo.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import sh.rime.demo.event.message.DemoMessage;
import sh.rime.reactor.commons.annotation.Anonymous;
import sh.rime.reactor.commons.bean.R;
import sh.rime.reactor.log.annotation.Log;
import sh.rime.reactor.rabbitmq.producer.RabbitMQSender;

@Tag(name = "mq", description = "消息队列")
@RestController
@RequiredArgsConstructor
@RequestMapping("mq")
public class RabbitMQController {

    private final RabbitMQSender rabbitMQSender;

    @GetMapping
    @Log("发送消息")
    @Operation(summary = "发送消息", description = "发送消息")
    @Anonymous
    public Mono<R<Void>> sendMsg() {
        this.rabbitMQSender.send(DemoMessage.builder()
                .message("Hello, RabbitMQ!")
                .build());
        return R.ok();
    }

}
