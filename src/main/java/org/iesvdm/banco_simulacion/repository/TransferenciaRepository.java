package org.iesvdm.banco_simulacion.repository;

import org.iesvdm.banco_simulacion.model.Transferencia;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TransferenciaRepository {

    private final JdbcTemplate jdbc;

    public TransferenciaRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    // Guardar una nueva inscripción
    public void save(Transferencia transferencia) {
        String sql = "INSERT INTO transferencia_programada (id, cuenta_origen_id, nombre_beneficiario, iban_destino, importe, concepto, fecha_programada, fecha_creacion, estado) " +
                "VALUES (?, ?, ?, ?, ? ,? ,? ,? ,? )";
        jdbc.update(sql,
                transferencia.getId(),
                transferencia.getCuentaOrigenId(),
                transferencia.getNombreBeneficiario(),
                transferencia.getIbanDestino(),
                transferencia.getImporte(),
                transferencia.getConcepto(),
                transferencia.getFechaProgramada(),
                transferencia.getFechaCreacion(),
                transferencia.getEstado()
        );
    }

    // Listar todas las transferenciaes
    public List<Transferencia> findAll() {
        String sql = "SELECT * FROM transferencia_programada";
        return jdbc.query(sql, new RowMapper<Transferencia>() {
            @Override
            public Transferencia mapRow(ResultSet rs, int rowNum) throws SQLException {
                return Transferencia.builder()
                        .id(rs.getInt("id"))
                        .cuentaOrigenId(rs.getInt("cuenta_origen_id"))
                        .nombreBeneficiario(rs.getString("nombre_beneficiario"))
                        .ibanDestino(rs.getString("iban_destino"))
                        .importe(rs.getBigDecimal("importe"))
                        .concepto(rs.getString("concepto"))
                        .fechaProgramada(rs.getTimestamp("fecha_programada").toLocalDateTime())
                        .fechaCreacion(rs.getTimestamp("fecha_creacion").toLocalDateTime())
                        .estado(rs.getString("estado"))
                        .build();
            }
        });
    }

    //Admin

    // Obtener inscripción por id
    public Transferencia findById(long id) {
        String sql = "SELECT * FROM transferencia_programada WHERE id = ?";
        return jdbc.queryForObject(sql, new Object[]{id}, (rs, rowNum) ->
                Transferencia.builder()
                        .id(rs.getInt("id"))
                        .cuentaOrigenId(rs.getInt("cuenta_origen_id"))
                        .nombreBeneficiario(rs.getString("nombre_beneficiario"))
                        .ibanDestino(rs.getString("iban_destino"))
                        .importe(rs.getBigDecimal("importe"))
                        .concepto(rs.getString("concepto"))
                        .fechaProgramada(rs.getTimestamp("fecha_programada").toLocalDateTime())
                        .fechaCreacion(rs.getTimestamp("fecha_creacion").toLocalDateTime())
                        .estado(rs.getString("estado"))
                        .build()
        );
    }
    // Actualizar inscripción existente
    public void update(Transferencia transferencia) {
        String sql = "UPDATE transferencia_programada SET cuenta_origen_id = ?, nombre_beneficiario = ?, iban_destino = ?, importe = ?, concepto = ?, fecha_programada = ?, fecha_creacion = ?, estado = ?" +
                "WHERE id = ?";
        jdbc.update(sql,

                transferencia.getCuentaOrigenId(),
                transferencia.getNombreBeneficiario(),
                transferencia.getIbanDestino(),
                transferencia.getImporte(),
                transferencia.getConcepto(),
                transferencia.getFechaProgramada(),
                transferencia.getFechaCreacion(),
                transferencia.getEstado(),
                transferencia.getId()
        );
    }
    // Borrar inscripción por id
    public void deleteById(long id) {
        String sql = "DELETE FROM transferencia_programada WHERE id = ?";
        jdbc.update(sql, id);
    }


}