package cc.onelooker.kaleido.dto;

import com.zjjcnt.common.core.dto.BaseDTO;

import java.io.Serializable;

/**
 * @Author xiadawei
 * @Date 2022-09-13 01:34:00
 * @Description TODO
 */
public interface ICascadeData<ID extends Serializable> extends BaseDTO<ID> {

    String getValue();

    String getTitle();
}
