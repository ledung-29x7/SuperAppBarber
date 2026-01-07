package com.example.SuperAppBarber.common.exception.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // Common
    SUCCESS("00", "Success"),
    INTERNAL_SERVER_ERROR("99", "Internal server error"),
    INVALID_REQUEST("01", "Invalid request"),

    // Auth
    UNAUTHORIZED("AUTH_01", "Unauthorized"),
    FORBIDDEN("AUTH_02", "Forbidden"),

    // Business
    RESOURCE_NOT_FOUND("BUS_01", "Resource not found"),
    DATA_INVALID("BUS_02", "Data invalid"),

    BOOKING_CONFLICT("BOOK_01", "Time slot already booked"),
    STAFF_NOT_AVAILABLE("BOOK_02", "Staff not available"),
    BOOKING_NOT_CANCELLABLE("BOOK_03", "Booking cannot be cancelled");

    private final String code;
    private final String message;
}
