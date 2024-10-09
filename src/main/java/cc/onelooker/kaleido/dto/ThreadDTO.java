package cc.onelooker.kaleido.dto;

import cc.onelooker.kaleido.entity.ThreadDO;
import com.zjjcnt.common.core.dto.BaseDTO;
import lombok.Data;

import java.util.List;

/**
 * 发布记录DTO
 *
 * @author cyetstar
 * @date 2023-12-18 14:53:14
 * @see ThreadDO
 */
@Data
public class ThreadDTO implements BaseDTO<String> {
    private static final long serialVersionUID = 1665172171093787891L;

    /**
     * 主键
     */
    private String id;

    /**
     * 标题
     */
    private String title;

    /**
     * 链接
     */
    private String url;

    /**
     * 豆瓣编号
     */
    private String doubanId;

    /**
     * iMDB编号
     */
    private String imdbId;

    /**
     * 状态
     */
    private String status;

    // ------ 非数据库表字段 -------

    private List<String> idList;

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

}
