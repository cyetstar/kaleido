package cc.onelooker.kaleido.dto;

import cc.onelooker.kaleido.entity.SysMenuDO;
import com.zjjcnt.common.core.dto.BaseDTO;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 菜单表DTO
 *
 * @author cyetstar
 * @date 2022-11-13 01:12:24
 * @see SysMenuDO
 */
@Data
public class SysMenuDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = -3736176839648235544L;

    /**
     * id
     */
    private Long id;

    /**
     * 父菜单id
     */
    private Long parentId;

    /**
     * 菜单类型
     */
    private String type;

    /**
     * 菜单路径
     */
    private String path;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 菜单名称恒等
     */
    private String eqName;

    /**
     * 菜单标题
     */
    private String title;

    /**
     * 描述
     */
    private String description;

    /**
     * 重定向路径
     */
    private String redirect;

    /**
     * 菜单组件
     */
    private String component;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 额外信息，json格式
     */
    private String meta;

    /**
     * 是否显示根
     */
    private String isShowRoot;

    /**
     * 是否显示
     */
    private String isHidden;

    /**
     * 顺序号
     */
    private Integer orderNum;

    /**
     * 子节点数
     */
    private Integer subCount;

    /**
     * 所属应用
     */
    private String app;

    /**
     *
     */
    private String permission;

    /**
     * 是否已删除1：已删除，0：未删除
     */
    private String isDeleted;

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

    private List<SysMenuDTO> children;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

}
