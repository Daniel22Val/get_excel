package io.lacak.clone.live.io.repository;

import io.lacak.clone.live.io.entity.TrackDetailEntity;
import io.lacak.clone.live.io.entity.TrackSummaryEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackDetailRepository extends JpaRepository<TrackDetailEntity, Long> {

    List<TrackDetailEntity> findBySummary(TrackSummaryEntity summary);
}
