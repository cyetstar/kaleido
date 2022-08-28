package cc.onelooker.kaleido.security;

import com.zjjcnt.common.util.DateTimeUtils;
import cc.onelooker.kaleido.utils.DevoaConstants;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

/**
 * @Author xiadawei
 * @Date 2022-08-20 17:39:00
 * @Description TODO
 */
@Component
public class CaptchaFilter extends OncePerRequestFilter {

    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        if (!(StringUtils.equals(request.getRequestURI(), "/login")
                && HttpMethod.POST.matches(request.getMethod()))) {
            chain.doFilter(request, response);
            return;
        }
        try {
            HttpSession session = request.getSession();
            // Session中的校验码
            Boolean enable = (Boolean) session.getAttribute(DevoaConstants.KAPTCHA_ENABLE);
            if (BooleanUtils.isNotTrue(enable)) {
                chain.doFilter(request, response);
                return;
            }
            Date sessionTime = (Date) session.getAttribute(DevoaConstants.KAPTCHA_TIME);
            String sessionTimeStr = DateFormatUtils.format(sessionTime, DateTimeUtils.DATETIME_PATTERN);
            if (StringUtils.compare(DateTimeUtils.addSeconds(-60), sessionTimeStr) > 0) {
                throw new AuthenticationServiceException("验证码过期");
            }
            String sessionCode = (String) session.getAttribute(DevoaConstants.KAPTCHA_CODE);
            String requestCode = request.getParameter("code");
            if (!StringUtils.equalsIgnoreCase(requestCode, sessionCode)) {
                throw new AuthenticationServiceException("验证码错误");
            }
            // 清除Session中校验码相关信息
            session.removeAttribute(DevoaConstants.KAPTCHA_ENABLE);
            session.removeAttribute(DevoaConstants.KAPTCHA_CODE);
            session.removeAttribute(DevoaConstants.KAPTCHA_TIME);
        } catch (AuthenticationException e) {
            customAuthenticationFailureHandler.onAuthenticationFailure(request, response, e);
            return;
        }
        chain.doFilter(request, response);
    }
}
