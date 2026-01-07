package com.example.SuperAppBarber.booking.Service.Impl;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.SuperAppBarber.booking.Service.BookingService;
import com.example.SuperAppBarber.booking.dto.sdi.CreateBookingRequest;
import com.example.SuperAppBarber.booking.dto.sdi.RescheduleBookingRequest;
import com.example.SuperAppBarber.booking.dto.sdo.BookingResponse;
import com.example.SuperAppBarber.booking.model.BookingEntity;
import com.example.SuperAppBarber.booking.repository.BookingRepository;
import com.example.SuperAppBarber.common.enums.BookingStatus;
import com.example.SuperAppBarber.common.exception.BusinessException;
import com.example.SuperAppBarber.common.exception.enums.ErrorCode;
import com.example.SuperAppBarber.common.security.SecurityUtil;
import com.example.SuperAppBarber.customer.model.CustomerEntity;
import com.example.SuperAppBarber.customer.repository.CustomerRepository;
import com.example.SuperAppBarber.servicecatalog.repository.ServiceRepository;
import com.example.SuperAppBarber.staff.repository.StaffRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

        private final BookingRepository bookingRepository;
        private final ServiceRepository serviceRepository;
        private final StaffRepository staffRepository;
        private final CustomerRepository customerRepository;

        @Transactional
        @Override
        public BookingResponse createBooking(CreateBookingRequest req) {

                // UUID customerId = SecurityUtil.getCurrentUserId();
                UUID customerId = resolveCustomerId(req);

                var service = serviceRepository.findById(req.getServiceId())
                                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND));

                LocalDateTime startTime = req.getStartTime();
                LocalDateTime endTime = startTime.plusMinutes(service.getDurationMinutes());

                UUID staffId = resolveStaff(req, service.getServiceId(), startTime);

                // 🔐 Lock
                LocalDate date = startTime.toLocalDate();
                bookingRepository.lockStaffBookings(
                                staffId,
                                date.atStartOfDay(),
                                date.plusDays(1).atStartOfDay());

                // ❌ Check conflict
                if (bookingRepository.existsConflict(staffId, startTime, endTime)) {
                        throw new BusinessException(ErrorCode.BOOKING_CONFLICT);
                }

                BookingEntity booking = BookingEntity.builder()
                                // .bookingId()
                                .bookingCode(generateUniqueBookingCode())
                                .salonId(req.getSalonId())
                                .customerId(customerId)
                                .staffId(staffId)
                                .startTime(startTime)
                                .endTime(endTime)
                                .status(BookingStatus.CONFIRMED)
                                .build();

                bookingRepository.save(booking);

                return BookingResponse.builder()
                                .bookingId(booking.getBookingId())
                                .bookingCode(booking.getBookingCode())
                                .salonId(booking.getSalonId())
                                .staffId(staffId)
                                .customerId(customerId)
                                .startTime(startTime)
                                .endTime(endTime)
                                .status(booking.getStatus())
                                .build();
        }

        private UUID resolveStaff(CreateBookingRequest req,
                        Long serviceId,
                        LocalDateTime startTime) {

                if (req.getStaffId() != null) {
                        return req.getStaffId();
                }

                LocalDate date = startTime.toLocalDate();

                return staffRepository.findAvailableStaffForService(
                                req.getSalonId(),
                                serviceId,
                                date.atStartOfDay(),
                                date.plusDays(1).atStartOfDay())
                                .stream()
                                .findFirst()
                                .orElseThrow(() -> new BusinessException(ErrorCode.STAFF_NOT_AVAILABLE));
        }

        private String generateBookingCode() {
                return "SAL-" + UUID.randomUUID()
                                .toString()
                                .substring(0, 8)
                                .toUpperCase();
        }

        private String generateUniqueBookingCode() {
                String code;
                do {
                        code = generateBookingCode();
                } while (bookingRepository.existsByBookingCode(code));
                return code;
        }

        @Override
        public BookingResponse getByCode(String bookingCode) {

                BookingEntity booking = bookingRepository.findByBookingCode(bookingCode)
                                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND));

                return BookingResponse.builder()
                                .bookingId(booking.getBookingId())
                                .bookingCode(booking.getBookingCode())
                                .salonId(booking.getSalonId())
                                .staffId(booking.getStaffId())
                                .customerId(booking.getCustomerId())
                                .startTime(booking.getStartTime())
                                .endTime(booking.getEndTime())
                                .status(booking.getStatus())
                                .build();
        }

        @Transactional
        @Override
        public void cancel(String bookingCode) {

                BookingEntity booking = bookingRepository.findByBookingCode(bookingCode)
                                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND));

                if (!BookingStatus.CONFIRMED.equals(booking.getStatus())
                                || booking.getStartTime().isBefore(LocalDateTime.now())) {
                        throw new BusinessException(ErrorCode.BOOKING_NOT_CANCELLABLE);
                }

                booking.setStatus(BookingStatus.CANCELLED);
                bookingRepository.save(booking);
        }

        @Override
        @Transactional
        public BookingResponse reschedule(String bookingCode,
                        RescheduleBookingRequest req) {

                BookingEntity booking = bookingRepository.findByBookingCode(bookingCode)
                                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND));

                if (!BookingStatus.CONFIRMED.equals(booking.getStatus())) {
                        throw new BusinessException(ErrorCode.BOOKING_CONFLICT);
                }

                LocalDateTime newStart = req.getStartTime();
                long duration = Duration.between(
                                booking.getStartTime(),
                                booking.getEndTime()).toMinutes();

                LocalDateTime newEnd = newStart.plusMinutes(duration);

                // lock
                LocalDate date = newStart.toLocalDate();
                bookingRepository.lockStaffBookings(
                                booking.getStaffId(),
                                date.atStartOfDay(),
                                date.plusDays(1).atStartOfDay());

                if (bookingRepository.existsConflict(
                                booking.getStaffId(), newStart, newEnd)) {
                        throw new BusinessException(ErrorCode.BOOKING_CONFLICT);
                }

                booking.setStartTime(newStart);
                booking.setEndTime(newEnd);

                bookingRepository.save(booking);

                return BookingResponse.builder()
                                .bookingId(booking.getBookingId())
                                .bookingCode(booking.getBookingCode())
                                .salonId(booking.getSalonId())
                                .staffId(booking.getStaffId())
                                .customerId(booking.getCustomerId())
                                .startTime(booking.getStartTime())
                                .endTime(booking.getEndTime())
                                .status(booking.getStatus())
                                .build();

        }

        private UUID resolveCustomerId(CreateBookingRequest req) {

                UUID userId = SecurityUtil.getCurrentUserIdOptional();

                // ================= CASE 1: Logged-in user =================
                if (userId != null) {

                        return customerRepository.findByUserId(userId)
                                        .map(CustomerEntity::getCustomerId)
                                        .orElseGet(() -> {
                                                // auto create customer
                                                CustomerEntity c = CustomerEntity.builder()
                                                                // .customerId(UUID.randomUUID())
                                                                .userId(userId)
                                                                .salonId(req.getSalonId())
                                                                .createdAt(LocalDateTime.now())
                                                                .build();

                                                return customerRepository.save(c).getCustomerId();
                                        });
                }

                // ================= CASE 2: WALK-IN =================
                if (req.getCustomerPhone() == null) {
                        throw new BusinessException(ErrorCode.INVALID_REQUEST);
                }

                return customerRepository
                                .findBySalonIdAndPhone(req.getSalonId(), req.getCustomerPhone())
                                .map(CustomerEntity::getCustomerId)
                                .orElseGet(() -> {
                                        CustomerEntity c = CustomerEntity.builder()
                                                        // .customerId(UUID.randomUUID())
                                                        .salonId(req.getSalonId())
                                                        .name(req.getCustomerName())
                                                        .phone(req.getCustomerPhone())
                                                        .createdAt(LocalDateTime.now())
                                                        .build();

                                        return customerRepository.save(c).getCustomerId();
                                });
        }

}
