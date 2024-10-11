package cc.onelooker.kaleido.support;

import cc.onelooker.kaleido.dto.SysConfigDTO;
import cc.onelooker.kaleido.service.SysConfigService;
import cc.onelooker.kaleido.utils.ConfigUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @Author cyetstar
 * @Date 2023-05-23 21:56:00
 * @Description TODO
 */
@Slf4j
@Component
@DependsOn("flyway")
public class ConfigInitializer {

    @Autowired
    private SysConfigService sysConfigService;

    @PostConstruct
    public void init() {
        ConfigUtils.clearSysConfig();
        List<SysConfigDTO> sysConfigDTOList = sysConfigService.list(null);
        for (SysConfigDTO sysConfigDTO : sysConfigDTOList) {
            ConfigUtils.setSysConfig(sysConfigDTO.getConfigKey(), sysConfigDTO.getConfigValue());
        }
    }

}
