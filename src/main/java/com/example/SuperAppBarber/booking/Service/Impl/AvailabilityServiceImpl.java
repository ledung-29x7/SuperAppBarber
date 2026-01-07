package com.example.SuperAppBarber.booking.Service.Impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.SuperAppBarber.booking.Service.AvailabilityService;
import com.example.SuperAppBarber.booking.dto.sdo.AvailabilityResponse;
import com.example.SuperAppBarber.booking.model.BookingEntity;
import com.example.SuperAppBarber.booking.repository.BookingRepository;
import com.example.SuperAppBarber.common.exception.BusinessException;
import com.example.SuperAppBarber.common.exception.enums.ErrorCode;
import com.example.SuperAppBarber.servicecatalog.repository.ServiceRepository;
import com.example.SuperAppBarber.staff.model.StaffWorkingHoursEntity;
import com.example.SuperAppBarber.staff.repository.DayOffRepository;
import com.example.SuperAppBarber.staff.repository.StaffRepository;
import com.example.SuperAppBarber.staff.repository.StaffWorkingHoursRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AvailabilityServiceImpl implements AvailabilityService {

    private final StaffRepository staffRepository;
    private final StaffWorkingHoursRepository staffWorkingHourRepository;
    private final DayOffRepository dayOffRepository;
    private final BookingRepository bookingRepository;
    private final ServiceRepository serviceRepository;

    @Override
    public List<AvailabilityResponse> getAvailability(
            UUID salonId,
            Long serviceId,
            LocalDate date) {

        var service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND));

        int duration = service.getDurationMinutes();

        LocalDateTime dayStart = date.atStartOfDay();
        LocalDateTime dayEnd = date.plusDays(1).atStartOfDay();

        List<UUID> staffIds = staffRepository.findStaffIdsBySalonAndService(
                salonId, serviceId);

        List<AvailabilityResponse> result = new ArrayList<>();

        for (UUID staffId : staffIds) {

            if (dayOffRepository.existsDayOff(staffId, salonId, date)) {
                continue;
            }

            List<StaffWorkingHoursEntity> workingHours = staffWorkingHourRepository.findByStaffAndDay(
                    staffId, date.getDayOfWeek().getValue());

            if (workingHours.isEmpty())
                continue;

            List<BookingEntity> bookings = bookingRepository.findBookingsOfStaff(
                    staffId, dayStart, dayEnd);

            for (StaffWorkingHoursEntity wh : workingHours) {

                LocalDateTime whStart = LocalDateTime.of(date, wh.getStartTime());

                LocalDateTime whEnd = LocalDateTime.of(date, wh.getEndTime());

                List<TimeRange> freeSlots = subtractBookings(whStart, whEnd, bookings);

                for (TimeRange slot : freeSlots) {

                    LocalDateTime t = slot.start();

                    while (!t.plusMinutes(duration).isAfter(slot.end())) {

                        result.add(AvailabilityResponse.builder()
                                .staffId(staffId)
                                .startTime(t)
                                .endTime(t.plusMinutes(duration))
                                .build());

                        t = t.plusMinutes(duration);
                    }
                }
            }
        }

        return result;
    }

    // ================= TIME SLOT ENGINE =================

    private List<TimeRange> subtractBookings(
            LocalDateTime start,
            LocalDateTime end,
            List<BookingEntity> bookings) {

        List<TimeRange> slots = new ArrayList<>();
        slots.add(new TimeRange(start, end));

        for (BookingEntity b : bookings) {

            List<TimeRange> newSlots = new ArrayList<>();

            for (TimeRange slot : slots) {

                if (b.getEndTime().isBefore(slot.start())
                        || b.getStartTime().isAfter(slot.end())) {
                    newSlots.add(slot);
                    continue;
                }

                if (b.getStartTime().isAfter(slot.start())) {
                    newSlots.add(
                            new TimeRange(slot.start(), b.getStartTime()));
                }

                if (b.getEndTime().isBefore(slot.end())) {
                    newSlots.add(
                            new TimeRange(b.getEndTime(), slot.end()));
                }
            }

            slots = newSlots;
        }

        return slots;
    }

    private record TimeRange(
            LocalDateTime start,
            LocalDateTime end) {
    }
}
