package com.demo.socialsync.Request;

import java.util.List;
import java.util.Set;

import com.demo.socialsync.Models.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDTO {

	@Email
	@NotBlank
	private String email;

	@NotBlank
	private String username;

	@NotBlank
	private String password;
	private String fotoPerfil;
	private String fotoPortada;
	private Set<String> roles;

}
