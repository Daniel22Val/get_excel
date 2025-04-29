package io.lacak.clone.live.zonelogic.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.lacak.clone.live.zonelogic.dto.subDto.BoundLocationDto;
import io.lacak.clone.live.zonelogic.dto.subDto.LocationDto;
import java.util.List;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ZoneResponseDto {

    private Long id;
    private String label;
    private String address;
    private String color;
    private List<Integer> tags;
    private Double radius;
    private LocationDto center;
    private String type;
    private BoundLocationDto bounds;
}
