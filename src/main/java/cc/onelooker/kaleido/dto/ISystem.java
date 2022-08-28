package cc.onelooker.kaleido.dto;

import com.zjjcnt.common.core.dto.BaseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Author xiadawei
 * @Date 2022-05-31 01:08:00
 * @Description TODO
 */
public interface ISystem<ID extends Serializable> extends BaseDTO<ID> {

    String getCjrzh();

    void setCjrzh(String cjrzh);

    String getCjr();

    void setCjr(String cjr);

    String getCjsj();

    void setCjsj(String cjsj);

    String getXgrzh();

    void setXgrzh(String xgrzh);

    String getXgr();

    void setXgr(String xgr);

    String getXgsj();

    void setXgsj(String xgsj);

}
