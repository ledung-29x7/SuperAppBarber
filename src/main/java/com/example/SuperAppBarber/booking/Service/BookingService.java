package com.example.SuperAppBarber.booking.Service;

import com.example.SuperAppBarber.booking.dto.sdi.CreateBookingRequest;
import com.example.SuperAppBarber.booking.dto.sdi.RescheduleBookingRequest;
import com.example.SuperAppBarber.booking.dto.sdo.BookingResponse;

public interface BookingService {
    BookingResponse createBooking(CreateBookingRequest request);

    BookingResponse getByCode(String bookingCode);

    void cancel(String bookingCode);

    BookingResponse reschedule(String bookingCode, RescheduleBookingRequest req);

}
