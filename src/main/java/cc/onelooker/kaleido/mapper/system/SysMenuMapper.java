package cc.onelooker.kaleido.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cc.onelooker.kaleido.entity.system.SysMenuDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 菜单表Mapper接口
 *
 * @author xiadawei
 * @date 2022-11-13 01:12:24
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenuDO> {

    List<SysMenuDO> listByUserId(Long userId);
}