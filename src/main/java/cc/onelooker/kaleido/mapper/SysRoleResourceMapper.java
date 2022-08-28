package cc.onelooker.kaleido.mapper;

import cc.onelooker.kaleido.entity.SysRoleResourceDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色和资源关系表Mapper接口
 *
 * @author xiadawei
 * @date 2022-08-15 17:38:57
 */
@Mapper
public interface SysRoleResourceMapper extends BaseMapper<SysRoleResourceDO> {

}