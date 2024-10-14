package cc.onelooker.kaleido.config;

import com.zjjcnt.common.core.dict.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.List;

@DependsOn("flyway")
@Configuration
@ConditionalOnClass(Dictionary.class)
@ConditionalOnProperty(prefix = "zjjcnt.dictionary", name = "enabled", havingValue = "true", matchIfMissing = true)
public class DictionaryConfig {

    @Bean
    public Dictionary dictionary(ApplicationContext applicationContext,
                                 List<DictionaryInitializer> dictionaryInitializer,
                                 List<DictionaryValueGetter> dictionaryValueGetters,
                                 DictionaryStorer dictionaryStorer) {
        Dictionary dictionary = new Dictionary(applicationContext);
        dictionary.setDictionaryInitializers(dictionaryInitializer);
        dictionary.setDictionaryValueGetters(dictionaryValueGetters);
        dictionary.setDictionaryStorer(dictionaryStorer);
        return dictionary;
    }

    @Bean
    public DictionaryStorer dictionaryStorer() {
        return new MemoryDictionaryStorer();
    }

}
