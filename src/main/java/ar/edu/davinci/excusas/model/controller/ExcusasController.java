package ar.edu.davinci.excusas.model.controller;

import ar.edu.davinci.excusas.model.dto.ExcusaRequestDTO;
import ar.edu.davinci.excusas.model.dto.ExcusaResponseDTO;
import ar.edu.davinci.excusas.model.service.ExcusaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/excusas")
public class ExcusasController {

    private final ExcusaService service;

    @Autowired
    public ExcusasController(ExcusaService service) {
        this.service = service;
    }

    // GET /excusas → todas las excusas, con filtros opcionales
    // Ejemplos: /excusas?motivo=TRIVIAL  o  /excusas?encargado=Ana
    @GetMapping
    public ResponseEntity<?> getTodas(
            @RequestParam(required = false) String motivo,
            @RequestParam(required = false) String encargado) {
        try {
            return ResponseEntity.ok(service.listarTodas(motivo, encargado));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // POST /excusas → registrar nueva excusa
    // Body: { "legajo": 501, "motivo": "TRIVIAL" }
    @PostMapping
    public ResponseEntity<?> registrar(@RequestBody ExcusaRequestDTO dto) {
        try {
            ExcusaResponseDTO resultado = service.registrar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
        } catch (IllegalArgumentException e) {
            // 400 Bad Request si el motivo no existe o el empleado no existe
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // GET /excusas/{legajo} → excusas de un empleado
    @GetMapping("/{legajo}")
    public List<ExcusaResponseDTO> getPorLegajo(@PathVariable int legajo) {
        return service.buscarPorLegajo(legajo);
    }

    // GET /excusas/rechazadas → excusas no aceptadas
    @GetMapping("/rechazadas")
    public List<ExcusaResponseDTO> getRechazadas() {
        return service.listarRechazadas();
    }

    // GET /excusas/busqueda?legajo=501&fechaDesde=2025-01-01&fechaHasta=2025-12-31
    @GetMapping("/busqueda")
    public List<ExcusaResponseDTO> busqueda(
            @RequestParam int legajo,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDesde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaHasta) {
        return service.buscarPorLegajoYFecha(legajo, fechaDesde, fechaHasta);
    }

    // DELETE /excusas/eliminar?fechaLimite=2024-12-31
    @DeleteMapping("/eliminar")
    public ResponseEntity<?> eliminar(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaLimite) {
        // Validación: si no se pasa fechaLimite, rechazar con error claro
        if (fechaLimite == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Debe proporcionar el parámetro fechaLimite para proteger la operación"));
        }
        long eliminadas = service.eliminarAnterioresA(fechaLimite);
        return ResponseEntity.ok(Map.of("eliminadas", eliminadas));
    }
}
