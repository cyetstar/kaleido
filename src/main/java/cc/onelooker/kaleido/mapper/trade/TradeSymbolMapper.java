package cc.onelooker.kaleido.mapper.trade;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cc.onelooker.kaleido.entity.trade.TradeSymbolDO;

import java.lang.String;
import java.lang.Long;
import java.lang.Integer;
import com.zjjcnt.common.core.annotation.Dict;
import java.math.BigDecimal;


/**
 * 交易商品Mapper接口
 *
 * @author cyetstar
 * @date 2023-07-06 11:56:08
 */
@Mapper
public interface TradeSymbolMapper extends BaseMapper<TradeSymbolDO> {

}