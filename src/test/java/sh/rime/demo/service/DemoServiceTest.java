package sh.rime.demo.service;

import cn.hutool.core.util.IdUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import sh.rime.demo.domain.Account;
import sh.rime.demo.domain.AccountRepo;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DemoServiceTest {

    @Mock
    private AccountRepo accountRepo;

    @InjectMocks
    private DemoService demoService;

    @Test
    public void testSaveAccount_newUsername() {
        String username = "testuser";
        Account account = Account.builder()
                .username(username)
                .mobile("13000000000")
                .avatar("https://v.img.com/1.jpg")
                .nickName("rained")
                .status(1)
                .userId(IdUtil.objectId())
                .build();

        when(accountRepo.findByUsername(username)).thenReturn(Mono.empty());
        when(accountRepo.save(any(Account.class))).thenReturn(Mono.just(account));

        Mono<Account> result = demoService.saveAccount(username);

        StepVerifier.create(result)
                .expectNext(account)
                .verifyComplete();
    }

    @Test
    public void testSaveAccount_existingUsername() {
        String username = "existinguser";
        Account existingAccount = Account.builder()
                .username(username)
                .mobile("13000000001")
                .avatar("https://v.img.com/2.jpg")
                .nickName("sunshine")
                .status(0)
                .userId(IdUtil.objectId())
                .build();

        when(accountRepo.save(any(Account.class))).thenAnswer(invocation -> {
            Account account = invocation.getArgument(0);
            // Set userId here, simulating the database behavior
            account.setUserId(IdUtil.objectId());
            return Mono.just(account);
        });
        when(accountRepo.findByUsername(username)).thenReturn(Mono.just(existingAccount));

        Mono<Account> result = demoService.saveAccount(username);

        StepVerifier.create(result)
                .expectNext(existingAccount)
                .verifyComplete();
    }
}
