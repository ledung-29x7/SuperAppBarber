package com.example.SuperAppBarber.servicecatalog.service;

import java.util.List;
import java.util.UUID;

import com.example.SuperAppBarber.servicecatalog.dto.sdo.StaffServiceResponse;

public interface StaffServiceService {

    List<StaffServiceResponse> assign(UUID salonId, UUID staffId, Long serviceId);

    List<StaffServiceResponse> unassign(UUID salonId, UUID staffId, Long serviceId);

    List<StaffServiceResponse> getServicesByStaff(UUID salonId, UUID staffId);
}
