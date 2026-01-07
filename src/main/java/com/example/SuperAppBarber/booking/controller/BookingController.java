package com.example.SuperAppBarber.booking.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.SuperAppBarber.booking.Service.AvailabilityService;
import com.example.SuperAppBarber.booking.Service.BookingService;
import com.example.SuperAppBarber.booking.dto.sdi.CreateBookingRequest;
import com.example.SuperAppBarber.booking.dto.sdi.RescheduleBookingRequest;
import com.example.SuperAppBarber.booking.dto.sdo.AvailabilityResponse;
import com.example.SuperAppBarber.booking.dto.sdo.BookingResponse;
import com.example.SuperAppBarber.common.exception.enums.ErrorCode;
import com.example.SuperAppBarber.common.exception.response.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final AvailabilityService availabilityService;

    @PostMapping
    public ApiResponse<BookingResponse> createBooking(
            @Valid @RequestBody CreateBookingRequest request) {

        return ApiResponse.<BookingResponse>builder()
                .success(true)
                .code(ErrorCode.SUCCESS.getCode())
                .message(ErrorCode.SUCCESS.getMessage())
                .data(bookingService.createBooking(request))
                .build();
    }

    @GetMapping("/{bookingCode}")
    public ApiResponse<BookingResponse> getBooking(@PathVariable String bookingCode) {

        return ApiResponse.<BookingResponse>builder()
                .success(true)
                .code(ErrorCode.SUCCESS.getCode())
                .message(ErrorCode.SUCCESS.getMessage())
                .data(bookingService.getByCode(bookingCode))
                .build();
    }

    // cancel
    @PostMapping("/{bookingCode}/cancel")
    public ApiResponse<Void> cancel(@PathVariable String bookingCode) {

        bookingService.cancel(bookingCode);

        return ApiResponse.<Void>builder()
                .success(true)
                .code(ErrorCode.SUCCESS.getCode())
                .message("Booking cancelled")
                .build();
    }

    @PostMapping("/{bookingCode}/reschedule")
    public ApiResponse<BookingResponse> reschedule(
            @PathVariable String bookingCode,
            @Valid @RequestBody RescheduleBookingRequest request) {

        return ApiResponse.<BookingResponse>builder()
                .success(true)
                .code(ErrorCode.SUCCESS.getCode())
                .message("Booking rescheduled")
                .data(bookingService.reschedule(bookingCode, request))
                .build();
    }

    @GetMapping("/availability")
    public ApiResponse<List<AvailabilityResponse>> getAvailability(
            @RequestParam UUID salonId,
            @RequestParam Long serviceId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        return ApiResponse.<List<AvailabilityResponse>>builder()
                .success(true)
                .code(ErrorCode.SUCCESS.getCode())
                .message(ErrorCode.SUCCESS.getMessage())
                .data(availabilityService.getAvailability(salonId, serviceId, date))
                .build();
    }
}
