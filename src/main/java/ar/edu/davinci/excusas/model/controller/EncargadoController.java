package ar.edu.davinci.excusas.model.controller;

import ar.edu.davinci.excusas.model.domain.TipoExcusa;
import ar.edu.davinci.excusas.model.dto.EncargadoRequestDTO;
import ar.edu.davinci.excusas.model.dto.ModoRequestDTO;
import ar.edu.davinci.excusas.model.service.CadenaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/encargados")
public class EncargadoController {

    private final CadenaService cadenaService;

    @Autowired
    public EncargadoController(CadenaService cadenaService) {
        this.cadenaService = cadenaService;
    }

    // GET /encargados — current chain config
    @GetMapping
    public List<CadenaService.EncargadoInfoDTO> getConfig() {
        return cadenaService.getInfo();
    }

    // POST /encargados — body: { "nombre": "Carlos", "email": "carlos@empresa.com", "nroLegajo": 200, "motivos": ["TRIVIAL"] }
    @PostMapping
    public ResponseEntity<?> agregar(@RequestBody EncargadoRequestDTO dto) {
        try {
            List<TipoExcusa> tipos = dto.getMotivos().stream()
                    .map(m -> TipoExcusa.valueOf(m.toUpperCase()))
                    .collect(Collectors.toList());
            cadenaService.agregarEncargado(dto.getNombre(), dto.getEmail(), dto.getNroLegajo(), tipos);
            return ResponseEntity.ok(Map.of("mensaje", "Encargado agregado correctamente"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Motivo inválido: " + e.getMessage()));
        }
    }

    // PUT /encargados/modo — body: { "nroLegajo": 101, "modo": "VAGO" }
    @PutMapping("/modo")
    public ResponseEntity<?> cambiarModo(@RequestBody ModoRequestDTO dto) {
        boolean ok = cadenaService.cambiarModo(dto.getNroLegajo(), dto.getModo());
        if (ok) {
            return ResponseEntity.ok(Map.of("mensaje", "Modo actualizado a " + dto.getModo()));
        }
        return ResponseEntity.badRequest().body(Map.of("error", "Encargado no encontrado o modo inválido"));
    }
}
