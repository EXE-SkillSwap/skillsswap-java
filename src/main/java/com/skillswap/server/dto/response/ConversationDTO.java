package com.skillswap.server.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConversationDTO {

    int id;
    String title;
    Date createdAt;
    List<ParticipantDTO> participants;
}
