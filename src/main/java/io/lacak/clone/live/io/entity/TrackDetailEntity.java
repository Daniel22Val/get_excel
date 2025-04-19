package io.lacak.clone.live.io.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "track_list")
@Data
public class TrackDetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "summary_id", referencedColumnName = "id")
    private TrackSummaryEntity summary;

    @Column(name = "start_address")
    private String startAddress;

    @Column(name = "start_lat")
    private Double startLat;

    @Column(name = "start_lng")
    private Double startLng;

    @Column(name = "end_address")
    private String endAddress;

    @Column(name = "end_lat")
    private Double endLat;

    @Column(name = "end_lng")
    private Double endLng;

    @Column(name = "length")
    private Double length;

    @Column(name = "travel_time")
    private String travelTime;

    @Column(name = "avgSpeed")
    private Integer avgSpeed;

    @Column(name = "maxSpeed")
    private Integer maxSpeed;

    @Column(name = "stop_duration")
    private String stopDuration;
}
