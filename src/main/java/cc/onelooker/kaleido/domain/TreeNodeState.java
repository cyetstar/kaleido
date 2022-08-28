package cc.onelooker.kaleido.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author xiadawei
 * @Date 2022-06-13 13:23:00
 * @Description TODO
 */
@Data
@Accessors(chain = true)
public class TreeNodeState {

    private boolean opened = false;
    private boolean disabled = false;
    private boolean selected = false;
    @JsonProperty("checkbox_disabled")
    private boolean checkboxDisabled = false;
    private boolean checked = false;

    public static TreeNodeState of(boolean opened, boolean disabled, boolean selected) {
        TreeNodeState treeNodeState = new TreeNodeState();
        treeNodeState.setOpened(opened);
        treeNodeState.setDisabled(disabled);
        treeNodeState.setSelected(selected);
        return treeNodeState;
    }
}
