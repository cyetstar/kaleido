package cc.onelooker.kaleido.thymeleaf;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.WebUtils;
import org.thymeleaf.context.IWebContext;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by cyetstar on 18/4/9.
 */
public class Urls {

    HttpServletRequest httpServletRequest = null;

    public Urls(IWebContext webContext) {
        if (webContext != null) {
            httpServletRequest = webContext.getRequest();
        }
    }

    public String current() {
        Map<String, Object> parameters = WebUtils.getParametersStartingWith(httpServletRequest, StringUtils.EMPTY);
        parameters.remove("referer");
        return generateUrl(parameters);
    }

    public String replace(String name, Object value) {
        Map<String, Object> parameters = WebUtils.getParametersStartingWith(httpServletRequest, StringUtils.EMPTY);
        parameters.put(name, value);
        return generateUrl(parameters);
    }

    public String page(Integer page) {
        return replace("pageNumber", page);
    }

    public String style(String style) {
        return replace("style", style);
    }

    private String generateUrl(Map<String, Object> parameters) {
        String queryString = generateQueryString(parameters);
        String requestURI = httpServletRequest.getRequestURI();
        return StringUtils.isNotBlank(queryString) ? requestURI + "?" + queryString : requestURI;
    }

    private String generateQueryString(Map<String, Object> parameters) {
        if (parameters == null) {
            return null;
        }
        List<String> parameterList = Lists.newArrayList();
        for (String key : parameters.keySet()) {
            parameterList.add(key + "=" + parameters.get(key));
        }
        return StringUtils.join(parameterList, "&");
    }

}
