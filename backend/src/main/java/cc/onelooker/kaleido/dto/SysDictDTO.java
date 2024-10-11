package cc.onelooker.kaleido.dto;

import cc.onelooker.kaleido.entity.SysDictDO;
import com.zjjcnt.common.core.dto.BaseDTO;
import lombok.Data;

import java.util.Date;

/**
 * 字典表DTO
 *
 * @author cyetstar
 * @date 2022-04-26 00:41:00
 * @see SysDictDO
 */
@Data
public class SysDictDTO implements BaseDTO<Long>, Comparable<SysDictDTO> {
    private static final long serialVersionUID = -1538162865444621239L;

    /**
     * 字典详情id
     */
    private Long id;

    /**
     * 字典类型
     */
    private String dictType;

    /**
     * 字典标签
     */
    private String label;

    /**
     * 字典值
     */
    private String value;

    /**
     * 顺序号
     */
    private Integer sort;

    /**
     * 是否启用
     */
    private Boolean isEnabled;

    /**
     * 是否已删除1：已删除，0：未删除
     */
    private Boolean isDeleted;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 更新人
     */
    private String updatedBy;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int compareTo(SysDictDTO o) {
        return Integer.compare(getSort(), o.getSort());
    }
}
