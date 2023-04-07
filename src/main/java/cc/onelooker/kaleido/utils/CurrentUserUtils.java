package cc.onelooker.kaleido.utils;

import com.zjjcnt.common.security.domain.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

/**
 * @Author xiadawei
 * @Date 2022-08-15 21:34:00
 * @Description TODO
 */
public class CurrentUserUtils {

    public static CustomUserDetails getUser() {
        return (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static Long getUserId() {
        CustomUserDetails user = getUser();
        return user.getUserId();
    }

    public static String getUsername() {
        CustomUserDetails user = getUser();
        return user.getUsername();
    }

    public static String getName() {
        CustomUserDetails user = getUser();
        return user.getName();
    }

    public static List<String> getRoles() {
        CustomUserDetails user = getUser();
        return (List) user.getAdditionalInfo("roles");
    }

    public static String getDepartmentId() {
        CustomUserDetails user = getUser();
        return user.getAdditionalInfoString("departmentId");
    }

    public static Long getEmployeeId() {
        CustomUserDetails user = getUser();
        return user.getAdditionalInfoLong("employeeId");
    }
}
