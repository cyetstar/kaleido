package cc.onelooker.kaleido.utils;

import com.google.common.collect.Lists;
import cc.onelooker.kaleido.domain.TreeNode;

import java.util.List;
import java.util.Map;

/**
 * @Author xiadawei
 * @Date 2022-08-09 12:54:00
 * @Description TODO
 */
public class TreeUtils {

    public static void buildChildren(TreeNode parent, List<TreeNode> children, Map<String, List<TreeNode>> map) {
        children.forEach(s -> {
            buildChildren(s, map.getOrDefault(s.getId(), Lists.newArrayList()), map);
        });
        if (parent != null) {
            if (children != null) {
                parent.setChildren(children);
            } else {
                parent.setChildren(false);
            }
        }
    }
}
