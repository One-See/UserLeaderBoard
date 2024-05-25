package com.crio.board.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateUserDTO {

    @NotBlank(message = "Please provide a unique userId")
    @Size(min = 4, max = 10)
    private String userId;

    @NotBlank(message = "Please provide user name")
    @Size(min = 4, max = 15)
    private String userName;

}
