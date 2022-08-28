package cc.onelooker.kaleido.thymeleaf;

import com.zjjcnt.common.core.dict.Dictionary;

import java.util.Map;

/**
 * Created by cyetstar on 17/5/7.
 */
public class Dicts {

    public Map<String, String> items(String type) {
        return Dictionary.get(type);
    }

    public String value(String type, String key) {
        return Dictionary.getValue(type, key);
    }

}
