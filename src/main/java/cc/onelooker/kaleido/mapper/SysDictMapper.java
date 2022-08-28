package cc.onelooker.kaleido.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cc.onelooker.kaleido.entity.SysDictDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 字典表Mapper接口
 *
 * @author xiadawei
 * @date 2022-04-26 00:41:00
 */
@Mapper
public interface SysDictMapper extends BaseMapper<SysDictDO> {

}