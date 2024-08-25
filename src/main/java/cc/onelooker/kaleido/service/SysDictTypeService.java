package cc.onelooker.kaleido.service;

import cc.onelooker.kaleido.dto.SysDictTypeDTO;
import cc.onelooker.kaleido.dto.req.SysDictTypeUpdateReq;
import com.zjjcnt.common.core.service.IBaseService;

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


     void syncPlex();
}