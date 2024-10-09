package cc.onelooker.kaleido.thread;

import cc.onelooker.kaleido.service.TableOptimizeService;
import com.zjjcnt.common.core.domain.PageResult;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by cyetstar on 2021/1/7.
 */
@Component
public class TableOptimizeRunnable extends AbstractEntityActionRunnable<String> {

    private TableOptimizeService tableOptimizeService;

    public TableOptimizeRunnable(TableOptimizeService tableOptimizeService) {
        this.tableOptimizeService = tableOptimizeService;
    }

    @Override
    public Action getAction() {
        return Action.tableOptimize;
    }

    @Override
    protected PageResult<String> page(Map<String, String> params, int pageNumber, int pageSize) {
        PageResult<String> pageResult = new PageResult<>();
        pageResult.setSearchCount(true);
        if (pageNumber == 1) {
            List<String> records = tableOptimizeService.listAllTableNames();
            pageResult.setRecords(records);
            pageResult.setTotal((long) records.size());
        }
        return pageResult;
    }

    @Override
    protected int processEntity(Map<String, String> params, String tableName) throws Exception {
        tableOptimizeService.optimizeTable(tableName);
        return SUCCESS;
    }

}
