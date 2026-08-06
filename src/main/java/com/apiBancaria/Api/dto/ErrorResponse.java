package com.apiBancaria.Api.dto;

import java.util.List;

public record ErrorResponse(int status, String message, List<String> detalhes) {

    public ErrorResponse(int status, String message) {
        this(status, message, List.of());
    }

}
