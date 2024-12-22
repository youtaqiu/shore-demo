package sh.rime.demo.service;

import cn.hutool.core.util.IdUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import sh.rime.demo.domain.Account;
import sh.rime.demo.domain.AccountRepo;

@Service
@RequiredArgsConstructor
public class DemoService {

    private final AccountRepo accountRepo;

    public Mono<Account> saveAccount(String username) {
        Account account = Account.builder()
                .username(username)
                .mobile("13000000000")
                .avatar("https://v.img.com/1.jpg")
                .nickName("rained")
                .status(1)
                .userId(IdUtil.objectId())
                .build();
        return this.accountRepo
                .findByUsername(username)
                .switchIfEmpty(this.accountRepo.save(account));
    }
}
