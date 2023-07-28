package cc.onelooker.kaleido.mexc;

import cc.onelooker.kaleido.mexc.req.OrderReq;
import cc.onelooker.kaleido.mexc.resp.*;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import java.net.URI;
import java.util.*;

/**
 * @Author xiadawei
 * @Date 2023-07-06 00:44:00
 * @Description TODO
 */
@Component
public class MexcApiService {

    private final static String API_DEFAULT_SYMBOLS = "/api/v3/defaultSymbols";
    private final static String API_EXCHANGE_INFO = "/api/v3/exchangeInfo?symbol={symbol}";
    private final static String API_TICKER_PRICE = "/api/v3/ticker/price?symbol={symbol}";
    private final static String API_ACCOUNT = "/api/v3/account?timestamp={timestamp}&signature={signature}";
    private final static String API_ORDER = "/api/v3/order?timestamp={timestamp}&signature={signature}";
    private final static String API_ORDER_TEST = "/api/v3/order/test?signature={signature}";
    private final static String API_OPEN_ORDERS = "/api/v3/openOrders?symbol={symbol}&timestamp={timestamp}&signature={signature}";

    private String url = "https://api.mexc.com";

    private String accessKey = "mx0vglU0J5CKvdhw5t";
    private String secretKey = "68e8e759b0f644c3b9739da23d1033b0";

    @Autowired
    private RestTemplate restTemplate;

    private String getAPIUrl(String api) {
        return url + api;
    }

    public DefaultSymbolsResp defaultSymbols() {
        return get(API_DEFAULT_SYMBOLS, DefaultSymbolsResp.class, Maps.newHashMap());
    }

    public ExchangeInfoResp exchangeInfo(String symbol) {
        Map<String, String> param = Maps.newHashMap();
        param.put("symbol", symbol);
        return get(API_EXCHANGE_INFO, ExchangeInfoResp.class, param);
    }

    public TickerPriceResp tickerPrice(String symbol) {
        Map<String, String> param = Maps.newHashMap();
        param.put("symbol", symbol);
        return get(API_TICKER_PRICE, TickerPriceResp.class, param);
    }

    public AccountResp account() {
        Map<String, String> param = Maps.newHashMap();
        Long timestamp = System.currentTimeMillis();
        param.put("timestamp", String.valueOf(timestamp));
        return get(API_ACCOUNT, AccountResp.class, param);
    }

    public OrderResp order(OrderReq orderReq) {
        Map<String, String> param = Maps.newHashMap();
        Long timestamp = System.currentTimeMillis();
        param.put("timestamp", String.valueOf(timestamp));
        param.put("side", orderReq.getSide());
        param.put("type", orderReq.getType());
        param.put("symbol", orderReq.getSymbol());
        param.put("price", orderReq.getPrice());
        param.put("quantity", orderReq.getQuantity());
        param.put("newClientOrderId", orderReq.getNewClientOrderId());
        return post(API_ORDER_TEST, OrderResp.class, param);
    }

    public List<OrderResp> allOrders(String symbol) {
        Map<String, String> param = Maps.newHashMap();
        Long timestamp = System.currentTimeMillis();
        param.put("symbol", symbol);
        param.put("timestamp", String.valueOf(timestamp));
        return get(API_OPEN_ORDERS, List.class, param);
    }

    private <RESP> RESP post(String url, Class<RESP> clazz, Map<String, String> param) {
        String body = ApiUtils.getRequestParamString(param);
        String signature = ApiUtils.actualSignature(body, secretKey);
        param.put("signature", signature);
        HttpHeaders headers = genHttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(body, headers);
        ResponseEntity<RESP> responseEntity = restTemplate.exchange(getAPIUrl(url), HttpMethod.POST, entity, clazz, param);
        return responseEntity.getBody();
    }

    private <RESP> RESP get(String url, Class<RESP> clazz, Map<String, String> param) {
        String signature = ApiUtils.sign(param, secretKey);
        HttpHeaders headers = genHttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        UriTemplate uriTemplate = new UriTemplate(getAPIUrl(url));
        param.put("signature", signature);
        URI uri = uriTemplate.expand(param);
        ResponseEntity<RESP> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, entity, clazz);
        return responseEntity.getBody();
    }

    private HttpHeaders genHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.put("X-MEXC-APIKEY", Lists.newArrayList(accessKey));
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
