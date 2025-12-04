package org.iesvdm.banco_simulacion.controller;

import org.iesvdm.banco_simulacion.model.Transferencia;
import org.iesvdm.banco_simulacion.repository.TransferenciaRepository;
import org.iesvdm.banco_simulacion.service.CuentaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/transferencias")
public class AdminController {

    private final TransferenciaRepository transferenciaRepository;
    private final CuentaService service;

    public AdminController(TransferenciaRepository transferenciaRepository, CuentaService service) {
        this.transferenciaRepository = transferenciaRepository;
        this.service = service;
    }

    @GetMapping("/index")
    public String index(Model model) {
        List<Transferencia> transferencias = service.listarTransferencias();
        model.addAttribute("transferencias", transferencias); // Debe ser "transferencias" y no "transferencia"
        return "index";
    }

    @GetMapping("/añadir")
    public String aniadirGet(Model model) {
        model.addAttribute("transferencia", new Transferencia());
        return "añadir";
    }

    @PostMapping("/añadir")
    public String aniadirPost(@ModelAttribute("transferencia") Transferencia transferencia) {
        transferenciaRepository.save(transferencia);
        return "redirect:/admin/transferencias/index";
    }

    @GetMapping("/editar/{id}")
    public String editarGet(@PathVariable int id, Model model) {
        Transferencia transferencia = service.findTransferenciaById(id);
        model.addAttribute("transferencia", transferencia);
        return "editar";
    }

    @PostMapping("/editar")
    public String editarPost(@ModelAttribute("transferencia") Transferencia transferencia) {
        service.editarTransferencia(transferencia.getId(), transferencia);
        return "redirect:/admin/transferencias/index";
    }

    @GetMapping("/borrar/{id}")
    public String borrarGet(@PathVariable int id, Model model) {
        Transferencia transferencia = service.findTransferenciaById(id);
        model.addAttribute("transferencia", transferencia);
        return "borrar";
    }

    @PostMapping("/borrar")
    public String borrarPost(@ModelAttribute("transferencia") Transferencia transferencia) {
        service.eliminarTransferencia((int) transferencia.getId());
        return "redirect:/admin/transferencias/index";
    }
}

