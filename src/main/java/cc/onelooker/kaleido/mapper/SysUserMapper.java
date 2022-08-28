package cc.onelooker.kaleido.mapper;

import cc.onelooker.kaleido.entity.SysUserDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户表Mapper接口
 *
 * @author xiadawei
 * @date 2022-04-26 00:41:00
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUserDO> {

    List<SysUserDO> listByRoleId(Long roleId);
}