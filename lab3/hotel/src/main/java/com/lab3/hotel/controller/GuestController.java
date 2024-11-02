package com.lab3.hotel.controller;

import com.lab3.hotel.model.dto.request.GuestFilter;
import com.lab3.hotel.model.dto.request.GuestSaveRequestDto;
import com.lab3.hotel.model.dto.response.GuestResponseDto;
import com.lab3.hotel.model.dto.response.GuestShortResponseDto;
import com.lab3.hotel.service.GuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/guest")
@RequiredArgsConstructor
public class GuestController {
    private final GuestService guestService;

    @PostMapping()
    public GuestResponseDto createGuest(@RequestBody GuestSaveRequestDto guestSaveRequestDto) {
        return guestService.createGuest(guestSaveRequestDto);
    }

    @PutMapping("/{guestId}/room/{roomId}")
    public GuestResponseDto checkInGuestById(@PathVariable("guestId") long guestId, @PathVariable("roomId") long roomId) {
        return guestService.checkInGuestById(guestId,roomId);
    }
    @PutMapping("/{guestId}")
    public GuestResponseDto checkOutGuestById(@PathVariable("guestId") long guestId) {
        return guestService.checkOutGuestById(guestId);
    }


    @GetMapping("/{id}")
    public GuestResponseDto getGuestById(@PathVariable("id") long guestId) {
        return guestService.getGuestById(guestId);
    }

    @GetMapping()
    public List<GuestShortResponseDto> getAllGuests(@RequestBody GuestFilter filter) {
        return guestService.getAllGuests(filter);
    }


}
