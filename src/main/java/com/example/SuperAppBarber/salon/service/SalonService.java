package com.example.SuperAppBarber.salon.service;

import java.util.List;
import java.util.UUID;

import com.example.SuperAppBarber.salon.dto.sdi.CreateSalonRequest;
import com.example.SuperAppBarber.salon.dto.sdi.UpdateSalonRequest;
import com.example.SuperAppBarber.salon.dto.sdo.SalonResponse;

public interface SalonService {

    SalonResponse create(CreateSalonRequest request, UUID ownerId);

    SalonResponse update(UUID salonId, UpdateSalonRequest request, UUID ownerId);

    SalonResponse getSalon(UUID ownerId, UUID salonId);

    List<SalonResponse> getMySalons(UUID ownerId);
}
