package cc.onelooker.kaleido.dto;

import com.google.common.collect.Maps;
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
public interface IFile<ID extends Serializable> extends BaseDTO<ID> {

    default Map<String, List<MultipartFile>> getFiles() {
        return Maps.newHashMap();
    }

    default List<String> getFileIds() {
        return null;
    }
}
