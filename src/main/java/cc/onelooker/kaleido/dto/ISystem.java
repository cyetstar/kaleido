package cc.onelooker.kaleido.dto;

import com.zjjcnt.common.core.dto.BaseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

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
