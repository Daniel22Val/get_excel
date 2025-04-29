package io.lacak.clone.live.ui.controller;

import io.lacak.clone.live.zonelogic.LocationService;
import io.lacak.clone.live.zonelogic.dto.XyzResponseDto;
import io.lacak.clone.live.zonelogic.dto.ZoneResponseDto;
import io.lacak.clone.live.zonelogic.reponse.ZoneCustomResponse;
import io.lacak.clone.live.zonelogic.request.ZoneRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/location")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @GetMapping("/sync")
    public ResponseEntity<String> syncZone(@RequestParam String hash) {

        if (hash.equals("Test123") == false) {
            return null;
        }
        locationService.sync();
        return ResponseEntity.ok("done");
    }

    @PostMapping("/db")
    public ResponseEntity<XyzResponseDto<ZoneCustomResponse>> getZoneNew(
        @RequestBody ZoneRequest request) {

        if (request.getHash().equals("Test123") == false) {
            return null;
        }
        return ResponseEntity.ok(locationService.findZoneFromDB(request.getLocation().getLat(),
            request.getLocation().getLng()));
    }

    @PostMapping("/manual")
    public ResponseEntity<XyzResponseDto<ZoneResponseDto>> getZoneOld(
        @RequestBody ZoneRequest request) {

        if (request.getHash().equals("Test123") == false) {
            return null;
        }
        return ResponseEntity.ok(locationService.findZoneFromApi(request.getLocation().getLat(),
            request.getLocation().getLng()));
    }

}
