package cc.onelooker.kaleido.mapper.trade;

import cc.onelooker.kaleido.entity.trade.TradeOrderDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 交易订单Mapper接口
 *
 * @author cyetstar
 * @date 2023-07-05 23:02:49
 */
@Mapper
public interface TradeOrderMapper extends BaseMapper<TradeOrderDO> {

}