package cc.onelooker.kaleido.service.system;

import com.zjjcnt.common.core.service.IBaseService;
import cc.onelooker.kaleido.dto.system.SysMenuDTO;

import java.util.List;

/**
 * 菜单表Service
 *
 * @author xiadawei
 * @date 2022-11-13 01:12:24
 */
public interface SysMenuService extends IBaseService<SysMenuDTO> {

    void init(List<SysMenuDTO> sysMenuDTOList);

    SysMenuDTO findByName(String name);

    List<SysMenuDTO> listByUserId(Long userId);

    List<SysMenuDTO> listByApp(String app);
}