package cc.onelooker.kaleido.mapper.comic;

import cc.onelooker.kaleido.dto.comic.ComicSeriesDTO;
import cc.onelooker.kaleido.entity.comic.ComicSeriesDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 漫画系列Mapper接口
 *
 * @author cyetstar
 * @date 2024-03-12 17:47:50
 */
@Mapper
public interface ComicSeriesMapper extends BaseMapper<ComicSeriesDO> {

    List<ComicSeriesDTO> listByAuthorId(@Param("authorId") String authorId);
}