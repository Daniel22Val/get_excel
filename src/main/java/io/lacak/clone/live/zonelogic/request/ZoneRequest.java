package io.lacak.clone.live.zonelogic.request;

import io.lacak.clone.live.zonelogic.dto.subDto.LocationDto;
import lombok.Data;

@Data
public class ZoneRequest {
    private String hash;
    private LocationDto location;
}
