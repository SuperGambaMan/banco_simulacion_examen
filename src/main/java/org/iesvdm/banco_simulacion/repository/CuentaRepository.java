package org.iesvdm.banco_simulacion.repository;

import org.iesvdm.banco_simulacion.model.CuentaOrigen;
import org.iesvdm.banco_simulacion.model.TransferenciaProgramada;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class CuentaRepository {

    private final JdbcTemplate jdbcTemplate;

    CuentaRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    // Mostrar todas las cuentas
    public List<CuentaOrigen> findAllCuentas(){
        String sql= """
                SELECT * FROM cuenta_origen;
                """;
        List<CuentaOrigen> cuentas = jdbcTemplate.query(sql,
                (rs, rowNum) -> new CuentaOrigen(
                        rs.getLong("id"),
                        rs.getString("alias_cuenta"),
                        rs.getString("iban")
                ));
        return cuentas;
    }

    // Mostrar todas las transferencias
    public List<TransferenciaProgramada> findAllTransferencias(){
        String sql= """
                SELECT * FROM transferencia_programada;
                """;
        List<TransferenciaProgramada> transferencias = jdbcTemplate.query(sql,
                (rs, rowNum) -> new TransferenciaProgramada(
                        rs.getLong("id"),
                        rs.getLong("cuenta_origen_id"),
                        rs.getString("nombre_beneficiario"),
                        rs.getString("iban_destino"),
                        rs.getBigDecimal("importe"),
                        rs.getString("concepto"),
                        rs.getTimestamp("fecha_programada").toLocalDateTime(),
                        rs.getTimestamp("fecha_creacion").toLocalDateTime(),
                        rs.getString("estado")
                ));
        return transferencias;
    }

    //Encontrar Cuenta por ID
    public CuentaOrigen findCuentaById (Long id){
        String sql = """
                SELECT * FROM cuenta_origen
                WHERE id = ?
                """;
        return jdbcTemplate.queryForObject(sql,
                new Object[] {id},
                (rs, rowNum) -> new CuentaOrigen(
                        rs.getLong("id"),
                        rs.getString("alias_cuenta"),
                        rs.getString("iban")
                ));

    }

    //Encontrar Transferencia por ID
    public TransferenciaProgramada findTransferenciaById(Long id){
    String sql = """
                SELECT * FROM transferencia_programada
                WHERE id = ?
                """;
        return jdbcTemplate.queryForObject(sql,
            new Object[] {id},
            (rs, rowNum) -> new TransferenciaProgramada(
                    rs.getLong("id"),
                    rs.getLong("cuenta_origen_id"),
                    rs.getString("nombre_beneficiario"),
                    rs.getString("iban_destino"),
                    rs.getBigDecimal("importe"),
                    rs.getString("concepto"),
                    rs.getTimestamp("fecha_programada").toLocalDateTime(),
                    rs.getTimestamp("fecha_creacion").toLocalDateTime(),
                    rs.getString("estado")
            ));
    }

    // Crear Transferencia
    public void crearTrans (TransferenciaProgramada transferenciaProgramada){
        String sql = """
                INSERT INTO transferencia_programada (cuenta_origen_id, nombre_beneficiario, iban_destino,
                                                      importe, concepto, fecha_programada,fecha_creacion, estado)
                                                      VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String [] ids={"id"};

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql,ids);
            ps.setLong(1, transferenciaProgramada.getCuentaOrigenId());
            ps.setString(2, transferenciaProgramada.getNombreBeneficiario());
            ps.setString(3, transferenciaProgramada.getIbanDestino());
            ps.setBigDecimal(4, transferenciaProgramada.getImporte());
            ps.setString(5, transferenciaProgramada.getConcepto());
            ps.setTimestamp(6, Timestamp.valueOf(transferenciaProgramada.getFechaProgramada()));
            ps.setTimestamp(7, Timestamp.valueOf(transferenciaProgramada.getFechaCreacion()));
            ps.setString(8, transferenciaProgramada.getEstado());
            return ps;
        }, keyHolder);
        transferenciaProgramada.setId(keyHolder.getKey().longValue());
    }

    // Editar Transferencia
    public void editarTrans (TransferenciaProgramada transferenciaProgramada){
        String sql = """
                UPDATE transferencia_programada SET cuenta_origen_id=?, nombre_beneficiario=?, iban_destino=?,
                    importe=?, concepto=?, fecha_programada=?,fecha_creacion=?, estado=? 
                WHERE id = ?;
                """;

        jdbcTemplate.update(sql,
                transferenciaProgramada.getCuentaOrigenId(),
                transferenciaProgramada.getNombreBeneficiario(),
                transferenciaProgramada.getIbanDestino(),
                transferenciaProgramada.getImporte(),
                transferenciaProgramada.getConcepto(),
                transferenciaProgramada.getFechaProgramada(),
                transferenciaProgramada.getFechaCreacion(),
                transferenciaProgramada.getEstado(),
                transferenciaProgramada.getId()
                );
    }

    //Eliminar Transferencia
    public void eliminarTrans(Long id){
        String sql= """
                DELETE FROM transferencia_programada
                WHERE id = ?;
                """;
        jdbcTemplate.update(sql,id);
    }
}