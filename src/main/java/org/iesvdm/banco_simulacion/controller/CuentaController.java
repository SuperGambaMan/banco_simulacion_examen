package org.iesvdm.banco_simulacion.controller;

import jakarta.validation.Valid;
import org.iesvdm.banco_simulacion.dto.Paso1DTO;
import org.iesvdm.banco_simulacion.dto.Paso2DTO;
import org.iesvdm.banco_simulacion.model.CuentaOrigen;
import org.iesvdm.banco_simulacion.model.TransferenciaProgramada;
import org.iesvdm.banco_simulacion.repository.CuentaRepository;
import org.iesvdm.banco_simulacion.service.CuentaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@SessionAttributes("transferencia")
@RequestMapping("/transferencia")
public class CuentaController {

    private final CuentaService cuentaService;
    private final CuentaRepository cuentaRepository;

    public CuentaController(CuentaService cuentaService, CuentaRepository cuentaRepository) {
        this.cuentaService = cuentaService;
        this.cuentaRepository = cuentaRepository;
    }

    @ModelAttribute("transferencia")
    public TransferenciaProgramada transferenciaProgramada(){
        return new TransferenciaProgramada();
    }

    @GetMapping("/paso1")
    public String paso1Get (Model model,@ModelAttribute("transferencia") TransferenciaProgramada transferenciaProgramada){

        List<CuentaOrigen> cuentas = cuentaService.listarCuentas();

        //Creamos un DTO para las validaciones
        Paso1DTO dto = new Paso1DTO();
        dto.setCuentaOrigenId(transferenciaProgramada.getCuentaOrigenId());
        dto.setNombreBeneficiario(transferenciaProgramada.getNombreBeneficiario());
        dto.setIbanDestino(transferenciaProgramada.getIbanDestino());
        dto.setImporte(transferenciaProgramada.getImporte());
        dto.setConcepto(transferenciaProgramada.getConcepto());
        dto.setFechaProgramada(transferenciaProgramada.getFechaProgramada());

        //Cuentas para listar las cuentas de la base de datos
        model.addAttribute("cuentas",cuentas);
        //Le pasamos el dto para la persistencia y validacion
        model.addAttribute("paso1DTO", dto);
        return "paso1";
    }

    @PostMapping("/paso1")
    public String paso1Post (Model model, @ModelAttribute("transferencia") TransferenciaProgramada transferenciaProgramada,
                             @Valid @ModelAttribute("paso1DTO") Paso1DTO paso1DTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            List<CuentaOrigen> cuentas = cuentaService.listarCuentas();
            //Estas "cuentas" es para seguir mantiendo la información del campo de cuentas de origen, tras un fallo de formulario
            model.addAttribute("cuentas", cuentas);
            return "paso1";
        }
        //introducimos los datos del formulario para mantenerlos en sesión
        transferenciaProgramada.setCuentaOrigenId(paso1DTO.getCuentaOrigenId());
        transferenciaProgramada.setNombreBeneficiario(paso1DTO.getNombreBeneficiario());
        transferenciaProgramada.setIbanDestino(paso1DTO.getIbanDestino());
        transferenciaProgramada.setImporte(paso1DTO.getImporte());
        transferenciaProgramada.setConcepto(paso1DTO.getConcepto());
        transferenciaProgramada.setFechaProgramada(paso1DTO.getFechaProgramada());
        transferenciaProgramada.setFechaCreacion(LocalDateTime.now());
        transferenciaProgramada.setEstado("PROGRAMADA");
        //NO insertar aquí - solo guardar en sesión para mostrar en paso2
        return "redirect:/transferencia/paso2";
    }

    @GetMapping("/paso2")
    public String paso2Get (Model model, @ModelAttribute("transferencia") TransferenciaProgramada transferenciaProgramada){

        CuentaOrigen cuentaOrigen = cuentaService.buscarCuentaId(transferenciaProgramada.getCuentaOrigenId());
        //Cuentas para coger la cuenta de la base de datos con un ID
        model.addAttribute("cuentaOrigen", cuentaOrigen);

        //Creamos un DTO para las validaciones
        Paso2DTO dto = new Paso2DTO();
        //Le pasamos el dto para la persistencia y validacion
        model.addAttribute("paso2DTO",dto);

        return "paso2";
    }

    @PostMapping("/paso2")
    public String paso2Post (Model model, @ModelAttribute("transferencia") TransferenciaProgramada transferenciaProgramada,
                             @Valid @ModelAttribute("paso2DTO") Paso2DTO paso2DTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            CuentaOrigen cuentaOrigen = cuentaService.buscarCuentaId(transferenciaProgramada.getCuentaOrigenId());
            //Cuentas para coger la cuenta de la base de datos con un ID y seguir manteniendola
            model.addAttribute("cuentaOrigen", cuentaOrigen);
            return "paso2";
        }
        //El código ya está en paso2DTO, no necesitamos setearlo de nuevo
        cuentaService.crearTransferencia(transferenciaProgramada);
        return "redirect:/transferencia/final";
    }

    @GetMapping("/final")
    public String finalGet ( Model model, @ModelAttribute("transferencia") TransferenciaProgramada transferenciaProgramada){

        CuentaOrigen cuentaOrigen = cuentaService.buscarCuentaId(transferenciaProgramada.getCuentaOrigenId());
        model.addAttribute("cuentaOrigen", cuentaOrigen);
        model.addAttribute("transferencia",transferenciaProgramada);

        return "final";
    }

    //Este PostMapping me sirve para limpiar la sesion, enlazandolo al boton de volver al paso 1 del html final
    @PostMapping("/final")
    public String volverPaso1(SessionStatus sessionStatus){
        //Limpiar la sesión para vaciar todos los datos
        sessionStatus.setComplete();
        //Redirigir al paso1 con to-do limpio
        return "redirect:/transferencia/paso1";
    }
}
