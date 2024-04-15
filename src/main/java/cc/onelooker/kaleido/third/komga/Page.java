package cc.onelooker.kaleido.third.komga;

import lombok.Data;

/**
 * @Author xiadawei
 * @Date 2024-04-14 19:28:00
 * @Description TODO
 */
@Data
public class Page {
    private Integer number;
    private String fileName;
    private String mediaType;
    private Integer width;
    private Integer height;
    private Integer sizeBytes;
    private String size;
}
