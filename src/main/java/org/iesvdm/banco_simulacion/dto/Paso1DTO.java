package org.iesvdm.banco_simulacion.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Paso1DTO {

    @NotNull(message= "Debe indicar la cuenta de origen")
    private long cuentaOrigenId;

    @NotEmpty(message = "Debe indicar el nombre del beneficiario")
    private String nombreBeneficiario;

    @NotEmpty(message = "Debe indicar el IBAN de destino")
    private String ibanDestino;

    @NotNull(message = "Debe indicar el importe")
    @Min(value = 1, message = "El importe debe ser al menos 1")
    private BigDecimal importe;

    @NotEmpty(message = "Debe indicar el concepto")
    private String concepto;

//    @NotEmpty(message = "Debe indicar la fecha programada")
    private LocalDateTime fechaProgramada;

}
