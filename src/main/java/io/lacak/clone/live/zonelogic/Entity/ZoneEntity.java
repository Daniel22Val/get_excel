package io.lacak.clone.live.zonelogic.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "zone")
@Setter
@Getter
public class ZoneEntity {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "hash")
    private String hash;

    @Column(name = "label")
    private String label;

    @Column(name = "type")
    private String type;

    @Column(name = "radius")
    private Double radius;

    @Column(name = "center_lat")
    private Double centerLat;

    @Column(name = "center_lng")
    private Double centerLng;

    @Column(name = "bound_se_lat")
    private Double boundSeLat;

    @Column(name = "bound_se_lng")
    private Double boundSeLng;

    @Column(name = "bound_nw_lat")
    private Double boundNwLat;

    @Column(name = "bound_nw_lng")
    private Double boundNwLng;
}
