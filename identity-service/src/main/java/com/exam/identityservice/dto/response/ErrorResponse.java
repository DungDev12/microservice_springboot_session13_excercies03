package com.exam.identityservice.dto.response;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Map;

@Getter
@Setter
@Builder
public class ErrorResponse {
    private Instant timestamp = Instant.now();
    private int status;
    private Object error;
    private String path;
}
