package cc.onelooker.kaleido.service.system;

import com.zjjcnt.common.core.service.IBaseService;
import cc.onelooker.kaleido.dto.system.SysDictTypeDTO;
import cc.onelooker.kaleido.dto.system.req.SysDictTypeUpdateReq;

/**
 * 字典表类型表Service
 *
 * @author cyetstar
 * @date 2022-04-26 00:41:00
 */
public interface SysDictTypeService extends IBaseService<SysDictTypeDTO> {

    Boolean updateById(SysDictTypeUpdateReq req);

    Boolean deleteByIds(Long[] ids);

    Boolean reload();

    SysDictTypeDTO findByType(String type);
}