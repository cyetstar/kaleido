package cc.onelooker.kaleido.enums;

/**
 * Created by cyetstar on 2021/2/23.
 */
public enum ThreadStatus {

    def("默认"),
    todo("待收"),
    done("已收"),
    ignore("忽略");

    public String name;

    ThreadStatus(String name) {
        this.name = name;
    }
}
