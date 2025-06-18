package com.skillswap.server.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ProfileImageDTO {

    private int id;
    private String imageUrl;
    private String publicId;
}
