package cc.onelooker.kaleido.mapper.business;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cc.onelooker.kaleido.entity.business.MovieFileVideoDO;

import java.lang.Long;
import java.lang.String;
import java.lang.Integer;


/**
 * 电影文件视频信息Mapper接口
 *
 * @author xiadawei
 * @date 2023-06-13 20:35:11
 */
@Mapper
public interface MovieFileVideoMapper extends BaseMapper<MovieFileVideoDO> {

}