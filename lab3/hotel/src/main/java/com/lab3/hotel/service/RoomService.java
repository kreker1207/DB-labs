package com.lab3.hotel.service;

import com.lab3.hotel.model.dto.request.RoomFilter;
import com.lab3.hotel.model.dto.request.RoomSaveRequestDto;
import com.lab3.hotel.model.dto.response.RoomResponseDto;
import com.lab3.hotel.model.dto.response.RoomShortResponseDto;
import com.lab3.hotel.model.dto.response.RoomTypeResponseDto;
import com.lab3.hotel.model.entity.RoomEntity;
import com.lab3.hotel.repository.RoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;

    @Transactional
    public RoomResponseDto getRoomById(Long roomId) {
        Optional<RoomEntity> maybeRoomId = roomRepository.getRoomById(roomId);
        if (maybeRoomId.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Room was not found by id: " + roomId);
        }
        RoomEntity room = maybeRoomId.get();
        int roomTypeId = room.getRoomTypeId();
        RoomTypeResponseDto roomType = getRoomTypeById(roomTypeId);
        return RoomResponseDto.builder()
                .id(room.getId())
                .floor(room.getFloor())
                .type(roomType)
                .isAvailable(room.getIsAvailable())
                .number(room.getRoomNumber())
                .phoneNumber(room.getPhoneNumber())
                .pricePerDay(room.getPricePerDay())
                .build();
    }

    public boolean isRoomExistsById(long roomId) {
        return roomRepository.isRoomExistsById(roomId);
    }

    public void checkIn(long roomId) {
        roomRepository.checkIn(roomId, false);
    }

    public void checkOut(Long roomId) {
        roomRepository.checkIn(roomId, true);
    }

    @Transactional
    public RoomResponseDto createRoom(RoomSaveRequestDto roomSaveRequestDto) {
        Optional<Long> createdRoomId = roomRepository.createRoom(roomSaveRequestDto);
        if (createdRoomId.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Room wasn't saved");
        }
        Long roomId = createdRoomId.get();
        return getRoomById(roomId);
    }

    public List<RoomShortResponseDto> getAllRooms(RoomFilter filter) {
        return roomRepository.getAllRooms(filter);
    }

    public RoomTypeResponseDto getRoomTypeById(long roomTypeId) {
        Optional<RoomTypeResponseDto> maybeRoomType = roomRepository.getRoomTypeById(roomTypeId);
        if (maybeRoomType.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Room type was not found by id: " + roomTypeId);
        }
        return maybeRoomType.get();
    }

    public List<RoomTypeResponseDto> getAllRoomTypes() {
        return roomRepository.getAllRoomTypes();
    }
}
