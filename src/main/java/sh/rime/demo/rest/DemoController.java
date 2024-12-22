package sh.rime.demo.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import sh.rime.demo.domain.Account;
import sh.rime.demo.event.DemoUser;
import sh.rime.demo.service.DemoService;
import sh.rime.reactor.commons.bean.R;
import sh.rime.reactor.log.annotation.Log;

@RestController
@RequestMapping("demo")
@RequiredArgsConstructor
public class DemoController {

    private final DemoService demoService;

    @PostMapping
    @Log("test create account")
    public Mono<R<String>> hello(@RequestBody DemoUser userInfo) {
        String name = userInfo.getName();
        return this.demoService.saveAccount(name)
                .map(Account::getUsername)
                .flatMap(R::ok);
    }
}
