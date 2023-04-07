package cc.onelooker.kaleido.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zjjcnt.common.core.entity.IdEntity;
import cc.onelooker.kaleido.dto.system.FileInfoDTO;
import lombok.Data;

/**
 * 文件信息DO
 *
 * @author xiadawei
 * @date 2022-05-31 00:27:23
 * @see FileInfoDTO
 */
@Data
@TableName("file_info")
public class FileInfoDO implements IdEntity<Long> {
    private static final long serialVersionUID = -6682807500851010479L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 文件编号
     */
    @TableField(value = "file_id")
    private String fileId;

    /**
     * 文件名
     */
    @TableField(value = "filename")
    private String filename;

    /**
     * 文件类型
     */
    @TableField(value = "content_type")
    private String contentType;

    /**
     * 文件大小
     */
    @TableField(value = "size")
    private Integer size;

    /**
     * 业务主键
     */
    @TableField(value = "biz_id")
    private String bizId;

    /**
     * 业务表名
     */
    @TableField(value = "biz_table")
    private String bizTable;

    /**
     * 业务类型
     */
    @TableField(value = "biz_type")
    private String bizType;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
