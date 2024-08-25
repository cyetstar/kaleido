package cc.onelooker.kaleido.service;

import cc.onelooker.kaleido.dto.SysDictDTO;
import cc.onelooker.kaleido.dto.req.SysDictCreateReq;
import cc.onelooker.kaleido.dto.req.SysDictPageReq;
import cc.onelooker.kaleido.dto.req.SysDictUpdateReq;
import cc.onelooker.kaleido.dto.resp.SysDictPageResp;
import com.zjjcnt.common.core.service.IBaseService;

import java.util.List;

/**
 * 字典表Service
 *
 * @author cyetstar
 * @date 2022-04-26 00:41:00
 */
public interface SysDictService extends IBaseService<SysDictDTO> {

    List<SysDictDTO> listByDictType(String dictType);

    void deleteByDictType(String dictType);

    void deleteByDictType(List<String> dictTypeList);

    SysDictDTO findByDictTypeAndValue(String dictType, String value);

    SysDictDTO findByDictTypeAndLabel(String dictType, String label);

    Boolean updateByDictType(String oldDictType, String newDictType);

    Boolean create(SysDictCreateReq req);

    Boolean updateById(SysDictUpdateReq req);

    List<SysDictDTO> listBySysDictType(String dictType);

    List<SysDictPageResp> listAll(SysDictPageReq req);

    Boolean createBatch(List<SysDictCreateReq> reqs);

    void deleteByDictTypeAndValue(String dictType, String value);
}