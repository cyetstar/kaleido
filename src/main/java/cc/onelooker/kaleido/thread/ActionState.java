package cc.onelooker.kaleido.thread;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.Duration;
import java.time.Instant;

/**
 * Created by cyetstar on 2021/1/27.
 */
@Data
public class ActionState {

    private Action action;

    private String message;

    private Float percent;

    private Boolean running;

    private Boolean complete;

    private Instant startTime;

    @JsonProperty
    public String getDuration() {
        Duration between = Duration.between(startTime, Instant.now());
        int hoursPart = (int) between.toHours() % 24;
        int minutesPart = (int) between.toMinutes() % 60;
        int secondsPart = (int) between.getSeconds() % 60;
        StringBuilder duration = new StringBuilder();
        if (hoursPart > 0) {
            duration.append(hoursPart).append("小时");
        }
        if (minutesPart > 0) {
            duration.append(minutesPart).append("分");
        }
        if (secondsPart > 0) {
            duration.append(secondsPart).append("秒");
        }
        return duration.toString();
    }

    @JsonProperty
    public String getActionLabel() {
        return action.getTitle();
    }

}
