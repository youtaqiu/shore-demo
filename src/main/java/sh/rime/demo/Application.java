package sh.rime.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import sh.rime.reactor.security.autoconfigure.EnableSecurity;

@SpringBootApplication
@Slf4j
@EnableReactiveMethodSecurity
@EnableSecurity
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
