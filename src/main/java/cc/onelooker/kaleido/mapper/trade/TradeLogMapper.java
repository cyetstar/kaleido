package cc.onelooker.kaleido.mapper.trade;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cc.onelooker.kaleido.entity.trade.TradeLogDO;

import java.lang.Long;
import com.zjjcnt.common.core.annotation.Dict;
import java.lang.String;
import java.math.BigDecimal;
import com.zjjcnt.common.core.annotation.StringDateTimeFormat;


/**
 * 交易记录Mapper接口
 *
 * @author cyetstar
 * @date 2023-06-23 13:38:46
 */
@Mapper
public interface TradeLogMapper extends BaseMapper<TradeLogDO> {

}