package io.lacak.clone.live.ui.model.response;

import io.lacak.clone.live.ui.model.FieldBase;
import lombok.Data;

@Data
public class TrackListResponse extends FieldBase {

    public String startAddress;
    public Double startLat;
    public Double startLng;
    public String endAddress;
    public Double endLat;
    public Double endLng;
    public Double length;
    public String travelTime;
    public Integer avgSpeed;
    public Integer maxSpeed;
    public String stopDuration;
}
