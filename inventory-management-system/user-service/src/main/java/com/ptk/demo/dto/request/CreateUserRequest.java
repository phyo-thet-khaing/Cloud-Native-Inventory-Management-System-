package com.ptk.demo.dto.request;

import jakarta.validation.constraints.*;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {
	
	@NotBlank(message="Username is required")
	@Size(max =100, message ="Username must not exceed 100 characters")
	private String username;
	
	@NotBlank(message ="Email is required")
	@Email(message ="Invalid email format")
	@Size(max =100 , message ="Email must not exceed 100 characters")
	private String email;
	
	@NotBlank(message = "Password is required")
    @Size(min = 8, max = 255, message = "Password must be between 8 and 255 characters")
    private String password;

    @Size(max = 20, message = "Phone must not exceed 20 characters")
    private String phone;

    @NotNull(message = "Role ID is required")
    private Long roleId;
}
