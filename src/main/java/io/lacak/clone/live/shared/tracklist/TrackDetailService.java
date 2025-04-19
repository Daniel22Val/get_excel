package io.lacak.clone.live.shared.tracklist;

import io.lacak.clone.live.io.entity.TrackDetailEntity;
import io.lacak.clone.live.io.entity.TrackSummaryEntity;
import io.lacak.clone.live.io.repository.TrackDetailRepository;
import io.lacak.clone.live.io.repository.TrackSummaryRepository;
import io.lacak.clone.live.ui.model.response.TrackListResponse;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class TrackDetailService {

    private final TrackSummaryRepository trackSummaryRepository;
    private final TrackDetailRepository trackDetailRepository;

    public List<TrackListResponse> findByTrackerIdAndDate (Long trackerId, String date) {

        String dateTimeStr = date + " 00:00:00";

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, format);
        Optional<TrackSummaryEntity> optional = trackSummaryRepository.getOneSummary(trackerId, dateTime);

        if (optional.isEmpty()) {
            return null;
        }

        List<TrackDetailEntity> entityList = trackDetailRepository.findBySummary(optional.get());
        return entityList.stream().map(this::toResponse).toList();
    }

    public TrackListResponse toResponse (TrackDetailEntity detail) {
        TrackListResponse response = new TrackListResponse();
        BeanUtils.copyProperties(detail, response);
        return response;
    }
}
