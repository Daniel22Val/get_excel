package io.lacak.clone.live.shared.summary;

import io.lacak.clone.live.io.entity.TrackDetailEntity;
import io.lacak.clone.live.io.entity.TrackSummaryEntity;
import io.lacak.clone.live.io.repository.TrackDetailRepository;
import io.lacak.clone.live.io.repository.TrackSummaryRepository;
import io.lacak.clone.live.pattern.FieldConvertExcel;
import io.lacak.clone.live.ui.model.BaseFieldResponse;
import io.lacak.clone.live.ui.model.request.SummarySearchRequest;
import io.lacak.clone.live.ui.model.response.SummaryResponse;
import io.lacak.clone.live.ui.model.response.TrackListResponse;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
        FieldConvertExcel<BaseFieldResponse> fieldVertical = new FieldConvertExcel<>(new BaseFieldResponse());
        for (TrackSummaryEntity entity : entityList) {
            String nopol = entity.getNopol();
            List<TrackDetailEntity> detailList = trackDetailRepository.findBySummary(entity);
            List<TrackListResponse> detailResponse = detailList.stream().map(this::toDetailResponse).toList();
            fieldConvertDetail.exportReportAnotherSheet(workbook, nopol, detailResponse);

            String nopol2 = nopol + " - 2";
            List<BaseFieldResponse> summaryNopol = getSummaryBaseField(entity);
            fieldVertical.exportReportAnotherSheet(workbook, nopol2, summaryNopol);
        }


        return workbook;
    }

    private List<BaseFieldResponse> getSummaryBaseField (TrackSummaryEntity entity) {

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        List<BaseFieldResponse> summaryNopol = new ArrayList<>();
        BaseFieldResponse field1  = new BaseFieldResponse();
        field1.setSummary("Date");
        field1.setValue(entity.getTrackDate().format(format));
        summaryNopol.add(field1);

        BaseFieldResponse field2 = new BaseFieldResponse();
        field2.setSummary("Trips");
        field2.setValue(String.valueOf(entity.getTrips()));
        summaryNopol.add(field2);

        BaseFieldResponse field3 = new BaseFieldResponse();
        field3.setSummary("Total Trip Length, km");
        field3.setValue(String.valueOf(entity.getTotal_trip_length()));
        summaryNopol.add(field3);

        BaseFieldResponse field4 = new BaseFieldResponse();
        field4.setSummary("Travel Time");
        field4.setValue(entity.getTravel_time());
        summaryNopol.add(field4);

        BaseFieldResponse field5 = new BaseFieldResponse();
        field5.setSummary("Average speed, km/h");
        field5.setValue(String.valueOf(entity.getAvg_speed()));
        summaryNopol.add(field5);

        BaseFieldResponse field6 = new BaseFieldResponse();
        field6.setSummary("Max speed, km/h");
        field6.setValue(String.valueOf(entity.getMax_speed()));
        summaryNopol.add(field6);

        BaseFieldResponse field7 = new BaseFieldResponse();
        field7.setSummary("Stop Duration");
        field7.setValue(entity.getStop_duration());
        summaryNopol.add(field7);

        BaseFieldResponse field8 = new BaseFieldResponse();
        field8.setSummary("Odometer value, km");
        field8.setValue("0.1");
        summaryNopol.add(field8);

        return summaryNopol;
    }

    private TrackListResponse toDetailResponse (TrackDetailEntity detail) {
        TrackListResponse response = new TrackListResponse();
        BeanUtils.copyProperties(detail, response);
        return response;
    }

}
