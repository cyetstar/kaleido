package cc.onelooker.kaleido.convert.movie;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.InheritInverseConfiguration;

import cc.onelooker.kaleido.dto.movie.MovieFileAudioDTO;
import cc.onelooker.kaleido.entity.movie.MovieFileAudioDO;
import cc.onelooker.kaleido.dto.movie.req.MovieFileAudioPageReq;
import cc.onelooker.kaleido.dto.movie.req.MovieFileAudioCreateReq;
import cc.onelooker.kaleido.dto.movie.req.MovieFileAudioUpdateReq;
import cc.onelooker.kaleido.dto.movie.resp.MovieFileAudioPageResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieFileAudioViewResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieFileAudioCreateResp;
import cc.onelooker.kaleido.exp.movie.MovieFileAudioExp;

/**
* 电影文件音频信息Convert
*
* @author xiadawei
* @date 2023-06-13 20:35:11
*/
@Mapper
public interface MovieFileAudioConvert {

    MovieFileAudioConvert INSTANCE = Mappers.getMapper(MovieFileAudioConvert.class);

    MovieFileAudioDTO convert(MovieFileAudioDO entity);

    @InheritInverseConfiguration(name="convert")
    MovieFileAudioDO convertToDO(MovieFileAudioDTO dto);

    MovieFileAudioDTO convertToDTO(MovieFileAudioPageReq req);

    MovieFileAudioDTO convertToDTO(MovieFileAudioCreateReq req);

    MovieFileAudioDTO convertToDTO(MovieFileAudioUpdateReq req);

    MovieFileAudioPageResp convertToPageResp(MovieFileAudioDTO dto);

    MovieFileAudioViewResp convertToViewResp(MovieFileAudioDTO dto);

    MovieFileAudioCreateResp convertToCreateResp(MovieFileAudioDTO dto);

    MovieFileAudioExp convertToExp(MovieFileAudioDTO dto);

}