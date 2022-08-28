package cc.onelooker.kaleido.service;

import com.zjjcnt.common.core.service.IBaseService;
import cc.onelooker.kaleido.dto.SysDictDTO;

import java.util.List;

/**
 * 字典表Service
 *
 * @author xiadawei
 * @date 2022-04-26 00:41:00
 */
public interface SysDictService extends IBaseService<SysDictDTO> {

    List<SysDictDTO> listByDictType(String dictType);
}