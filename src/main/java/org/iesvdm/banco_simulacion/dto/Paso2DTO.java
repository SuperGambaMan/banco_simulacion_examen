package org.iesvdm.banco_simulacion.dto;

import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "El código es obligatorio")
    @Pattern(regexp = "^\\d{4}$", message = "El código debe tener exactamente 4 dígitos (0000-9999)")
    private String codigo;
}
