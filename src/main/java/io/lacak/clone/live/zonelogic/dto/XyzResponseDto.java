package io.lacak.clone.live.zonelogic.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class XyzResponseDto <T> {

    private List<T> list;
    private Integer count;
    private Boolean success;
}
