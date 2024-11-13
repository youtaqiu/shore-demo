package sh.rime.demo.rest;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import sh.rime.reactor.commons.annotation.Anonymous;
import sh.rime.reactor.commons.bean.R;
import sh.rime.reactor.log.annotation.Log;

@RestController
@RequestMapping("demo")
public class DemoController {

    @GetMapping
    @Log("test")
    @Operation(summary = "test", description = "test")
    @Anonymous
    public Mono<R<String>> hello() {
        return R.ok(Mono.just("Hello, Rime!"));
    }
}
