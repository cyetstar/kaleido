package cc.onelooker.kaleido.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cc.onelooker.kaleido.entity.system.SysResourceDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 资源表Mapper接口
 *
 * @author cyetstar
 * @date 2022-08-15 17:38:57
 */
@Mapper
public interface SysResourceMapper extends BaseMapper<SysResourceDO> {

    List<SysResourceDO> listByUserId(Long userId);

    List<SysResourceDO> listByRoleId(Long roleId);
}