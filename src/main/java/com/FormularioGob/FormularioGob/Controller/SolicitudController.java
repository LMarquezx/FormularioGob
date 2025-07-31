package com.FormularioGob.FormularioGob.Controller;

import com.FormularioGob.FormularioGob.model.Solicitud;
import com.FormularioGob.FormularioGob.service.SolicitudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/solicitudes")
public class SolicitudController {
    
    @Autowired
    private SolicitudService solicitudService;
    
    @GetMapping("/consultar")
    public String mostrarFormularioConsulta() {
        return "consultar-solicitudes";
    }
    
    @GetMapping("/listar")
    public String listarTodasLasSolicitudes(Model model) {
        List<Solicitud> solicitudes = solicitudService.obtenerTodasLasSolicitudes();
        model.addAttribute("solicitudes", solicitudes);
        return "listar-solicitudes";
    }
    
    @GetMapping("/buscar")
    public String buscarSolicitudes(@RequestParam(required = false) String criterio,
                                  @RequestParam(required = false) String valor,
                                  Model model) {
        List<Solicitud> solicitudes = null;
        
        if (criterio != null && valor != null && !valor.trim().isEmpty()) {
            switch (criterio) {
                case "curp":
                    solicitudes = solicitudService.buscarPorCurp(valor);
                    break;
                case "nombre":
                    solicitudes = solicitudService.buscarPorNombre(valor);
                    break;
                case "plantel":
                    solicitudes = solicitudService.buscarPorPlantel(valor);
                    break;
                case "semestre":
                    solicitudes = solicitudService.buscarPorSemestre(valor);
                    break;
                case "area":
                    solicitudes = solicitudService.buscarPorAreaPropedeutica(valor);
                    break;
                default:
                    solicitudes = solicitudService.obtenerTodasLasSolicitudes();
                    break;
            }
        } else {
            solicitudes = solicitudService.obtenerTodasLasSolicitudes();
        }
        
        model.addAttribute("solicitudes", solicitudes);
        model.addAttribute("criterio", criterio);
        model.addAttribute("valor", valor);
        return "listar-solicitudes";
    }
    
    @GetMapping("/detalle/{id}")
    public String verDetalleSolicitud(@PathVariable String id, Model model) {
        Optional<Solicitud> solicitud = solicitudService.obtenerSolicitudPorId(id);
        
        if (solicitud.isPresent()) {
            model.addAttribute("solicitud", solicitud.get());
            return "detalle-solicitud";
        } else {
            model.addAttribute("error", "Solicitud no encontrada");
            return "error";
        }
    }
    
    @PostMapping("/eliminar/{id}")
    public String eliminarSolicitud(@PathVariable String id) {
        try {
            solicitudService.eliminarSolicitud(id);
            return "redirect:/solicitudes/listar?success=eliminada";
        } catch (Exception e) {
            return "redirect:/solicitudes/listar?error=no_eliminada";
        }
    }
    
    @GetMapping("/estadisticas")
    public String mostrarEstadisticas(Model model) {
        List<Solicitud> todasLasSolicitudes = solicitudService.obtenerTodasLasSolicitudes();
        
        // Estadísticas básicas
        model.addAttribute("totalSolicitudes", todasLasSolicitudes.size());
        
        // Contar por semestre
        long semestre1 = todasLasSolicitudes.stream().filter(s -> "1".equals(s.getSemestre())).count();
        long semestre2 = todasLasSolicitudes.stream().filter(s -> "2".equals(s.getSemestre())).count();
        long semestre3 = todasLasSolicitudes.stream().filter(s -> "3".equals(s.getSemestre())).count();
        long semestre4 = todasLasSolicitudes.stream().filter(s -> "4".equals(s.getSemestre())).count();
        long semestre5 = todasLasSolicitudes.stream().filter(s -> "5".equals(s.getSemestre())).count();
        long semestre6 = todasLasSolicitudes.stream().filter(s -> "6".equals(s.getSemestre())).count();
        
        model.addAttribute("semestre1", semestre1);
        model.addAttribute("semestre2", semestre2);
        model.addAttribute("semestre3", semestre3);
        model.addAttribute("semestre4", semestre4);
        model.addAttribute("semestre5", semestre5);
        model.addAttribute("semestre6", semestre6);
        
        // Contar por área propedéutica
        long areaQB = todasLasSolicitudes.stream().filter(s -> "QB".equals(s.getAreaPropedeutica())).count();
        long areaEA = todasLasSolicitudes.stream().filter(s -> "EA".equals(s.getAreaPropedeutica())).count();
        long areaHC = todasLasSolicitudes.stream().filter(s -> "HC".equals(s.getAreaPropedeutica())).count();
        long areaFM = todasLasSolicitudes.stream().filter(s -> "FM".equals(s.getAreaPropedeutica())).count();
        
        model.addAttribute("areaQB", areaQB);
        model.addAttribute("areaEA", areaEA);
        model.addAttribute("areaHC", areaHC);
        model.addAttribute("areaFM", areaFM);
        
        // Contar por género
        long generoMasc = todasLasSolicitudes.stream().filter(s -> "MASC".equals(s.getGenero())).count();
        long generoFem = todasLasSolicitudes.stream().filter(s -> "FEM".equals(s.getGenero())).count();
        
        model.addAttribute("generoMasc", generoMasc);
        model.addAttribute("generoFem", generoFem);
        
        return "estadisticas-solicitudes";
    }
    
    @GetMapping("/buscar-por-fecha")
    public String buscarPorRangoFechas(@RequestParam(required = false) String fechaInicio,
                                     @RequestParam(required = false) String fechaFin,
                                     Model model) {
        List<Solicitud> solicitudes = null;
        
        if (fechaInicio != null && fechaFin != null && !fechaInicio.isEmpty() && !fechaFin.isEmpty()) {
            try {
                LocalDateTime inicio = LocalDateTime.parse(fechaInicio + "T00:00:00");
                LocalDateTime fin = LocalDateTime.parse(fechaFin + "T23:59:59");
                solicitudes = solicitudService.buscarPorRangoFechas(inicio, fin);
            } catch (Exception e) {
                model.addAttribute("error", "Formato de fecha inválido");
                solicitudes = solicitudService.obtenerTodasLasSolicitudes();
            }
        } else {
            solicitudes = solicitudService.obtenerTodasLasSolicitudes();
        }
        
        model.addAttribute("solicitudes", solicitudes);
        model.addAttribute("fechaInicio", fechaInicio);
        model.addAttribute("fechaFin", fechaFin);
        return "listar-solicitudes";
    }
}
