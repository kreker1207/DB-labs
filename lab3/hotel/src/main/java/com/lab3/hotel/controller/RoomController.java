package com.lab3.hotel.controller;

import com.lab3.hotel.model.dto.request.RoomFilter;
import com.lab3.hotel.model.dto.request.RoomSaveRequestDto;
import com.lab3.hotel.model.dto.response.RoomResponseDto;
import com.lab3.hotel.model.dto.response.RoomShortResponseDto;
import com.lab3.hotel.model.dto.response.RoomTypeResponseDto;
import com.lab3.hotel.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;
    @PostMapping()
    public RoomResponseDto createRoom(@RequestBody RoomSaveRequestDto roomSaveRequestDto) {
        return roomService.createRoom(roomSaveRequestDto);
    }

    @GetMapping("/{id}")
    public RoomResponseDto getRoomById(@PathVariable("id") long roomId) {
        return roomService.getRoomById(roomId);
    }

    @GetMapping()
    public List<RoomShortResponseDto> getAllRooms(RoomFilter filter) {
        return roomService.getAllRooms(filter);
    }
    @GetMapping("/type/{id}")
    public RoomTypeResponseDto getRoomTypeById(@PathVariable("id") long roomTypeId) {
        return roomService.getRoomTypeById(roomTypeId);
    }

    @GetMapping("/type")
    public List<RoomTypeResponseDto> getAllRoomTypes() {
        return roomService.getAllRoomTypes();
    }
}
