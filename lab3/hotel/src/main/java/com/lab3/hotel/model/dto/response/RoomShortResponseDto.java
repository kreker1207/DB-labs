package com.lab3.hotel.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomShortResponseDto {
    private Long id;
    private int number;
    private BigDecimal pricePerDay;
    private String phoneNumber;
    private int floor;
    private Boolean isAvailable;
    private String type;
}
