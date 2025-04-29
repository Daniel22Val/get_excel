package io.lacak.clone.live.zonelogic;

import io.lacak.clone.live.zonelogic.Entity.ZoneEntity;
import io.lacak.clone.live.zonelogic.Entity.ZoneRepository;
import io.lacak.clone.live.zonelogic.dto.XyzResponseDto;
import io.lacak.clone.live.zonelogic.dto.ZoneResponseDto;
import io.lacak.clone.live.zonelogic.dto.subDto.BoundLocationDto;
import io.lacak.clone.live.zonelogic.dto.subDto.LocationDto;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class LocationService {

    private final ZoneRepository zoneRepository;
    private final XyzApiService xyzApiService;
    private int EARTH_RADIUS = 6371000;

    public void sync() {
        log.info("start");
        XyzResponseDto<ZoneResponseDto> xyzResponseDto = xyzApiService.getAllZone();
        if (xyzResponseDto == null || xyzResponseDto.getList().isEmpty()) {
            return;
        }

        for (ZoneResponseDto zone : xyzResponseDto.getList()) {
            log.info("zone: {}", String.valueOf(zone.getId()));
            Optional<ZoneEntity> optional =  zoneRepository.findById(zone.getId());
            ZoneEntity newEntity = null;
            if (optional.isEmpty()) {
                newEntity = new ZoneEntity();
                newEntity.setId(zone.getId());
            } else {
                newEntity = optional.get();
            }

            newEntity.setHash("a51e2d17f4165e46927fd75bfe7365cd");
            newEntity.setLabel(zone.getLabel());
            newEntity.setType(zone.getType());

            if (zone.getRadius() != null)
                newEntity.setRadius(zone.getRadius());

            if (zone.getCenter() != null) {
                newEntity.setCenterLat(zone.getCenter().getLat());
                newEntity.setCenterLng(zone.getCenter().getLng());
            }

            if (zone.getBounds() != null) {
                newEntity.setBoundSeLat(zone.getBounds().getSe().getLat());
                newEntity.setBoundSeLng(zone.getBounds().getSe().getLng());
                newEntity.setBoundNwLat(zone.getBounds().getNw().getLat());
                newEntity.setBoundNwLng(zone.getBounds().getNw().getLng());
            }

            zoneRepository.save(newEntity);
        }

        log.info("DONE");
    }

    public XyzResponseDto<ZoneResponseDto> findZoneFromDB (Double lat, Double lng) {
        List<ZoneEntity> zoneEntityList = zoneRepository.findAllByHash("a51e2d17f4165e46927fd75bfe7365cd");
        List<ZoneResponseDto> zoneResponseList = new ArrayList<>();

        for (ZoneEntity zone : zoneEntityList) {

            if (zone.getType().equalsIgnoreCase("circle")) {
                LocationDto center = new LocationDto();
                center.setLat(zone.getCenterLat());
                center.setLng(zone.getCenterLng());

                if (calculateHaversineDistance(zone.getRadius(), lat, lng, center)) {
                    ZoneResponseDto zoneResponseDto = toApiResponseFromDb(zone);
                    zoneResponseList.add(zoneResponseDto);
                }
            } else if (zone.getType().equalsIgnoreCase("polygon")) {

                LocationDto nw = new LocationDto();
                nw.setLat(zone.getBoundNwLat());
                nw.setLng(zone.getBoundNwLng());

                LocationDto se = new LocationDto();
                se.setLat(zone.getBoundSeLat());
                se.setLng(zone.getBoundSeLng());

                BoundLocationDto boundDto = new BoundLocationDto();
                boundDto.setNw(nw);
                boundDto.setSe(se);

                if (isWithinBound(lat, lng, boundDto)) {
                    ZoneResponseDto zoneResponseDto = toApiResponseFromDb(zone);
                    zoneResponseList.add(zoneResponseDto);
                }
            }
        }

        XyzResponseDto<ZoneResponseDto> responseDto = new XyzResponseDto<>();
        responseDto.setList(zoneResponseList);
        responseDto.setCount(zoneResponseList.size());
        responseDto.setSuccess(true);
        return responseDto;
    }

    public XyzResponseDto<ZoneResponseDto> findZoneFromApi(Double lat, Double lng) {

        XyzResponseDto<ZoneResponseDto> xyzResponseDto = xyzApiService.getAllZone();
        if (xyzResponseDto == null || xyzResponseDto.getList().isEmpty()) {
            return null;
        }
        List<ZoneResponseDto> zoneDtoList = new ArrayList<>();

        for (ZoneResponseDto zone : xyzResponseDto.getList()) {

            if (zone.getType().equalsIgnoreCase("circle")) {
                if (calculateHaversineDistance(zone.getRadius(), lat, lng, zone.getCenter())) {
                    zoneDtoList.add(zone);
                }
            } else if (zone.getType().equalsIgnoreCase("polygon")) {
                if (isWithinBound(lat, lng, zone.getBounds())) {
                    zoneDtoList.add(zone);
                }
            }

        }

        XyzResponseDto<ZoneResponseDto> response = new XyzResponseDto<>();
        response.setList(zoneDtoList);
        response.setCount(zoneDtoList.size());
        response.setSuccess(true);
        return response;
    }

    private Boolean isWithinBound(Double lat, Double lng, BoundLocationDto bound) {
        return (bound.getNw().getLat() >= lat && lat >= bound.getSe().getLat()) &&
            (bound.getNw().getLng() <= lng && lng <= bound.getSe().getLng());
    }

    private Boolean calculateHaversineDistance (Double radius, Double lat, Double lng, LocationDto center) {

        LocationDto startPos = new LocationDto();
        startPos.setLat(lat);
        startPos.setLng(lng);

        Double distance = calculateHaversineDistance(startPos, center);
        return distance <= radius;
    }

    private Double calculateHaversineDistance(LocationDto startPos, LocationDto endPos) {
        double distanceLat = Math.toRadians(startPos.getLat() - endPos.getLat());
        double distanceLon = Math.toRadians(startPos.getLng() - endPos.getLng());
        double starLatRadius = Math.toRadians(startPos.getLat());
        double endLatRadius = Math.toRadians(endPos.getLat());
        double a = Math.pow(Math.sin(distanceLat / 2), 2) + Math.cos(starLatRadius) * Math.cos(
            endLatRadius) * Math.pow(Math.sin(distanceLon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return EARTH_RADIUS * c;
    }

    private ZoneResponseDto toApiResponseFromDb (ZoneEntity entity) {
        ZoneResponseDto dto = new ZoneResponseDto();
        dto.setId(entity.getId());
        dto.setLabel(entity.getLabel());
        dto.setAddress(entity.getAddress());
        dto.setColor(entity.getColor());
        dto.setRadius(entity.getRadius());
        dto.setType(entity.getType());

        LocationDto center = new LocationDto();
        center.setLat(entity.getCenterLat());
        center.setLng(entity.getCenterLng());

        BoundLocationDto bound = new BoundLocationDto();
        LocationDto se = new LocationDto();
        LocationDto nw = new LocationDto();

        se.setLat(entity.getBoundSeLat());
        se.setLng(entity.getBoundSeLng());
        nw.setLat(entity.getBoundNwLat());
        nw.setLng(entity.getBoundNwLng());

        bound.setSe(se);
        bound.setNw(nw);

        dto.setCenter(center);
        dto.setBounds(bound);
        return dto;
    }

}
