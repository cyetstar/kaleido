package cc.onelooker.kaleido.security;

import com.google.common.collect.Maps;
import cc.onelooker.kaleido.service.SysUserService;
import cc.onelooker.kaleido.utils.DevoaConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

/**
 * @Author xiadawei
 * @Date 2022-08-20 16:46:00
 * @Description TODO
 */
@Slf4j
@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Autowired
    private SysUserService sysUserService;

    public CustomAuthenticationFailureHandler() {
        super("/login?error");
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        HttpSession session = httpServletRequest.getSession();
        session.setAttribute(DevoaConstants.KAPTCHA_ENABLE, true);
        String username = httpServletRequest.getParameter("username");
        Map<String, Integer> errors = (Map) session.getAttribute(DevoaConstants.LOGIN_FAILURE_TIMES);
        errors = ObjectUtils.defaultIfNull(errors, Maps.newHashMap());
        Integer times = MapUtils.getInteger(errors, username, 1);
        if (times >= 5) {
            sysUserService.lock(username);
        }
        errors.put(username, times + 1);
        session.setAttribute(DevoaConstants.LOGIN_FAILURE_TIMES, errors);
        super.onAuthenticationFailure(httpServletRequest, httpServletResponse, e);
    }
}
