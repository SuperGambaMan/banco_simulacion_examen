package org.iesvdm.banco_simulacion.repository;


import org.iesvdm.banco_simulacion.model.CuentaOrigen;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class CuentaOrigenRepository {
    private final JdbcTemplate jdbc;

    public CuentaOrigenRepository(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    public List<CuentaOrigen> findAll() {
        return jdbc.query("SELECT * FROM cuenta_origen", (rs, rowNum) ->
                new CuentaOrigen(
                        rs.getLong("id"),
                        rs.getString("alias_cuenta"),
                        rs.getString("iban")
                )
        );
    }

    public CuentaOrigen findById(long id) {
        return jdbc.queryForObject("SELECT * FROM cuenta_origen WHERE id = ?",
                new Object[]{id},
                (rs, rowNum) ->
                        new CuentaOrigen(
                                rs.getInt("id"),
                                rs.getString("alias_cuenta"),
                                rs.getString("iban")
                        )
        );
    }
}
