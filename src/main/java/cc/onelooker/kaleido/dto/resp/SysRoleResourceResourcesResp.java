package cc.onelooker.kaleido.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author lijun
 * @date 2023/2/16 0016 14:50
 * @description 角色绑定的资源列表
 */
@ApiModel("角色绑定的资源列表")
public class SysRoleResourceResourcesResp {

    @ApiModelProperty("类型")
    private String type;

    @ApiModelProperty("操作")
    private List<ResourceInfo> actions;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ResourceInfo> getActions() {
        return actions;
    }

    public void setActions(List<ResourceInfo> actions) {
        this.actions = actions;
    }

    /**
     * 角色的资源状态信息
     */
    public static class ResourceInfo {
        private Long id;

        private String action;

        private boolean status;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }
    }
}


