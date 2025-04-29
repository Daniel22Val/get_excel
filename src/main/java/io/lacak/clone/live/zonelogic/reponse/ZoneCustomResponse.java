package io.lacak.clone.live.zonelogic.reponse;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ZoneCustomResponse {

    private Long id;
    private String label;
    private String type;
    private Double radius;
    private Double centerLat;
    private Double centerLng;
    private Double boundSeLat;
    private Double boundSeLng;
    private Double boundNwLat;
    private Double boundNwLng;
}
