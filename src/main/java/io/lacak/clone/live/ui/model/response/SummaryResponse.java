package io.lacak.clone.live.ui.model.response;

import io.lacak.clone.live.ui.model.FieldBase;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class SummaryResponse extends FieldBase {
    public Long tracker_id;
    public LocalDateTime date_time;
    public String nopol;
    public Integer trips;
    public Double total_trip_length;
    public String travel_time;
    public Integer avg_speed;
    public Integer max_speed;
    public String stop_duration;
}
