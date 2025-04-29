package io.lacak.clone.live.ui.controller;

import io.lacak.clone.live.zonelogic.LocationService;
import io.lacak.clone.live.zonelogic.dto.XyzResponseDto;
import io.lacak.clone.live.zonelogic.dto.ZoneResponseDto;
import io.lacak.clone.live.zonelogic.reponse.ZoneCustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/db")
    public ResponseEntity<XyzResponseDto<ZoneCustomResponse>> getZoneNew(@RequestParam String hash,
        @RequestParam Double lat, @RequestParam Double lng) {

        if (hash.equals("Test123") == false) {
            return null;
        }
        return ResponseEntity.ok(locationService.findZoneFromDB(lat, lng));
    }

    @GetMapping("/manual")
    public ResponseEntity<XyzResponseDto<ZoneResponseDto>> getZoneOld(@RequestParam String hash,
        @RequestParam Double lat, @RequestParam Double lng) {

        if (hash.equals("Test123") == false) {
            return null;
        }
        return ResponseEntity.ok(locationService.findZoneFromApi(lat, lng));
    }

}
