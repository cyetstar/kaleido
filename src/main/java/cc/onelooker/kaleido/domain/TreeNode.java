package cc.onelooker.kaleido.domain;

import com.google.common.collect.Maps;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * @Author xiadawei
 * @Date 2022-06-13 13:21:00
 * @Description TODO
 */
@Data
@Accessors(chain = true)
public class TreeNode {
    private String id;
    private String parentId;
    private String text;
    private String icon;
    private String type;
    private TreeNodeState state = new TreeNodeState();
    private Object children;
    private Map<String, Object> attrs = Maps.newHashMap();

    public TreeNode open() {
        state.setOpened(true);
        return this;
    }

    public TreeNode disable() {
        state.setDisabled(true);
        return this;
    }

    public TreeNode select() {
        state.setSelected(true);
        return this;
    }
}
