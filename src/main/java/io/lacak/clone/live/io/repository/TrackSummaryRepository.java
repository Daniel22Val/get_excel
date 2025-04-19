package io.lacak.clone.live.io.repository;

import io.lacak.clone.live.io.entity.TrackSummaryEntity;
import io.lacak.clone.live.ui.model.request.SummarySearchRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackSummaryRepository extends JpaRepository<TrackSummaryEntity, Long> {

    @Query(value = "SELECT tse"
        + " FROM TrackSummaryEntity tse"
        + " WHERE tse.id > 0 "
        + " AND (:#{#request.start} IS NULL OR :#{#request.start} <= tse.trackDate)"
        + " AND (:#{#request.end} IS NULL OR :#{#request.end} >= tse.trackDate)"
        + " AND (:#{#request.nopol} IS NULL OR tse.nopol LIKE CONCAT('%',:#{#request.nopol},'%'))")
    List<TrackSummaryEntity> search(SummarySearchRequest request);

    List<TrackSummaryEntity> findByTrackDate(LocalDateTime dateTime);

    @Query(value = "SELECT tse" +
        " FROM TrackSummaryEntity tse" +
        " WHERE tse.trackerId = :trackerId AND tse.trackDate = :dateTime")
    Optional<TrackSummaryEntity> getOneSummary (Long trackerId, LocalDateTime dateTime);
}
