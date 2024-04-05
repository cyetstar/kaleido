package cc.onelooker.kaleido.mapper.comic;

import cc.onelooker.kaleido.entity.comic.ComicBookAuthorDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 漫画书籍作者关联表Mapper接口
 *
 * @author cyetstar
 * @date 2024-03-12 17:47:50
 */
@Mapper
public interface ComicBookAuthorMapper extends BaseMapper<ComicBookAuthorDO> {

}