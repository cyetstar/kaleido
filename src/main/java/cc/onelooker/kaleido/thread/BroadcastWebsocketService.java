package cc.onelooker.kaleido.thread;

import cc.onelooker.kaleido.web.socket.WebSocket;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.json.JSONUtil;
import com.zjjcnt.common.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BroadcastWebsocketService {

    @Autowired
    WebSocket webSocket;

    public void broadcastProgressUpdate(String destination, Object value) {
        try {
            webSocket.sendOneMessage("1", JSONUtil.toJsonPrettyStr(value));
        } catch (Exception e) {
            log.error("广播进度发生错误，{}", ExceptionUtil.getMessage(e));
        }
    }
}
