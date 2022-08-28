package cc.onelooker.kaleido.service;

import java.io.Serializable;

/**
 * @Author xiadawei
 * @Date 2022-06-10 14:52:00
 * @Description TODO
 */
public interface IApplyService<ID extends Serializable> {

    String getBizTable();

    void apply(ID id);

    default void revoke(ID id) {
        return;
    }

    default void approve(ID id, String spzt) {
        return;
    }

}
