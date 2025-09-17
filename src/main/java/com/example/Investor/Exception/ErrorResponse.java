package com.example.Investor.Exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private List<String> details;
    private LocalDateTime timestamp;

    public static ErrorResponse create(String message, String details, LocalDateTime timestamp){
        return new ErrorResponse(message, Collections.singletonList(details),timestamp);
    }

    public static ErrorResponse create(String message, List<String> details, LocalDateTime timestamp){
        return new ErrorResponse(message, details,timestamp);
    }
}
