package cc.onelooker.kaleido.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cc.onelooker.kaleido.entity.system.SysRoleResourceDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色和资源关系表Mapper接口
 *
 * @author cyetstar
 * @date 2022-08-15 17:38:57
 */
@Mapper
public interface SysRoleResourceMapper extends BaseMapper<SysRoleResourceDO> {

}