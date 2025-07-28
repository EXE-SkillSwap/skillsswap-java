package com.skillswap.server.dto.response;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MembershipDTO {

    private int id;
    private String name;
    @Max(value = 365, message = "Duration must be less than 365 days")
    @Min(value = 1, message = "Duration must be greater than 0")
    private int duration;
    @Min(value = 1, message = "Price must be greater than 0")
    private BigDecimal price;
    private String description;
    private boolean isBought;
}
