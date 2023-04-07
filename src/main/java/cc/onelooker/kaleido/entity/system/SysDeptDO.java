package cc.onelooker.kaleido.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zjjcnt.common.core.entity.IdEntity;
import lombok.Data;

import java.util.Date;

/**
 * 部门表DO
 *
 * @author xiadawei
 * @date 2022-11-13 01:12:24
 * @see cc.onelooker.kaleido.dto.system.SysDeptDTO
 */
@Data
@TableName("sys_dept")
public class SysDeptDO implements IdEntity<Long> {
    private static final long serialVersionUID = -7740790268407851308L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 部门编码
     */
    @TableField(value = "dept_code")
    private String deptCode;

    /**
     * 父部门编码
     */
    @TableField(value = "parent_code")
    private String parentCode;

    /**
     * 父部门id
     */
    @TableField(value = "parent_id")
    private Long parentId;

    /**
     * 部门名称
     */
    @TableField(value = "dept_name")
    private String deptName;

    /**
     * 负责人
     */
    @TableField(value = "leader")
    private String leader;

    /**
     * 联系电话
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 邮箱
     */
    @TableField(value = "email")
    private String email;

    /**
     * 顺序号
     */
    @TableField(value = "order_num")
    private Integer orderNum;

    /**
     * 子节点数
     */
    @TableField(value = "sub_count")
    private Integer subCount;

    /**
     * 是否启用
     */
    @TableField(value = "is_enabled")
    private Boolean isEnabled;

    /**
     * 是否已删除1：已删除，0：未删除
     */
    @TableField(value = "is_deleted")
    private Boolean isDeleted;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 创建人
     */
    @TableField(value = "created_by")
    private String createdBy;

    /**
     * 更新人
     */
    @TableField(value = "updated_by")
    private String updatedBy;

    /**
     *
     */
    @TableField(value = "ancestors")
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
