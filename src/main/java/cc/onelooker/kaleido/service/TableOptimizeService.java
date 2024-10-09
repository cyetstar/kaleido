package cc.onelooker.kaleido.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author xiadawei
 * @Date 2024-10-09 17:07:00
 * @Description TODO
 */
@Service
public class TableOptimizeService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<String> listAllTableNames() {
        // 获取当前数据库的所有表名
        String sql = "SELECT table_name FROM information_schema.tables WHERE table_schema = (SELECT DATABASE())";
        return jdbcTemplate.queryForList(sql, String.class);
    }

    public void optimizeTable(String tableName) {
        // 使用原生的 JDBC 执行 SQL
        String sql = "OPTIMIZE TABLE " + tableName;
        jdbcTemplate.execute(sql);
    }
}
