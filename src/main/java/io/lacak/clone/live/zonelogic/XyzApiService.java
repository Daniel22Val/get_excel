package io.lacak.clone.live.zonelogic;

import io.lacak.clone.live.zonelogic.dto.XyzResponseDto;
import io.lacak.clone.live.zonelogic.dto.ZoneResponseDto;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class XyzApiService {

    public XyzResponseDto<ZoneResponseDto> getAllZone() {

        @Setter
        @Getter
        class ZoneRequest {
            private List<String> sort;
            private Integer offset;
            private Integer limit;
            private String hash;
        }

        ZoneRequest zoneRequest = new ZoneRequest();
        List<String> sort = new ArrayList<>();
        sort.add("label=asc");
        zoneRequest.setSort(sort);
        zoneRequest.setOffset(0);
        zoneRequest.setLimit(20000);
        zoneRequest.setHash("a51e2d17f4165e46927fd75bfe7365cd");

        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);

        String url = "https://xyz.lacak.io/zone/list";
        HttpEntity<ZoneRequest> payload = new HttpEntity<>(zoneRequest, headers);
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<XyzResponseDto<ZoneResponseDto>> response =
                restTemplate.exchange(url, HttpMethod.POST, payload,
                    new ParameterizedTypeReference<XyzResponseDto<ZoneResponseDto>>() {
                    });

            return response.getBody();
        } catch (Exception e) {
            log.error("error: {}", e.getMessage());
            return null;
        }

    }
}
