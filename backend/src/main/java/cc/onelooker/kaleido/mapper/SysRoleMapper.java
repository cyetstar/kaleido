package cc.onelooker.kaleido.mapper;

import cc.onelooker.kaleido.entity.SysRoleDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 角色表Mapper接口
 *
 * @author cyetstar
 * @date 2022-04-26 00:41:00
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRoleDO> {

    List<SysRoleDO> listByUserId(Long userId);
}