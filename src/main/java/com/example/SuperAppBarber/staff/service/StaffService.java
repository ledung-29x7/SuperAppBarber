package com.example.SuperAppBarber.staff.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.example.SuperAppBarber.staff.dto.sdi.CreateStaffRequest;
import com.example.SuperAppBarber.common.enums.DayOffStatus;
import com.example.SuperAppBarber.staff.dto.sdi.CreateDayOffRequest;
import com.example.SuperAppBarber.staff.dto.sdi.StaffWorkingHourRequest;
import com.example.SuperAppBarber.staff.dto.sdi.UpdateStaffRequest;
import com.example.SuperAppBarber.staff.dto.sdo.DayOffResponse;
import com.example.SuperAppBarber.staff.dto.sdo.StaffResponse;

public interface StaffService {

        StaffResponse create(UUID salonId, CreateStaffRequest request);

        List<StaffResponse> list(UUID salonId);

        StaffResponse update(UUID staffId, UUID salonId, UpdateStaffRequest request);

        void setWorkingHours(UUID staffId, List<StaffWorkingHourRequest> requests);

        void requestDayOff(UUID userId, CreateDayOffRequest request);

        void setWorkingHours(
                        UUID salonId,
                        UUID staffId,
                        List<StaffWorkingHourRequest> requests);

        void updateStatus(
                        UUID salonId,
                        Long dayOffId,
                        DayOffStatus status,
                        UUID ownerId);

        List<DayOffResponse> getDayOffForApproval(
                        UUID salonId,
                        LocalDate fromDate,
                        LocalDate toDate,
                        DayOffStatus status,
                        UUID staffId);
}
