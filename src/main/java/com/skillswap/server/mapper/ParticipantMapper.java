package com.skillswap.server.mapper;

import com.skillswap.server.dto.response.ParticipantDTO;
import com.skillswap.server.entities.Participant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ParticipantMapper {

    private final UserMapper userMapper;

    public ParticipantDTO toParticipantDTO(Participant participant) {
        if (participant == null) {
            return null;
        }

        ParticipantDTO participantDTO = new ParticipantDTO();
        participantDTO.setId(participant.getId());
        participantDTO.setUser(userMapper.chatUserDTO(participant.getUser()));

        return participantDTO;
    }
}
