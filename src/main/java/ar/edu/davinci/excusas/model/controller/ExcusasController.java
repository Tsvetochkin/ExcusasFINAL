package ar.edu.davinci.excusas.model.controller;

import ar.edu.davinci.excusas.model.dto.ExcusaRequestDTO;
import ar.edu.davinci.excusas.model.dto.ExcusaResponseDTO;
import ar.edu.davinci.excusas.model.service.ExcusaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
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

    // GET /excusas — all excuses, optional filters: ?motivo=TRIVIAL or ?encargado=Ana
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

    // POST /excusas — body: { "legajo": 501, "motivo": "TRIVIAL" }
    @PostMapping
    public ResponseEntity<?> registrar(@Valid @RequestBody ExcusaRequestDTO dto) {
        try {
            ExcusaResponseDTO resultado = service.registrar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
        } catch (IllegalArgumentException e) {
            // 400 if the motivo is unknown or the employee doesn't exist
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // GET /excusas/{legajo} — excuses for one employee
    @GetMapping("/{legajo}")
    public List<ExcusaResponseDTO> getPorLegajo(@PathVariable int legajo) {
        return service.buscarPorLegajo(legajo);
    }

    // GET /excusas/rechazadas — rejected excuses only
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
        // require the date param to prevent accidental wipes
        if (fechaLimite == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Debe proporcionar el parámetro fechaLimite para proteger la operación"));
        }
        long eliminadas = service.eliminarAnterioresA(fechaLimite);
        return ResponseEntity.ok(Map.of("eliminadas", eliminadas));
    }
}
