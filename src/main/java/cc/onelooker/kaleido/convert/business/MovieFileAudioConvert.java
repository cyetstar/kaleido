package cc.onelooker.kaleido.convert.business;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.InheritInverseConfiguration;

import cc.onelooker.kaleido.dto.business.MovieFileAudioDTO;
import cc.onelooker.kaleido.entity.business.MovieFileAudioDO;
import cc.onelooker.kaleido.dto.business.req.MovieFileAudioPageReq;
import cc.onelooker.kaleido.dto.business.req.MovieFileAudioCreateReq;
import cc.onelooker.kaleido.dto.business.req.MovieFileAudioUpdateReq;
import cc.onelooker.kaleido.dto.business.resp.MovieFileAudioPageResp;
import cc.onelooker.kaleido.dto.business.resp.MovieFileAudioViewResp;
import cc.onelooker.kaleido.dto.business.resp.MovieFileAudioCreateResp;
import cc.onelooker.kaleido.exp.business.MovieFileAudioExp;

import java.lang.Long;
import java.lang.String;
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