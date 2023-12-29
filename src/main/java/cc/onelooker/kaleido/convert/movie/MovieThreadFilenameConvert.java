package cc.onelooker.kaleido.convert.movie;

import cc.onelooker.kaleido.dto.movie.MovieThreadFilenameDTO;
import cc.onelooker.kaleido.dto.movie.req.MovieThreadFilenameCreateReq;
import cc.onelooker.kaleido.dto.movie.req.MovieThreadFilenamePageReq;
import cc.onelooker.kaleido.dto.movie.req.MovieThreadFilenameUpdateReq;
import cc.onelooker.kaleido.dto.movie.resp.MovieThreadFilenameCreateResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieThreadFilenamePageResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieThreadFilenameViewResp;
import cc.onelooker.kaleido.entity.movie.MovieThreadFilenameDO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 电影发布文件Convert
 *
 * @author cyetstar
 * @date 2023-12-18 16:03:32
 */
@Mapper
public interface MovieThreadFilenameConvert {

    MovieThreadFilenameConvert INSTANCE = Mappers.getMapper(MovieThreadFilenameConvert.class);

    MovieThreadFilenameDTO convert(MovieThreadFilenameDO entity);

    @InheritInverseConfiguration(name = "convert")
    MovieThreadFilenameDO convertToDO(MovieThreadFilenameDTO dto);

    MovieThreadFilenameDTO convertToDTO(MovieThreadFilenamePageReq req);

    MovieThreadFilenameDTO convertToDTO(MovieThreadFilenameCreateReq req);

    MovieThreadFilenameDTO convertToDTO(MovieThreadFilenameUpdateReq req);

    MovieThreadFilenamePageResp convertToPageResp(MovieThreadFilenameDTO dto);

    MovieThreadFilenameViewResp convertToViewResp(MovieThreadFilenameDTO dto);

    MovieThreadFilenameCreateResp convertToCreateResp(MovieThreadFilenameDTO dto);

}