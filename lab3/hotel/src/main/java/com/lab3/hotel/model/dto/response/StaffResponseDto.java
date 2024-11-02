package com.lab3.hotel.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StaffResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
}
