package org.iesvdm.banco_simulacion.controller;

import org.iesvdm.banco_simulacion.model.TransferenciaProgramada;
import org.iesvdm.banco_simulacion.repository.CuentaRepository;
import org.iesvdm.banco_simulacion.service.CuentaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@SessionAttributes("transferencia")
@RequestMapping("/transferencia/admin")
public class AdminController {

    private final CuentaService cuentaService;
    private final CuentaRepository cuentaRepository;


    public AdminController(CuentaService cuentaService, CuentaRepository cuentaRepository) {
        this.cuentaService = cuentaService;
        this.cuentaRepository = cuentaRepository;
    }

    @ModelAttribute("transferencia")
    public TransferenciaProgramada transferenciaProgramada(){
        return new TransferenciaProgramada();
    }

    @GetMapping("/index")
    public String AdminIndex (Model model, @ModelAttribute("transferencia") TransferenciaProgramada transferencia){
        List<TransferenciaProgramada> transferencias = cuentaService.listarTransferencias();
        model.addAttribute("transferencias", transferencias);
        return "index";
    }
    @GetMapping("/editar/{id}")
    public String editar (@PathVariable Long id, Model model) {
        TransferenciaProgramada transferencia = cuentaService.buscarTransferenciaId(id);
        model.addAttribute("transferencia", transferencia);
        return "editar";
    }

    @PostMapping("/editar")
    public String editarPost (@ModelAttribute("transferencia") TransferenciaProgramada transferencia) {
        cuentaService.editarTransferencia(transferencia);
        return "redirect:/transferencia/admin/index";
    }

    @GetMapping("/borrar/{id}")
    public String borrar (@PathVariable Long id, Model model){
        TransferenciaProgramada transferencia = cuentaService.buscarTransferenciaId(id);
        model.addAttribute("transferencia", transferencia);
        return "borrar";
    }

    @PostMapping("/borrar")
    public String borrarPost (@ModelAttribute("transferencia") TransferenciaProgramada transferencia){
        cuentaService.eliminarTransferencia(transferencia.getId());
        return "redirect:/transferencia/admin/index";
    }
}
