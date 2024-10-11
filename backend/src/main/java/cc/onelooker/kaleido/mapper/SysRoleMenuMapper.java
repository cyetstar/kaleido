package cc.onelooker.kaleido.mapper;

import cc.onelooker.kaleido.entity.SysRoleMenuDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色和菜单关系表Mapper接口
 *
 * @author cyetstar
 * @date 2022-11-13 01:12:24
 */
@Mapper
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenuDO> {

}