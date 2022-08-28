package cc.onelooker.kaleido.dto;

import cc.onelooker.kaleido.entity.FileInfoDO;
import com.zjjcnt.common.core.dto.BaseDTO;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件信息DTO
 *
 * @author xiadawei
 * @date 2022-05-31 00:27:23
 * @see FileInfoDO
 */
@Data
public class FileInfoDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = 2673808841507575735L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 文件编号
     */
    private String fileId;

    /**
     * 文件名
     */
    private String filename;

    /**
     * 文件类型
     */
    private String contentType;

    /**
     * 文件大小
     */
    private Long size;

    /**
     * 业务主键
     */
    private Long bizId;

    /**
     * 业务表名
     */
    private String bizTable;

    /**
     * 业务类型
     */
    private String bizType;

    private MultipartFile file;

    private String accept = "*";

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
