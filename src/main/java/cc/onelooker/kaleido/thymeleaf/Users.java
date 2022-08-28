package cc.onelooker.kaleido.thymeleaf;

import cc.onelooker.kaleido.utils.CurrentUserUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author xiadawei
 * @Date 2022-08-21 13:37:00
 * @Description TODO
 */
public class Users {

    public boolean isCurrentUser(String username) {
        return StringUtils.equals(CurrentUserUtils.getUsername(), username);
    }
}
