package cc.onelooker.kaleido.mapper.trade;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cc.onelooker.kaleido.entity.trade.TradeRuleDO;

import java.lang.Long;
import java.math.BigDecimal;
import java.lang.String;


/**
 * 交易规则Mapper接口
 *
 * @author cyetstar
 * @date 2023-06-23 13:38:46
 */
@Mapper
public interface TradeRuleMapper extends BaseMapper<TradeRuleDO> {

}