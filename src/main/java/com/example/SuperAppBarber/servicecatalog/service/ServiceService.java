package com.example.SuperAppBarber.servicecatalog.service;

import java.util.List;
import java.util.UUID;

import com.example.SuperAppBarber.servicecatalog.dto.sdi.CreateServiceRequest;
import com.example.SuperAppBarber.servicecatalog.dto.sdi.UpdateServiceRequest;
import com.example.SuperAppBarber.servicecatalog.dto.sdo.ServiceResponse;

public interface ServiceService {

    ServiceResponse create(UUID salonId, CreateServiceRequest request);

    ServiceResponse update(UUID salonId, Long serviceId, UpdateServiceRequest request);

    void delete(UUID salonId, Long serviceId);

    List<ServiceResponse> getAll(UUID salonId);
}
