package org.iesvdm.banco_simulacion.service;


import lombok.extern.slf4j.Slf4j;
import org.iesvdm.banco_simulacion.model.CuentaOrigen;
import org.iesvdm.banco_simulacion.model.Transferencia;
import org.iesvdm.banco_simulacion.repository.CuentaOrigenRepository;
import org.iesvdm.banco_simulacion.repository.TransferenciaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
public class CuentaService {


    private final CuentaOrigenRepository cuentaOrigenRepository;
    private final TransferenciaRepository transferenciaRepository;

    public CuentaService(CuentaOrigenRepository cuentaOrigenRepository, TransferenciaRepository transferenciaRepository) {
        this.cuentaOrigenRepository = cuentaOrigenRepository;
        this.transferenciaRepository = transferenciaRepository;
    }

    //CuentaOrigen
    // Obtener todos los cuentaOrigen
    public List<CuentaOrigen> getAllCuentas() {
        return cuentaOrigenRepository.findAll();
    }

    // Obtener un cuentaOrigen por su ID
    public CuentaOrigen findCuentaById(int id) {
        return cuentaOrigenRepository.findById(id);
    }

    // Guardar una inscripción en la base de datos
    public void guardarTransferencia(Transferencia inscripcion) {
        transferenciaRepository.save(inscripcion);
    }

    // Listar todas las inscripciones
    public List<Transferencia> listarTransferencias() {
        return transferenciaRepository.findAll();
    }

    //Admin
    public Transferencia findTransferenciaById(long id) {
        return transferenciaRepository.findById(id);
    }

    public void editarTransferencia(long id, Transferencia nuevaTransferencia) {
        nuevaTransferencia.setId(id);
        CuentaOrigen cuentaOrigen = findCuentaById((int) nuevaTransferencia.getCuentaOrigenId());
        nuevaTransferencia.setNombreBeneficiario(nuevaTransferencia.getNombreBeneficiario());
        //..AÑADIR LOS DEMAS CAMPOS
        transferenciaRepository.update(nuevaTransferencia);
    }

    public void eliminarTransferencia(int id) {
        transferenciaRepository.deleteById(id);
    }

}