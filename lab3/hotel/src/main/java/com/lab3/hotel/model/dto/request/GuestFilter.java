package com.lab3.hotel.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GuestFilter {
    private String guestName;
    private String city;
    private Boolean isInhabited;
    private Long roomId;
}
