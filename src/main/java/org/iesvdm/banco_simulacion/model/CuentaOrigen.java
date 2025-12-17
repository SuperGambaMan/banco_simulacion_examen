package org.iesvdm.banco_simulacion.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CuentaOrigen {
    private Long id;
    private String aliasCuenta;
    private String iban;
}
