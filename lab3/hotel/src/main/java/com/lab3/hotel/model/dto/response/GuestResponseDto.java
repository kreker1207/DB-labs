package com.lab3.hotel.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GuestResponseDto {
    private Long id;
    private String passportNumber;
    private String firstName;
    private String lastName;
    private String middleName;
    private String city;
    private OffsetDateTime checkInDate;
    private boolean isInhabited;
    private RoomResponseDto room;
}
