package com.example.SuperAppBarber.servicecatalog.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.SuperAppBarber.common.exception.BusinessException;
import com.example.SuperAppBarber.common.exception.enums.ErrorCode;
import com.example.SuperAppBarber.servicecatalog.dto.sdo.StaffServiceResponse;
import com.example.SuperAppBarber.servicecatalog.model.ServiceEntity;
import com.example.SuperAppBarber.servicecatalog.model.StaffServiceEntity;
import com.example.SuperAppBarber.servicecatalog.model.StaffServiceId;
import com.example.SuperAppBarber.servicecatalog.repository.ServiceRepository;
import com.example.SuperAppBarber.servicecatalog.repository.StaffServiceRepository;
import com.example.SuperAppBarber.servicecatalog.service.StaffServiceService;
import com.example.SuperAppBarber.staff.repository.StaffRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class StaffServiceServiceImpl implements StaffServiceService {

    private final StaffServiceRepository staffServiceRepository;
    private final ServiceRepository serviceRepository;
    private final StaffRepository staffRepository;

    @Override
    public List<StaffServiceResponse> assign(
            UUID salonId,
            UUID staffId,
            Long serviceId) {
        validateStaffAndService(salonId, staffId, serviceId);

        StaffServiceId id = new StaffServiceId();
        id.setStaffId(staffId);
        id.setServiceId(serviceId);

        if (!staffServiceRepository.existsByIdAndDeletedAtIsNull(id)) {
            StaffServiceEntity e = new StaffServiceEntity();
            e.setId(id);
            staffServiceRepository.save(e);
        }

        return getServicesByStaff(salonId, staffId);
    }

    @Override
    public List<StaffServiceResponse> unassign(
            UUID salonId,
            UUID staffId,
            Long serviceId) {
        validateStaffAndService(salonId, staffId, serviceId);

        staffServiceRepository.softDelete(staffId, serviceId);

        return getServicesByStaff(salonId, staffId);
    }

    @Override
    public List<StaffServiceResponse> getServicesByStaff(
            UUID salonId, UUID staffId) {
        validateStaff(salonId, staffId);

        return staffServiceRepository
                .findByIdStaffIdAndDeletedAtIsNull(staffId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private void validateStaff(UUID salonId, UUID staffId) {
        if (!staffRepository.existsByStaffIdAndSalonId(staffId, salonId)) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }
    }

    private void validateStaffAndService(
            UUID salonId, UUID staffId, Long serviceId) {
        validateStaff(salonId, staffId);

        if (!serviceRepository
                .findByServiceIdAndSalonIdAndDeletedAtIsNull(serviceId, salonId)
                .isPresent()) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }
    }

    private StaffServiceResponse toResponse(StaffServiceEntity e) {

        ServiceEntity s = serviceRepository
                .findById(e.getId().getServiceId())
                .orElseThrow();

        StaffServiceResponse r = new StaffServiceResponse();
        r.setStaffId(e.getId().getStaffId());
        r.setServiceId(s.getServiceId());
        r.setServiceName(s.getName());
        r.setPrice(s.getPrice());
        r.setDurationMinutes(s.getDurationMinutes());
        return r;
    }
}
