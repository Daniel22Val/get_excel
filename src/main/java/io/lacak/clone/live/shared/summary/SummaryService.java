package io.lacak.clone.live.shared.summary;

import io.lacak.clone.live.io.entity.TrackDetailEntity;
import io.lacak.clone.live.io.entity.TrackSummaryEntity;
import io.lacak.clone.live.io.repository.TrackDetailRepository;
import io.lacak.clone.live.io.repository.TrackSummaryRepository;
import io.lacak.clone.live.pattern.FieldConvertExcel;
import io.lacak.clone.live.ui.model.request.SummarySearchRequest;
import io.lacak.clone.live.ui.model.response.SummaryResponse;
import io.lacak.clone.live.ui.model.response.TrackListResponse;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class SummaryService {

    private final TrackSummaryRepository trackSummaryRepository;
    private final TrackDetailRepository trackDetailRepository;

    public List<SummaryResponse> searchResponse (SummarySearchRequest searchRequest) {

        List<TrackSummaryEntity> entityList = trackSummaryRepository.search(searchRequest);

        return entityList.stream().map(this::toResponse).toList();
    }

    public SummaryResponse toResponse (TrackSummaryEntity entity) {
        SummaryResponse response = new SummaryResponse();
        BeanUtils.copyProperties(entity, response);
        response.setTracker_id(entity.getTrackerId());
        response.setDate_time(entity.getTrackDate());
        return response;

    }

    public XSSFWorkbook getExcel (String date) {
        String dateTimeStr = date + " 00:00:00";

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, format);

        List<TrackSummaryEntity> entityList = trackSummaryRepository.findByTrackDate(dateTime);
        List<SummaryResponse> responseList = entityList.stream().map(this::toResponse).toList();
        FieldConvertExcel<SummaryResponse> fieldConvert = new FieldConvertExcel<>(new SummaryResponse());
        XSSFWorkbook workbook = fieldConvert.exportReport(responseList);

        FieldConvertExcel<TrackListResponse> fieldConvertDetail = new FieldConvertExcel<>(new TrackListResponse());
        for (TrackSummaryEntity entity : entityList) {
            String nopol = entity.getNopol();
            List<TrackDetailEntity> detailList = trackDetailRepository.findBySummary(entity);
            List<TrackListResponse> detailResponse = detailList.stream().map(this::toDetailResponse).toList();
            fieldConvertDetail.exportReportAnotherSheet(workbook, nopol, detailResponse);
        }


        return workbook;
    }

    private TrackListResponse toDetailResponse (TrackDetailEntity detail) {
        TrackListResponse response = new TrackListResponse();
        BeanUtils.copyProperties(detail, response);
        return response;
    }

}
