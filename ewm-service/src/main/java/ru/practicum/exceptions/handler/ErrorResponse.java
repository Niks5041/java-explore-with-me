package ru.practicum.exceptions.handler;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorResponse {
    private String status;
    private Object reason;
    private String message;
    private LocalDateTime timestamp;

    public ErrorResponse(String status, String reason, String message, LocalDateTime timestamp) {
        this.status = status;
        this.reason = reason;
        this.message = message;
        this.timestamp = timestamp;
    }

    public ErrorResponse(String status, Object reason) {
        this.status = status;
        this.reason = getReason();
    }

}


