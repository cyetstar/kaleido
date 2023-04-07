package cc.onelooker.kaleido.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cc.onelooker.kaleido.entity.system.SysUserDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户表Mapper接口
 *
 * @author xiadawei
 * @date 2022-04-26 00:41:00
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUserDO> {

    List<SysUserDO> listByRoleId(@Param("roleId") Long roleId);

    List<SysUserDO> listByRoleCode(@Param("roleCode") String roleCode);

    List<SysUserDO> listByDeptCodeAndRoleCode(@Param("deptCode") String deptCode, @Param("roleCode") String roleCode);
}