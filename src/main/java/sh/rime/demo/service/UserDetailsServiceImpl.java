package sh.rime.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import sh.rime.reactor.security.domain.CurrentUser;
import sh.rime.reactor.security.domain.RoleEnum;
import sh.rime.reactor.security.domain.RoleInfo;
import sh.rime.reactor.security.service.UserDetailService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailService {

    @Override
    public Mono<CurrentUser> loadUserByPhone(String phone) throws UsernameNotFoundException {
        return null;
    }

    @Override
    public Mono<CurrentUser> loadUserByOpenId(String openId) throws UsernameNotFoundException {
        return null;
    }

    @Override
    public Mono<CurrentUser> loadByUsername(String username) {
        RoleInfo role = new RoleInfo();
        role.setRole(RoleEnum.ROLE_USER);
        return Mono.justOrEmpty(CurrentUser.builder()
                        .id("1")
                        .username("youta")
                        .password("")
                        .enabled(true)
                        .roleInfos(List.of(RoleInfo.builder()
                                .role(RoleEnum.ROLE_USER)
                                .roleName("普通用户")
                                .build()))
                        .build());
    }

    @Override
    public Boolean customMatcher() {
        return true;
    }

    @Override
    public boolean matches(CurrentUser currentUser, String code, String grantType) {
        return true;
    }
}
