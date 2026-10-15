package com.exam.identityservice.dto.request;

public record LoginUserRequest(
        String username,
        String password
) {
}
