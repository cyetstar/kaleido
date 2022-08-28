package cc.onelooker.kaleido.thymeleaf;

import com.google.common.collect.Sets;
import org.thymeleaf.context.IExpressionContext;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.dialect.IExpressionObjectDialect;
import org.thymeleaf.expression.IExpressionObjectFactory;

import java.util.Set;

/**
 * Created by cyetstar on 17/1/8.
 */
public class ExtraDialect implements IExpressionObjectDialect {

    @Override
    public IExpressionObjectFactory getExpressionObjectFactory() {
        return new IExpressionObjectFactory() {
            @Override
            public Set<String> getAllExpressionObjectNames() {
                return Sets.newHashSet("dicts", "urls", "datetimes", "strs", "users");
            }

            @Override
            public Object buildObject(IExpressionContext iExpressionContext, String expressionObjectName) {
                if ("dicts".equals(expressionObjectName)) {
                    return new Dicts();
                } else if ("urls".equals(expressionObjectName)) {
                    IWebContext webContext = (iExpressionContext instanceof IWebContext ? (IWebContext) iExpressionContext : null);
                    return new Urls(webContext);
                } else if ("datetimes".equals(expressionObjectName)) {
                    return new DateTimes();
                } else if ("strs".equals(expressionObjectName)) {
                    return new Strings();
                } else if ("users".equals(expressionObjectName)) {
                    return new Users();
                }
                return null;
            }

            @Override
            public boolean isCacheable(String expressionObjectName) {
                return false;
            }
        };
    }

    @Override
    public String getName() {
        return "extra";
    }
}
