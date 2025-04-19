package io.lacak.clone.live.ui.controller;

import io.lacak.clone.live.shared.tracklist.TrackDetailService;
import io.lacak.clone.live.ui.model.response.TrackListResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/track-detail")
@RequiredArgsConstructor
public class TrackDetailsController {

    private final TrackDetailService trackDetailService;

    @GetMapping()
    public ResponseEntity<List<TrackListResponse>> findById(@RequestParam Long tracker_id,
        @RequestParam String date, @RequestParam String hash) {

        if (hash.equals("Test123") == false) {
            return null;
        }

        return ResponseEntity.ok(trackDetailService.findByTrackerIdAndDate(tracker_id, date));
    }

}
