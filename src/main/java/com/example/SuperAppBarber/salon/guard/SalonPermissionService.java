package com.example.SuperAppBarber.salon.guard;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.SuperAppBarber.common.exception.BusinessException;
import com.example.SuperAppBarber.common.exception.enums.ErrorCode;
import com.example.SuperAppBarber.salon.model.SalonEntity;
import com.example.SuperAppBarber.salon.repository.SalonRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SalonPermissionService {

    private final SalonRepository salonRepository;

    public SalonEntity checkOwner(UUID salonId, UUID userId) {
        SalonEntity salon = salonRepository.findById(salonId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND));

        if (!salon.getOwnerId().equals(userId)) {
            throw new BusinessException(ErrorCode.FORBIDDEN);
        }

        return salon;
    }
}
