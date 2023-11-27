package cc.onelooker.kaleido.dto.system;

import cc.onelooker.kaleido.dto.IDictionary;
import com.zjjcnt.common.core.dto.BaseDTO;
import lombok.Data;

import java.util.Date;

/**
 * 部门表DTO
 *
 * @author cyetstar
 * @date 2022-11-13 01:12:24
 * @see cc.onelooker.kaleido.entity.system.SysDeptDO
 */
@Data
public class SysDeptDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = -4812652532919262218L;

    /**
     * id
     */
    private Long id;

    /**
     * 部门编码
     */
    private String deptCode;

    /**
     * 父部门编码
     */
    private String parentCode;

    /**
     * 父部门id
     */
    private Long parentId;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 负责人
     */
    private String leader;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 顺序号
     */
    private Integer orderNum;

    /**
     * 子节点数
     */
    private Integer subCount;

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

    /**
     *
     */
    private String ancestors;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
