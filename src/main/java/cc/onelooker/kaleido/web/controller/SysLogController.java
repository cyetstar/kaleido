package cc.onelooker.kaleido.web.controller;

import cc.onelooker.kaleido.dto.resp.SysLogReadResp;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.core.rolling.RollingFileAppender;
import com.google.common.collect.Lists;
import com.zjjcnt.common.core.domain.CommonResult;
import com.zjjcnt.common.core.domain.TextValue;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.nio.file.Paths;
import java.util.List;

/**
 * @Author cyetstar
 * @Date 2023-05-26 11:30:00
 * @Description TODO
 */
@Slf4j
@Api(tags = "系统日志")
@RestController
@RequestMapping("/sysLog")
public class SysLogController {

    @GetMapping("list")
    public CommonResult<List<TextValue>> list() throws IOException {
        File logFolder = getLogFolder();
        String[] fileNames = logFolder.list((dir, name) -> name.endsWith(".log"));
        List<TextValue> list = Lists.newArrayList();
        for (String fileName : fileNames) {
            list.add(new TextValue(fileName, fileName));
        }
        return CommonResult.success(list);
    }

    @GetMapping("read")
    public CommonResult<SysLogReadResp> read(String fileName, @RequestParam(defaultValue = "0") Integer lineNumber) throws IOException {
        File logFolder = getLogFolder();
        String filePath = Paths.get(logFolder.getPath(), fileName).toString();
        List<String> logList = readFile(filePath, lineNumber);
        SysLogReadResp resp = new SysLogReadResp();
        resp.setLogs(logList);
        resp.setLineNumber(logList.size() + lineNumber);
        return CommonResult.success(resp);
    }

    private File getLogFolder() {
        Logger logger = (Logger) LoggerFactory.getLogger("ROOT");
        RollingFileAppender rollingFileAppender = (RollingFileAppender) logger.getAppender("INFO_FILE");
        return Paths.get(rollingFileAppender.getFile()).getParent().toFile();
    }

    private List<String> readFile(String filePath, Integer lineNumber) throws IOException {
        List<String> logList = Lists.newArrayList();
        File file = new File(filePath);
        LineNumberReader reader = new LineNumberReader(new FileReader(file));
//        reader.setLineNumber(lineNumber);
        String line = null;
        while ((line = reader.readLine()) != null) {
            if (reader.getLineNumber() > lineNumber) {
                logList.add(line);
            }
        }
        reader.close();
        return logList;
    }
}
