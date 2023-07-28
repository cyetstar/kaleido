package cc.onelooker.kaleido.mapper.trade;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cc.onelooker.kaleido.entity.trade.TradeStrategyDO;

import java.lang.Long;
import java.lang.String;
import com.zjjcnt.common.core.annotation.StringDateTimeFormat;
import com.zjjcnt.common.core.annotation.Dict;
import java.math.BigDecimal;
import java.lang.Integer;


/**
 * 策略Mapper接口
 *
 * @author cyetstar
 * @date 2023-07-05 23:02:49
 */
@Mapper
public interface TradeStrategyMapper extends BaseMapper<TradeStrategyDO> {

}