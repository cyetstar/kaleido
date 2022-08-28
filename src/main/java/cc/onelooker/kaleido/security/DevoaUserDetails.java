package cc.onelooker.kaleido.security;

import com.zjjcnt.common.security.domain.CustomUserDetails;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @Author xiadawei
 * @Date 2022-06-12 00:51:00
 * @Description TODO
 */
public class DevoaUserDetails extends CustomUserDetails {

    public DevoaUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public DevoaUserDetails(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }
}
