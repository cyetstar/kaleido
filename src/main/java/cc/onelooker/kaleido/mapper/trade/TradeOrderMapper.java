package cc.onelooker.kaleido.mapper.trade;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cc.onelooker.kaleido.entity.trade.TradeOrderDO;

import java.lang.Long;
import java.lang.String;
import java.math.BigDecimal;
import com.zjjcnt.common.core.annotation.Dict;
import java.lang.Integer;
import com.zjjcnt.common.core.annotation.StringDateTimeFormat;


/**
 * 交易订单Mapper接口
 *
 * @author cyetstar
 * @date 2023-07-05 23:02:49
 */
@Mapper
public interface TradeOrderMapper extends BaseMapper<TradeOrderDO> {

}