package io.lacak.clone.live.ui.controller;

import io.lacak.clone.live.shared.summary.SummaryService;
import io.lacak.clone.live.ui.model.request.SummarySearchRequest;
import io.lacak.clone.live.ui.model.response.SummaryResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/track-summary")
@RequiredArgsConstructor
public class TrackSummaryController {

    private final SummaryService summaryService;

    @PostMapping(value = "/search")
    public ResponseEntity<List<SummaryResponse>> searchSummary (@RequestParam String hash, @RequestBody SummarySearchRequest searchRequest) {

        if (!hash.equals("Test123")) {
            return null;
        }

        return ResponseEntity.ok(summaryService.searchResponse(searchRequest));
    }

    @GetMapping(value = "/excel")
    public ResponseEntity<?> getExcel (@RequestParam String hash, @RequestParam String date) {

        if (!hash.equals("Test123")) {
            return null;
        }

        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            XSSFWorkbook workbook = summaryService.getExcel(date);
            HttpHeaders header = new HttpHeaders();
            header.setContentType(new MediaType("application", "force-download"));
            header.set(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=report.xlsx");
            workbook.write(stream);
            workbook.close();
            return new ResponseEntity<>(new ByteArrayResource(stream.toByteArray()), header, HttpStatus.CREATED);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("some thing went wrong when trying to download with " + e.getClass().getCanonicalName());
        }
    }

}
