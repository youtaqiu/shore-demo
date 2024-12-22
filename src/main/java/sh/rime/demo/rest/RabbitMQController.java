package sh.rime.demo.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import sh.rime.demo.domain.AccountRepo;
import sh.rime.demo.event.message.DemoMessage;
import sh.rime.reactor.commons.annotation.Anonymous;
import sh.rime.reactor.commons.bean.R;
import sh.rime.reactor.log.annotation.Log;
import sh.rime.reactor.rabbitmq.producer.RabbitMQSender;

@RestController
@RequiredArgsConstructor
@RequestMapping("mq")
@Slf4j
public class RabbitMQController {

    private final RabbitMQSender rabbitMQSender;
    private final AccountRepo accountRepo;

    @GetMapping
    @Log("发送消息")
    @Anonymous
    public Mono<R<Void>> sendMsg() {
        return R.ok(this.accountRepo.findByUsername("rainbow")
                .doOnNext(account -> log.info("account1: {}", account))
                .map(account -> {
                    this.rabbitMQSender.send(DemoMessage.builder()
                            .message("Hello, " + account.getNickName())
                            .build());
                    return account;
                })
                .then());
    }

}
