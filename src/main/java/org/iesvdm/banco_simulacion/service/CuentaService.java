package org.iesvdm.banco_simulacion.service;

import org.iesvdm.banco_simulacion.model.CuentaOrigen;
import org.iesvdm.banco_simulacion.model.TransferenciaProgramada;
import org.iesvdm.banco_simulacion.repository.CuentaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CuentaService {

    private final CuentaRepository cuentaRepository;


    public CuentaService(CuentaRepository cuentaRepository) {
        this.cuentaRepository = cuentaRepository;
    }

    //Cuenta_Origen
    public List<CuentaOrigen> listarCuentas(){
        return cuentaRepository.findAllCuentas();
    }

    public  CuentaOrigen buscarCuentaId(Long id){
        return cuentaRepository.findCuentaById(id);
    }

    //Transferencia_programada
    public List<TransferenciaProgramada> listarTransferencias(){
        return cuentaRepository.findAllTransferencias();
    }

    public  TransferenciaProgramada buscarTransferenciaId(Long id){
        return cuentaRepository.findTransferenciaById(id);
    }

    //CRUD de Transferencia
    public void crearTransferencia (TransferenciaProgramada transferenciaProgramada){
        cuentaRepository.crearTrans(transferenciaProgramada);
    }

    public void editarTransferencia (TransferenciaProgramada transferenciaProgramada){
        cuentaRepository.editarTrans(transferenciaProgramada);
    }

    public void eliminarTransferencia (Long id){
        cuentaRepository.eliminarTrans(id);
    }
}
