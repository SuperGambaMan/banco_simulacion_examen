package org.iesvdm.banco_simulacion.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Paso1DTO {

    @NotNull(message="Debes seleccionar una cuenta")
    private Long cuentaOrigenId;

    @NotBlank(message = "No puedes dejar vacío el Nombre de Beneficiario")
    private String nombreBeneficiario;

    @Size(min=1, max=29, message = "No se puede poner más de 24 dígitos")
    private String ibanDestino;

    @NotNull(message = "No puedes dejarlo vacío el importe")
    @Min(value=1, message= "Tienes que poner una cifra mínimo")
    private BigDecimal importe;

    @Size(min = 1, max = 50, message = "El concepto solo puede tener 50 caracteres")
    private String concepto;

    @FutureOrPresent(message = "Debes de ser mas adelante de ahora mismo")
    @NotNull(message = "Debes introducir una fecha")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime fechaProgramada;

}
