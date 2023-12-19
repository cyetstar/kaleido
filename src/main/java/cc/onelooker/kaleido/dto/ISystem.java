package cc.onelooker.kaleido.dto;

import com.zjjcnt.common.core.dto.BaseDTO;

import java.io.Serializable;

/**
 * @Author cyetstar
 * @Date 2022-05-31 01:08:00
 * @Description TODO
 */
public interface ISystem<ID extends Serializable> extends BaseDTO<ID> {

    String getCjsj();

    void setCjsj(String cjsj);

    String getXgsj();

    void setXgsj(String xgsj);

}
