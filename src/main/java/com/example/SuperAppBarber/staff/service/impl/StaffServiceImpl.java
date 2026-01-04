package com.example.SuperAppBarber.staff.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.SuperAppBarber.common.enums.DayOffStatus;
import com.example.SuperAppBarber.common.enums.Role;
import com.example.SuperAppBarber.common.enums.StaffRole;
import com.example.SuperAppBarber.common.enums.UserStatus;
import com.example.SuperAppBarber.common.exception.BusinessException;
import com.example.SuperAppBarber.common.exception.enums.ErrorCode;
import com.example.SuperAppBarber.staff.dto.sdi.CreateStaffRequest;
import com.example.SuperAppBarber.staff.dto.sdi.CreateDayOffRequest;
import com.example.SuperAppBarber.staff.dto.sdi.StaffWorkingHourRequest;
import com.example.SuperAppBarber.staff.dto.sdi.UpdateStaffRequest;
import com.example.SuperAppBarber.staff.dto.sdo.DayOffResponse;
import com.example.SuperAppBarber.staff.dto.sdo.StaffResponse;
import com.example.SuperAppBarber.staff.mapper.StaffMapper;
import com.example.SuperAppBarber.staff.model.DayOffEntity;
import com.example.SuperAppBarber.staff.model.StaffEntity;
import com.example.SuperAppBarber.staff.model.StaffWorkingHoursEntity;
import com.example.SuperAppBarber.staff.repository.DayOffRepository;
import com.example.SuperAppBarber.staff.repository.StaffRepository;
import com.example.SuperAppBarber.staff.repository.StaffWorkingHoursRepository;
import com.example.SuperAppBarber.staff.repository.Specification.DayOffSpecification;
import com.example.SuperAppBarber.staff.service.StaffService;
import com.example.SuperAppBarber.user.model.UserEntity;
import com.example.SuperAppBarber.user.repository.UserRepository;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class StaffServiceImpl implements StaffService {

    private final StaffRepository staffRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final StaffWorkingHoursRepository workingHourRepository;
    private final DayOffRepository dayOffRepository;

    @Override
    public StaffResponse create(UUID salonId, CreateStaffRequest request) {

        UserEntity user = new UserEntity();
        // user.setUserId(UUID.randomUUID());
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode("123456"));
        user.setRole(Role.STAFF);
        user.setStatus(UserStatus.ACTIVE);
        userRepository.save(user);

        StaffEntity staff = new StaffEntity();
        // staff.setStaffId(UUID.randomUUID());
        staff.setUserId(user.getUserId());
        staff.setSalonId(salonId);
        staff.setName(request.getName());
        staff.setRole(StaffRole.STAFF);
        staff.setActive(true);
        staff.setCreatedAt(LocalDateTime.now());

        return StaffMapper.toResponse(staffRepository.save(staff));
    }

    @Override
    public List<StaffResponse> list(UUID salonId) {
        return staffRepository.findAllBySalonId(salonId)
                .stream()
                .map(StaffMapper::toResponse)
                .toList();
    }

    @Override
    public StaffResponse update(UUID staffId, UUID salonId, UpdateStaffRequest request) {

        StaffEntity staff = staffRepository.findById(staffId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND));

        if (!staff.getSalonId().equals(salonId))
            throw new BusinessException(ErrorCode.FORBIDDEN);

        if (request.getName() != null)
            staff.setName(request.getName());

        if (request.getActive() != null)
            staff.setActive(request.getActive());

        return StaffMapper.toResponse(staffRepository.save(staff));
    }

    @Override
    public void setWorkingHours(UUID staffId, List<StaffWorkingHourRequest> requests) {

        workingHourRepository.deleteAll(
                workingHourRepository.findByStaffIdAndDeletedAtIsNull(staffId));

        for (StaffWorkingHourRequest r : requests) {
            StaffWorkingHoursEntity e = new StaffWorkingHoursEntity();
            e.setStaffId(staffId);
            e.setDayOfWeek(r.getDayOfWeek());
            e.setStartTime(r.getStartTime());
            e.setEndTime(r.getEndTime());
            workingHourRepository.save(e);
        }
    }

    @Override
    public void requestDayOff(UUID userId, CreateDayOffRequest request) {

        StaffEntity staff = staffRepository.findByUserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND));

        DayOffEntity e = new DayOffEntity();
        e.setStaffId(staff.getStaffId());
        e.setSalonId(staff.getSalonId());
        e.setStartDate(request.getStartDate());
        e.setEndDate(request.getEndDate());
        e.setReason(request.getReason());
        e.setStatus(DayOffStatus.PENDING);
        e.setCreatedAt(LocalDateTime.now());

        dayOffRepository.save(e);
    }

    @Override
    public void setWorkingHours(
            UUID salonId,
            UUID staffId,
            List<StaffWorkingHourRequest> requests) {
        StaffEntity staff = staffRepository.findById(staffId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND));

        if (!staff.getSalonId().equals(salonId)) {
            throw new BusinessException(ErrorCode.FORBIDDEN);
        }

        // soft delete old
        workingHourRepository.softDeleteByStaffId(staffId);

        for (StaffWorkingHourRequest r : requests) {
            StaffWorkingHoursEntity e = new StaffWorkingHoursEntity();
            e.setStaffId(staffId);
            e.setDayOfWeek(r.getDayOfWeek());
            e.setStartTime(r.getStartTime());
            e.setEndTime(r.getEndTime());
            e.setCreatedAt(LocalDateTime.now());
            workingHourRepository.save(e);
        }
    }

    @Override
    public void updateStatus(
            UUID salonId,
            Long dayOffId,
            DayOffStatus status,
            UUID ownerId) {
        DayOffEntity dayOff = dayOffRepository.findById(dayOffId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND));

        if (!dayOff.getSalonId().equals(salonId)) {
            throw new BusinessException(ErrorCode.FORBIDDEN);
        }

        dayOff.setStatus(status);
        dayOff.setApprovedBy(ownerId);
        dayOff.setApprovedAt(LocalDateTime.now());

        dayOffRepository.save(dayOff);
    }

    @Override
    public List<DayOffResponse> getDayOffForApproval(
            UUID salonId,
            LocalDate fromDate,
            LocalDate toDate,
            DayOffStatus status,
            UUID staffId) {
        return dayOffRepository.findAll(
                DayOffSpecification.search(
                        salonId,
                        fromDate,
                        toDate,
                        status,
                        staffId))
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private DayOffResponse toResponse(DayOffEntity e) {
        DayOffResponse r = new DayOffResponse();
        r.setId(e.getId());
        r.setStaffId(e.getStaffId());
        r.setSalonId(e.getSalonId());
        r.setStartDate(e.getStartDate());
        r.setEndDate(e.getEndDate());
        r.setReason(e.getReason());
        r.setStatus(e.getStatus());
        r.setApprovedBy(e.getApprovedBy());
        r.setApprovedAt(e.getApprovedAt());
        r.setCreatedAt(e.getCreatedAt());
        return r;
    }

}
