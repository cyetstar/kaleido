package cc.onelooker.kaleido.convert.business;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.InheritInverseConfiguration;

import cc.onelooker.kaleido.dto.business.MovieFileVideoDTO;
import cc.onelooker.kaleido.entity.business.MovieFileVideoDO;
import cc.onelooker.kaleido.dto.business.req.MovieFileVideoPageReq;
import cc.onelooker.kaleido.dto.business.req.MovieFileVideoCreateReq;
import cc.onelooker.kaleido.dto.business.req.MovieFileVideoUpdateReq;
import cc.onelooker.kaleido.dto.business.resp.MovieFileVideoPageResp;
import cc.onelooker.kaleido.dto.business.resp.MovieFileVideoViewResp;
import cc.onelooker.kaleido.dto.business.resp.MovieFileVideoCreateResp;
import cc.onelooker.kaleido.exp.business.MovieFileVideoExp;

import java.lang.Long;
import java.lang.String;
import java.lang.Integer;
/**
* 电影文件视频信息Convert
*
* @author xiadawei
* @date 2023-06-13 20:35:11
*/
@Mapper
public interface MovieFileVideoConvert {

    MovieFileVideoConvert INSTANCE = Mappers.getMapper(MovieFileVideoConvert.class);

    MovieFileVideoDTO convert(MovieFileVideoDO entity);

    @InheritInverseConfiguration(name="convert")
    MovieFileVideoDO convertToDO(MovieFileVideoDTO dto);

    MovieFileVideoDTO convertToDTO(MovieFileVideoPageReq req);

    MovieFileVideoDTO convertToDTO(MovieFileVideoCreateReq req);

    MovieFileVideoDTO convertToDTO(MovieFileVideoUpdateReq req);

    MovieFileVideoPageResp convertToPageResp(MovieFileVideoDTO dto);

    MovieFileVideoViewResp convertToViewResp(MovieFileVideoDTO dto);

    MovieFileVideoCreateResp convertToCreateResp(MovieFileVideoDTO dto);

    MovieFileVideoExp convertToExp(MovieFileVideoDTO dto);

}