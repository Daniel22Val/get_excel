package io.lacak.clone.live.ui.model.request;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class SummarySearchRequest {

    private String nopol;
    private LocalDateTime start;
    private LocalDateTime end;
}
