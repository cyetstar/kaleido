package cc.onelooker.kaleido.mapper.trade;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cc.onelooker.kaleido.entity.trade.TradeGridDO;

import java.lang.Long;
import java.lang.String;
import java.math.BigDecimal;
import java.lang.Integer;
import com.zjjcnt.common.core.annotation.Dict;


/**
 * 交易网格Mapper接口
 *
 * @author cyetstar
 * @date 2023-07-05 23:02:49
 */
@Mapper
public interface TradeGridMapper extends BaseMapper<TradeGridDO> {

}