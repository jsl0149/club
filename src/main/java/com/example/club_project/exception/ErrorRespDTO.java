package com.example.club_project.exception;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorRespDTO {

    String exception;

    String message;

    String error;

    Map<String, String> errors;
}

