package com.lab3.hotel.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GuestSaveRequestDto {
    private String passportNumber;
    private String firstName;
    private String lastName;
    private String middleName;
    private String city;
    private boolean isInhabited;
    private Long roomId;
}
