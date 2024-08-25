package cc.onelooker.kaleido.mapper;

import cc.onelooker.kaleido.entity.SysUserDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户表Mapper接口
 *
 * @author cyetstar
 * @date 2022-04-26 00:41:00
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUserDO> {

    List<SysUserDO> listByRoleId(@Param("roleId") Long roleId);

    List<SysUserDO> listByRoleCode(@Param("roleCode") String roleCode);

    List<SysUserDO> listByDeptCodeAndRoleCode(@Param("deptCode") String deptCode, @Param("roleCode") String roleCode);
}