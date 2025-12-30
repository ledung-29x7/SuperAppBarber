package com.example.SuperAppBarber.user.dto.sdi;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminUpdateUserRequest {

    private String role; // ADMIN / OWNER / STAFF / CUSTOMER
    private String status; // ACTIVE / BLOCKED
}
