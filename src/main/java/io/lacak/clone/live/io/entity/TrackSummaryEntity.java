package io.lacak.clone.live.io.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Data;

@Entity
@Table(name = "track_summary")
@Data
public class TrackSummaryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tracker_id")
    private Long trackerId;

    @Column(name = "track_date")
    private LocalDateTime trackDate;

    @Column(name = "nopol")
    private String nopol;

    @Column(name = "trips")
    private Integer trips;

    @Column(name = "total_trip_length")
    private Double total_trip_length;

    @Column(name = "travel_time")
    private String travel_time;

    @Column(name = "avg_speed")
    private Integer avg_speed;

    @Column(name = "max_speed")
    private Integer max_speed;

    @Column(name = "stop_duration")
    private String stop_duration;
}
