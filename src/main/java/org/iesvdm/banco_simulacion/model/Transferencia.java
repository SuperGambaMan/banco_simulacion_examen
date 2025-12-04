package org.iesvdm.banco_simulacion.model;

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
public class Transferencia {

    long id;

    long cuentaOrigenId;

    String nombreBeneficiario;

    String ibanDestino;

    BigDecimal importe;

    String concepto;

    LocalDateTime fechaProgramada;

    LocalDateTime fechaCreacion;

    String estado;


}
