package cc.onelooker.kaleido.utils;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.FileAppender;
import com.zjjcnt.common.util.DateTimeUtils;
import com.zjjcnt.common.util.MyStringUtils;
import com.zjjcnt.common.util.constant.Constants;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

/**
 * @Author xiadawei
 * @Date 2024-01-18 15:40:00
 * @Description TODO
 */
public class LogUtil {

    public static Logger getLogger(Class<?> clazz) {
        String loggerName = ClassUtils.getName(clazz);
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        String path = loggerContext.getProperty("LOG_PATH");
        Logger logger = loggerContext.getLogger(loggerName);
        FileAppender<ILoggingEvent> appender = (FileAppender<ILoggingEvent>) logger.getAppender(loggerName + "FileAppender");
        String file = path + "/" + MyStringUtils.toSnakeCase(StringUtils.uncapitalize(ClassUtils.getShortClassName(clazz))) + Constants.DOT + DateTimeUtils.now() + ".log";
        if (appender == null) {
            // 设置输出规则和编码方式
            PatternLayoutEncoder encoder = new PatternLayoutEncoder();
            encoder.setPattern("[%-5level] %d{yyyy-MM-dd HH:mm:ss.SSS} [%t] %c{1} - %msg%n");
            encoder.setCharset(StandardCharsets.UTF_8);
            encoder.setContext(loggerContext);
            encoder.start();
            appender = new FileAppender<>();
            // 记得要设置Context
            appender.setContext(loggerContext);
            // 这个名字随意啦，按你们项目里的规则起名
            appender.setName(loggerName + "FileAppender");
            appender.setFile(file);
            appender.setEncoder(encoder);
            appender.start();
            logger.setAdditive(false);
            logger.addAppender(appender);
        } else {
            appender.stop();
            logger.detachAppender(appender);
            appender.setFile(file);
            appender.start();
            logger.addAppender(appender);
        }
        return logger;
    }
}
