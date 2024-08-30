package cc.onelooker.kaleido.entity;

import cc.onelooker.kaleido.dto.AuthorDTO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zjjcnt.common.core.entity.IdEntity;
import lombok.Data;

/**
 * 漫画作者DO
 *
 * @author cyetstar
 * @date 2024-03-12 17:47:50
 * @see AuthorDTO
 */
@Data
@TableName("author")
public class AuthorDO implements IdEntity<String> {
    private static final long serialVersionUID = -5163740577546613087L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 姓名
     */
    @TableField(value = "name")
    private String name;


    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
