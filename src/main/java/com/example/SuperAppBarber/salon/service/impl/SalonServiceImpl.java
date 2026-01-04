package com.example.SuperAppBarber.salon.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.SuperAppBarber.common.exception.BusinessException;
import com.example.SuperAppBarber.common.exception.enums.ErrorCode;
import com.example.SuperAppBarber.salon.dto.sdi.CreateSalonRequest;
import com.example.SuperAppBarber.salon.dto.sdi.UpdateSalonRequest;
import com.example.SuperAppBarber.salon.dto.sdo.SalonResponse;
import com.example.SuperAppBarber.salon.guard.SalonPermissionService;
import com.example.SuperAppBarber.salon.mapper.SalonMapper;
import com.example.SuperAppBarber.salon.model.SalonEntity;
import com.example.SuperAppBarber.salon.repository.SalonRepository;
import com.example.SuperAppBarber.salon.service.SalonService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SalonServiceImpl implements SalonService {

    private final SalonRepository salonRepository;
    private final SalonPermissionService permissionService;

    @Override
    public SalonResponse create(CreateSalonRequest request, UUID ownerId) {

        SalonEntity salon = new SalonEntity();
        // salon.setSalonId(UUID.randomUUID());
        salon.setSalonName(request.getSalonName());
        salon.setAddress(request.getAddress());
        salon.setPhone(request.getPhone());
        salon.setOwnerId(ownerId);
        salon.setCreatedAt(LocalDateTime.now());

        return SalonMapper.toResponse(salonRepository.save(salon));
    }

    @Override
    public SalonResponse update(UUID salonId, UpdateSalonRequest request, UUID ownerId) {

        SalonEntity salon = permissionService.checkOwner(salonId, ownerId);

        if (request.getSalonName() != null)
            salon.setSalonName(request.getSalonName());

        if (request.getAddress() != null)
            salon.setAddress(request.getAddress());

        if (request.getPhone() != null)
            salon.setPhone(request.getPhone());

        salon.setUpdatedAt(LocalDateTime.now());

        return SalonMapper.toResponse(salonRepository.save(salon));
    }

    @Override
    public SalonResponse getSalon(UUID ownerId, UUID salonID) {
        return salonRepository.findByOwnerIdAndSalonId(ownerId, salonID)
                .map(SalonMapper::toResponse)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND));
    }

    @Override
    public List<SalonResponse> getMySalons(UUID ownerId) {
        return salonRepository.findAllByOwnerId(ownerId)
                .stream()
                .map(SalonMapper::toResponse)
                .toList();
    }
}
