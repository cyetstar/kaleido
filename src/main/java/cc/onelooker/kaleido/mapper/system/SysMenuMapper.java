package cc.onelooker.kaleido.mapper.system;

import cc.onelooker.kaleido.entity.system.SysMenuDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 菜单表Mapper接口
 *
 * @author cyetstar
 * @date 2022-11-13 01:12:24
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenuDO> {

    List<SysMenuDO> listByUserId(Long userId);
}