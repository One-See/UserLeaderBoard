package com.crio.board.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserScoreDTO {

    @Max(100)
    @Min(0)
    private int score;

    @Size(min = 4, max = 15)
    @NotBlank(message = "Please provide the unique userId")
    private String userId;
}
