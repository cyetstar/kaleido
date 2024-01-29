package cc.onelooker.kaleido.service.movie;

import com.zjjcnt.common.core.service.IBaseService;

import cc.onelooker.kaleido.dto.movie.MovieDoubanWeeklyDTO;

/**
 * 豆瓣电影口碑榜Service
 *
 * @author cyetstar
 * @date 2023-12-29 16:15:43
 */
public interface MovieDoubanWeeklyService extends IBaseService<MovieDoubanWeeklyDTO> {

    MovieDoubanWeeklyDTO findByDoubanId(String doubanId);
}