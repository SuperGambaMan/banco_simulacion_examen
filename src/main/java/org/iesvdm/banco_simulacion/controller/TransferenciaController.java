package org.iesvdm.banco_simulacion.controller;


import jakarta.validation.Valid;
import org.iesvdm.banco_simulacion.dto.Paso1DTO;
import org.iesvdm.banco_simulacion.dto.Paso2DTO;
import org.iesvdm.banco_simulacion.model.CuentaOrigen;
import org.iesvdm.banco_simulacion.model.Transferencia;
import org.iesvdm.banco_simulacion.repository.CuentaOrigenRepository;
import org.iesvdm.banco_simulacion.service.CuentaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/transferencias/transferir")
@SessionAttributes("transferencia") //Guardar la transferencia de manera automatica
public class TransferenciaController {

    private final CuentaService service;
    private final CuentaOrigenRepository cuentaOrigenRepository;

    public TransferenciaController(CuentaService service, CuentaOrigenRepository cuentaOrigenRepository) {
        this.service = service;
        this.cuentaOrigenRepository = cuentaOrigenRepository;
    }

    // Inicializamos el objeto de sesión
    @ModelAttribute("transferencia")
    public Transferencia initCompra() {
        return new Transferencia();
    }

    //Paso 1: mostramos los cuentas
    @GetMapping("/paso1")
    public String paso1(Model model, @ModelAttribute("transferencia") Transferencia transferencia) {
        List<CuentaOrigen> cuentas = service.getAllCuentas();

        // Rellenamos el DTO desde el objeto de sesión
        Paso1DTO paso1DTO = new Paso1DTO();
        paso1DTO.setCuentaOrigenId(transferencia.getCuentaOrigenId());
        paso1DTO.setNombreBeneficiario(transferencia.getNombreBeneficiario());
        paso1DTO.setIbanDestino(transferencia.getIbanDestino());

        model.addAttribute("cuentas", cuentas);
        model.addAttribute("paso1DTO", paso1DTO);
        return "paso1";
    }

    // Paso 1: procesar selección de cuenta y datos de la transferencia
    @PostMapping("/paso1")
    public String procesarPaso1(
            @ModelAttribute("transferencia") Transferencia transferencia,
            @Valid @ModelAttribute("paso1DTO") Paso1DTO dto,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            List<CuentaOrigen> cuentas = service.getAllCuentas();
            model.addAttribute("cuentas", cuentas);
            return "paso1";
        }
        // Guardamos en el objeto de sesión

        transferencia.setCuentaOrigenId(dto.getCuentaOrigenId());
        transferencia.setNombreBeneficiario(dto.getNombreBeneficiario());
        transferencia.setIbanDestino(dto.getIbanDestino());

        return "redirect:/transferencias/paso2";
    }

    // Paso 2: mostrar formulario de datos del participante
    @GetMapping("/paso2")
    public String mostrarPaso2(@ModelAttribute("transferencia") Transferencia transferencia, Model model) {
        CuentaOrigen cuenta = service.findCuentaById((int) transferencia.getCuentaOrigenId());
        model.addAttribute("cuenta", cuenta);

        // Rellenamos el DTO desde el objeto de sesión
        Paso2DTO dto = new Paso2DTO();
        dto.setCodigoConfirmacion(123456); // Ejemplo de código fijo
        model.addAttribute("paso2DTO", dto);

        return "paso2";
    }

    // Paso 2: procesar datos del participante y guardar inscripción
    @PostMapping("/paso2")
    public String confirmarTransferencia(
            @ModelAttribute("transferencia") Transferencia transferencia,
            @Valid @ModelAttribute("paso2DTO") Paso2DTO dto,
            BindingResult bindingResult,
            Model model) {

        CuentaOrigen cuenta = service.findCuentaById((int) transferencia.getCuentaOrigenId());

        if (bindingResult.hasErrors()) {
            model.addAttribute("cuenta", cuenta);
            return "paso2";
        }

        //Guardamos el objeto de la sesion
//        transferencia.set(dto.getNombreParticipante());
//        transferencia.setEmailParticipante(dto.getEmailParticipante());
        //transferencia.setPrecioTotal(service.calcularPrecioTotal(taller, transferencia.getNumeroAsistentes()));

//        service.guardarTransferencia(transferencia);

        return "redirect:/transferencias/registro";
    }

    // registro final: mostrar todos los datos de la transferencia
    @GetMapping("/registro")
    public String listado(@ModelAttribute("transferencia") Transferencia transferencia, Model model) {
        CuentaOrigen cuenta = service.findCuentaById((int) transferencia.getCuentaOrigenId());
        model.addAttribute("cuenta", cuenta);
        List<Transferencia> transferencias = service.listarTransferencias();
        model.addAttribute("transferencias", transferencias);
        return "registro";
    }
}

