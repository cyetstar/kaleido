package cc.onelooker.kaleido.dto;

import com.zjjcnt.common.core.dto.BaseDTO;

import java.io.Serializable;

/**
 * @Author xiadawei
 * @Date 2022-05-31 01:08:00
 * @Description TODO
 */
public interface IDictionary<ID extends Serializable> extends BaseDTO<ID> {

    String getTitle();

}
