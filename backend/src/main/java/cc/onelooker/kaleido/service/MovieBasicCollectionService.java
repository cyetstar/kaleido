package cc.onelooker.kaleido.service;

import cc.onelooker.kaleido.dto.MovieBasicCollectionDTO;
import com.zjjcnt.common.core.service.IBaseService;

import java.util.List;

/**
 * 电影集合关联表Service
 *
 * @author cyetstar
 * @date 2023-12-29 16:15:43
 */
public interface MovieBasicCollectionService extends IBaseService<MovieBasicCollectionDTO> {

    List<MovieBasicCollectionDTO> listByCollectionId(String collectionId);

    List<MovieBasicCollectionDTO> listMovieId(String movieId);

    boolean deleteByCollectionId(String collectionId);

    MovieBasicCollectionDTO findByCollectionIdAndDoubanId(String collectionId, String doubanId);

    void updateStatusByMovieId(String status, String movieId);
}