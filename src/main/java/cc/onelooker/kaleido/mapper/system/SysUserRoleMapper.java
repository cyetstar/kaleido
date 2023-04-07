package cc.onelooker.kaleido.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cc.onelooker.kaleido.entity.system.SysUserRoleDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户和角色关系表Mapper接口
 *
 * @author xiadawei
 * @date 2022-04-26 00:41:00
 */
@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRoleDO> {

}