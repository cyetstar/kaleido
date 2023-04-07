package cc.onelooker.kaleido.service;

import java.util.Map;

/**
 * @Author xiadawei
 * @Date 2022-06-10 14:52:00
 * @Description TODO
 */
public interface IDictionaryService {

    String getType();

    Map<String, String> loadDict();

}
