package com.lab3.hotel.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GuestShortResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String passportNumber;
    private boolean isInhabited;
}
