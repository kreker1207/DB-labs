package com.lab3.hotel.service;

import com.lab3.hotel.model.dto.request.GuestFilter;
import com.lab3.hotel.model.dto.request.GuestSaveRequestDto;
import com.lab3.hotel.model.dto.response.GuestResponseDto;
import com.lab3.hotel.model.dto.response.GuestShortResponseDto;
import com.lab3.hotel.model.dto.response.RoomResponseDto;
import com.lab3.hotel.model.entity.GuestEntity;
import com.lab3.hotel.repository.GuestRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GuestService {
    private final GuestRepository guestRepository;
    private final RoomService roomService;

    @Transactional
    public GuestResponseDto createGuest(GuestSaveRequestDto guestSaveRequestDto) {
        boolean isValid = isValidRequest(guestSaveRequestDto);
        if (!isValid) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Guest save request is invalid, check fields");
        }
        Optional<Long> maybeSavedId = guestRepository.saveGuest(guestSaveRequestDto);
        if (maybeSavedId.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Guest wasn't saved");
        }
        return getGuestById(maybeSavedId.get());
    }

    private boolean isValidRequest(GuestSaveRequestDto guestSaveRequestDto) {
        if (guestSaveRequestDto == null) {
            return false;
        }
        if (guestSaveRequestDto.getPassportNumber() == null || guestSaveRequestDto.getPassportNumber().isEmpty()) {
            return false;
        }
        if (guestSaveRequestDto.getFirstName() == null || guestSaveRequestDto.getFirstName().isEmpty()) {
            return false;
        }
        return guestSaveRequestDto.getLastName() != null && !guestSaveRequestDto.getLastName().isEmpty();
    }


    @Transactional
    public GuestResponseDto checkInGuestById(long guestId, long roomId) {
        boolean isExistsById = guestRepository.isExistsBtId(guestId);
        if (!isExistsById) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Guest was not found by id: " + guestId);
        }
        boolean isRoomExists = roomService.isRoomExistsById(roomId);
        if (!isRoomExists) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Room was not found by id: " + roomId);
        }
        checkIn(guestId, roomId);
        return getGuestById(guestId);
    }

    private void checkIn(long guestId, long roomId) {
        guestRepository.checkInGuestById(guestId, roomId);
        roomService.checkIn(roomId);
    }

    public GuestResponseDto getGuestById(long guestId) {
        Optional<GuestEntity> maybeGuestEntity = guestRepository.getGuestById(guestId);
        if (maybeGuestEntity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Guest was not found by id: " + guestId);
        }
        GuestEntity guestEntity = maybeGuestEntity.get();
        Long roomId = guestEntity.getRoomId();
        RoomResponseDto roomResponseDto = new RoomResponseDto();
        if (roomId != null) {
            roomResponseDto = roomService.getRoomById(roomId);
        }
        return GuestResponseDto.builder()
                .id(guestEntity.getId())
                .city(guestEntity.getCity())
                .room(roomResponseDto)
                .passportNumber(guestEntity.getPassportNumber())
                .checkInDate(guestEntity.getCheckInDate())
                .isInhabited(guestEntity.getIsInhabited())
                .firstName(guestEntity.getFirstName())
                .lastName(guestEntity.getLastName())
                .middleName(guestEntity.getMiddleName())
                .build();
    }

    public List<GuestShortResponseDto> getAllGuests(GuestFilter filter) {
        return guestRepository.getAllGuests(filter);
    }

    @Transactional
    public GuestResponseDto checkOutGuestById(long guestId) {
        GuestResponseDto guest = getGuestById(guestId);
        RoomResponseDto room = guest.getRoom();
        checkOut(guestId, room);
        return getGuestById(guestId);
    }

    private void checkOut(long guestId, RoomResponseDto room) {
        guestRepository.checkOutGuest(guestId);
        if (room.getId() != null) {
            roomService.checkOut(room.getId());
        }
    }
}
