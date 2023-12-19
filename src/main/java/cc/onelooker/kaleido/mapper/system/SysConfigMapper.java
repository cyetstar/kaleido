package cc.onelooker.kaleido.mapper.system;

import cc.onelooker.kaleido.entity.system.SysConfigDO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统配置
 *
 * @author Ye Jianyu
 * @date 2021/2/7
 */
@Mapper
public interface SysConfigMapper extends BaseMapper<SysConfigDO> {

    /**
     * 根据配置键获取系统配置
     *
     * @param configKey 系统配置键名
     * @return 系统配置对象
     */
    default SysConfigDO getByConfigKey(String configKey) {
        LambdaQueryWrapper<SysConfigDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(SysConfigDO::getConfigKey, configKey);
        return selectOne(queryWrapper);
    }
}
