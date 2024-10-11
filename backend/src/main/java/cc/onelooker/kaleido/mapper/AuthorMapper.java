package cc.onelooker.kaleido.mapper;

import cc.onelooker.kaleido.entity.AuthorDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 作者Mapper接口
 *
 * @author cyetstar
 * @date 2024-03-12 17:47:50
 */
@Mapper
public interface AuthorMapper extends BaseMapper<AuthorDO> {

    void deleteBySeriesIdAndRole(@Param("seriesId") String seriesId, @Param("role") String role);
}