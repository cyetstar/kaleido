package cc.onelooker.kaleido.dto.system.resp;

import lombok.Data;

/**
 * @Author xiadawei
 * @Date 2022-10-01 19:28:00
 * @Description TODO
 */
@Data
public class DictListByTypeResp {
    /**
     * 字典名
     */
    private String label;

    /**
     * 字典值
     */
    private String value;

    public DictListByTypeResp(String label, String value) {
        this.label = label;
        this.value = value;
    }
}
