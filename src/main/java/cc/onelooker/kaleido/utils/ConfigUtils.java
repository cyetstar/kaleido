package cc.onelooker.kaleido.utils;

import com.zjjcnt.common.core.redis.RedisService;
import com.zjjcnt.common.core.utils.ApplicationContextHelper;
import org.apache.commons.lang3.StringUtils;

import java.util.Set;

/**
 * @Author cyetstar
 * @Date 2023-05-23 20:37:00
 * @Description TODO
 */
public class ConfigUtils {

    private static final String SYS_CONFIG_KEY = "sys_config:";

    /**
     * 清空系统配置
     */
    public static void clearSysConfig() {
        Set<String> keys = ApplicationContextHelper.getBean(RedisService.class).keys(SYS_CONFIG_KEY + "*");
        ApplicationContextHelper.getBean(RedisService.class).deleteObject(keys);
    }

    public static void setSysConfig(String configKey, String configValue) {
        RedisService redisService = ApplicationContextHelper.getBean(RedisService.class);
        redisService.setCacheObject(wrapKey(configKey), configValue);
    }

    public static String getSysConfig(String configKey) {
        return getSysConfig(configKey, "");
    }

    public static String getSysConfig(String configKey, String defValue) {
        String value = ApplicationContextHelper.getBean(RedisService.class).getCacheObject(wrapKey(configKey));
        if (StringUtils.isNotBlank(value)) {
            return value;
        }
        return defValue;
    }

    /**
     * 设置cache key
     *
     * @param configKey 配置键名
     * @return 缓存键key
     */
    private static String wrapKey(String configKey) {
        return SYS_CONFIG_KEY + configKey;
    }
}
