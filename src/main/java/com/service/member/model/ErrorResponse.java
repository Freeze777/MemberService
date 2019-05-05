package com.service.member.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private Date timestamp;
    private String message;
    private String details;
}
