package cc.onelooker.kaleido.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cc.onelooker.kaleido.entity.system.SysRoleMenuDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色和菜单关系表Mapper接口
 *
 * @author xiadawei
 * @date 2022-11-13 01:12:24
 */
@Mapper
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenuDO> {

}