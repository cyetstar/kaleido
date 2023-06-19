package cc.onelooker.kaleido.service.business;

import com.zjjcnt.common.core.service.IBaseService;

import java.lang.Long;
import java.lang.String;
import java.util.List;

import cc.onelooker.kaleido.dto.business.MovieFileAudioDTO;

/**
 * 电影文件音频信息Service
 *
 * @author xiadawei
 * @date 2023-06-13 20:35:11
 */
public interface MovieFileAudioService extends IBaseService<MovieFileAudioDTO> {

    boolean deleteByMovieFileId(Long movieFileId);

    List<MovieFileAudioDTO> listByMovieFileId(Long movieFileId);
}