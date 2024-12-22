package sh.rime.demo.domain;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

/**
 * account repo
 *
 * @author youta
 */
public interface AccountRepo extends ReactiveCrudRepository<Account, Long> {

    /**
     * find by username
     *
     * @param username username
     * @return Account {@link Account}
     */
    Mono<Account> findByUsername(String username);

}
