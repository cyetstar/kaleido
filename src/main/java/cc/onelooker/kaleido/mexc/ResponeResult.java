package cc.onelooker.kaleido.mexc;

import lombok.Data;

/**
 * @Author xiadawei
 * @Date 2023-07-06 01:04:00
 * @Description TODO
 */
@Data
public class ResponeResult<T> {

    private Integer code;
    private String msg;
    private Long timestamp;
    private T data;
}
