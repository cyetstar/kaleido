package cc.onelooker.kaleido.service;

import cc.onelooker.kaleido.dto.SysMenuDTO;
import com.zjjcnt.common.core.service.IBaseService;

import java.util.List;

/**
 * 菜单表Service
 *
 * @author cyetstar
 * @date 2022-11-13 01:12:24
 */
public interface SysMenuService extends IBaseService<SysMenuDTO> {

    void init(List<SysMenuDTO> sysMenuDTOList);

    SysMenuDTO findByName(String name);

    List<SysMenuDTO> listByUserId(Long userId);

    List<SysMenuDTO> listByApp(String app);

    boolean updateIsHidden(String isHidden, Long id);
}