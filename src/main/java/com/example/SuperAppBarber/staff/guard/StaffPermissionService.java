package com.example.SuperAppBarber.staff.guard;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.SuperAppBarber.common.exception.BusinessException;
import com.example.SuperAppBarber.common.exception.enums.ErrorCode;
import com.example.SuperAppBarber.staff.model.StaffEntity;
import com.example.SuperAppBarber.staff.repository.StaffRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StaffPermissionService {

    private final StaffRepository staffRepository;

    public StaffEntity checkOwner(UUID staffId, UUID ownerId, UUID salonId) {

        StaffEntity staff = staffRepository.findById(staffId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND));

        if (!staff.getSalonId().equals(salonId)) {
            throw new BusinessException(ErrorCode.FORBIDDEN);
        }

        return staff;
    }
}
