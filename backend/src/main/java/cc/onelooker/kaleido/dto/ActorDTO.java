package cc.onelooker.kaleido.dto;

import cc.onelooker.kaleido.entity.ActorDO;
import com.baomidou.mybatisplus.annotation.TableField;
import com.zjjcnt.common.core.dto.BaseDTO;
import lombok.Data;

import java.util.List;

/**
 * 演职员DTO
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 * @see ActorDO
 */
@Data
public class ActorDTO implements BaseDTO<String> {
    private static final long serialVersionUID = -1702790923657981726L;

    /**
     * 主键
     */
    private String id;

    /**
     * 豆瓣编号
     */
    private String doubanId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 原名
     */
    private String originalName;

    /**
     * 消歧
     */
    private String disambiguation;

    /**
     * 缩略图
     */
    private String thumb;

    // ------ 非数据库表字段 -------

    private String role;

    private String playRole;

    private String keyword;

    private List<String> idList;

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public ActorDTO() {
    }

    public ActorDTO(String name) {
        this.name = name;
    }
}
