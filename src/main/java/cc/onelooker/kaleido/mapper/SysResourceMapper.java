package cc.onelooker.kaleido.mapper;

import cc.onelooker.kaleido.entity.SysResourceDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 资源表Mapper接口
 *
 * @author xiadawei
 * @date 2022-08-15 17:38:57
 */
@Mapper
public interface SysResourceMapper extends BaseMapper<SysResourceDO> {

    List<SysResourceDO> listByUserId(Long userId);

    List<SysResourceDO> listByRoleId(Long roleId);
}