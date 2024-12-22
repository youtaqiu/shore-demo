package sh.rime.demo.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import sh.rime.reactor.security.domain.CurrentUser;
import sh.rime.reactor.security.domain.RoleEnum;
import sh.rime.reactor.security.domain.RoleInfo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Test
    public void testLoadUserByPhone() {
        StepVerifier.create(Mono.fromCallable(() -> userDetailsService.loadUserByPhone("1234567890")))
                .expectError(UnsupportedOperationException.class)
                .verify();
    }

    @Test
    public void testLoadUserByOpenId() {
        StepVerifier.create(Mono.fromCallable(() -> userDetailsService.loadUserByOpenId("openid123")))
                .expectError(UnsupportedOperationException.class)
                .verify();
    }

    @Test
    public void testLoadByUsername() {
        Mono<CurrentUser> result = userDetailsService.loadByUsername("youta");

        StepVerifier.create(result)
                .assertNext(currentUser -> {
                    assertEquals("1", currentUser.getId());
                    assertEquals("youta", currentUser.getUsername());
                    assertEquals("", currentUser.getPassword());
                    assertTrue(currentUser.isEnabled());
                    assertEquals(1, currentUser.getRoleInfos().size());
                    RoleInfo roleInfo = currentUser.getRoleInfos().getFirst();
                    assertEquals(RoleEnum.ROLE_USER, roleInfo.getRole());
                    assertEquals("普通用户", roleInfo.getRoleName());
                })
                .verifyComplete();
    }

    @Test
    public void testCustomMatcher() {
        assertTrue(userDetailsService.customMatcher());
    }

    @Test
    public void testMatches() {
        CurrentUser currentUser = CurrentUser.builder().build();
        assertTrue(userDetailsService.matches(currentUser, "code123", "grantType"));
    }
}
