package com.example.SuperAppBarber.servicecatalog.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.SuperAppBarber.common.exception.BusinessException;
import com.example.SuperAppBarber.common.exception.enums.ErrorCode;
import com.example.SuperAppBarber.servicecatalog.dto.sdi.CreateServiceRequest;
import com.example.SuperAppBarber.servicecatalog.dto.sdi.UpdateServiceRequest;
import com.example.SuperAppBarber.servicecatalog.dto.sdo.ServiceResponse;
import com.example.SuperAppBarber.servicecatalog.model.ServiceEntity;
import com.example.SuperAppBarber.servicecatalog.repository.ServiceRepository;
import com.example.SuperAppBarber.servicecatalog.service.ServiceService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServiceServiceImpl implements ServiceService {

    private final ServiceRepository serviceRepository;

    @Override
    public ServiceResponse create(UUID salonId, CreateServiceRequest request) {

        ServiceEntity e = new ServiceEntity();
        e.setSalonId(salonId);
        e.setName(request.getName());
        e.setPrice(request.getPrice());
        e.setDurationMinutes(request.getDurationMinutes());

        serviceRepository.save(e);

        return toResponse(e);
    }

    @Override
    public ServiceResponse update(UUID salonId, Long serviceId, UpdateServiceRequest request) {

        ServiceEntity e = serviceRepository
                .findByServiceIdAndSalonIdAndDeletedAtIsNull(serviceId, salonId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND));

        if (request.getName() != null)
            e.setName(request.getName());
        if (request.getPrice() != null)
            e.setPrice(request.getPrice());
        if (request.getDurationMinutes() != null)
            e.setDurationMinutes(request.getDurationMinutes());
        if (request.getActive() != null)
            e.setActive(request.getActive());

        serviceRepository.save(e);

        return toResponse(e);
    }

    @Override
    public void delete(UUID salonId, Long serviceId) {

        ServiceEntity e = serviceRepository
                .findByServiceIdAndSalonIdAndDeletedAtIsNull(serviceId, salonId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND));

        e.setDeletedAt(LocalDateTime.now());
        serviceRepository.save(e);
    }

    @Override
    public List<ServiceResponse> getAll(UUID salonId) {

        return serviceRepository.findBySalonIdAndDeletedAtIsNull(salonId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private ServiceResponse toResponse(ServiceEntity e) {
        ServiceResponse r = new ServiceResponse();
        r.setServiceId(e.getServiceId());
        r.setName(e.getName());
        r.setPrice(e.getPrice());
        r.setDurationMinutes(e.getDurationMinutes());
        r.setActive(e.getActive());
        return r;
    }
}
