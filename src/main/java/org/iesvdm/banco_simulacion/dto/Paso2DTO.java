package org.iesvdm.banco_simulacion.dto;

import jakarta.validation.OverridesAttribute;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Paso2DTO {

    @NotBlank (message = "El codigo es obligatorio")
    @Pattern(message = "El codigo debe ser 123456", regexp = "^[0-9]{6}$")
    private int codigoConfirmacion;

}
