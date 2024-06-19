package cc.onelooker.kaleido.dto.comic.resp;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

/**
 * 漫画系列响应对象
 *
 * @author cyetstar
 * @date 2024-03-12 17:47:50
 */
@Data
@ApiModel("漫画系列响应对象")
public class ComicSeriesViewResp {

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("原标题")
    private String originalTitle;

    @ApiModelProperty("简介")
    private String summary;

    @ApiModelProperty("出版社")
    private String publisher;

    @ApiModelProperty("卷数")
    private Integer bookCount;

    @ApiModelProperty("评分")
    private Float rating;

    @ApiModelProperty("状态")
    private String status;

    @ApiModelProperty("路径")
    private String path;

    @ApiModelProperty("番组计划编号")
    private String bgmId;

    @ApiModelProperty("作者")
    private Author writer;

    @ApiModelProperty("作画")
    public Author penciller;

    @ApiModelProperty("别名")
    private List<String> alternateTitleList;

    @ApiModelProperty("标签")
    private List<String> tagList;

    public List<Author> getAuthorList() {
        List<Author> authorList = Lists.newArrayList();
        CollectionUtils.addIgnoreNull(authorList, writer);
        CollectionUtils.addIgnoreNull(authorList, penciller);
        return authorList;
    }

    @Data
    public static class Author {
        private String id;
        private String name;
    }
}
