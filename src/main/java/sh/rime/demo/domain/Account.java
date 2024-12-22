package sh.rime.demo.domain;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.springframework.data.relational.core.mapping.Table;
import sh.rime.reactor.r2dbc.entity.BaseDomain;

import java.io.Serial;

/**
 * @author youta
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Table("t_account")
public class Account extends BaseDomain<Long> {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 状态  0-初始化 1-正常 2-冻结 3-注销
     */
    private Integer status;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 用户名
     */
    private String username;

    /**
     * 性别 0-未知 1-男 2-女
     */
    private Integer gender;

}
