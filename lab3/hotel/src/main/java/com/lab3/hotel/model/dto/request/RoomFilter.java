package com.lab3.hotel.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomFilter {
    private Integer floor;
    private Boolean isAvailable;
    private Integer roomTypeId;

}
