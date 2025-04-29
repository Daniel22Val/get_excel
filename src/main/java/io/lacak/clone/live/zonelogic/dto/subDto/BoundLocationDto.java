package io.lacak.clone.live.zonelogic.dto.subDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BoundLocationDto {
    private LocationDto se;
    private LocationDto nw;
}
