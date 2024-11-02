package com.lab3.hotel.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GuestEntity {
    private Long id;
    private String passportNumber;
    private String firstName;
    private String lastName;
    private String middleName;
    private String city;
    private OffsetDateTime checkInDate;
    private boolean isInhabited;
    private Long roomId;
}
