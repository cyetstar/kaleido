package cc.onelooker.kaleido.mapper.comic;

import cc.onelooker.kaleido.entity.comic.ComicAuthorDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 漫画作者Mapper接口
 *
 * @author cyetstar
 * @date 2024-03-12 17:47:50
 */
@Mapper
public interface ComicAuthorMapper extends BaseMapper<ComicAuthorDO> {

    List<ComicAuthorDO> listByBookId(@Param("bookId") String bookId);

    List<ComicAuthorDO> listBySeriesId(@Param("seriesId") String seriesId);
}