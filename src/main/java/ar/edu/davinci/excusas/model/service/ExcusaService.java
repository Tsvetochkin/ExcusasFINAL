package ar.edu.davinci.excusas.model.service;

import ar.edu.davinci.excusas.model.domain.*;
import ar.edu.davinci.excusas.model.domain.excusas.*;
import ar.edu.davinci.excusas.model.dto.ExcusaRequestDTO;
import ar.edu.davinci.excusas.model.dto.ExcusaResponseDTO;
import ar.edu.davinci.excusas.model.repository.EmpleadoRepository;
import ar.edu.davinci.excusas.model.repository.ExcusaRepository;
import ar.edu.davinci.excusas.model.repository.ProntuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExcusaService {

    private final ExcusaRepository excusaRepository;
    private final EmpleadoRepository empleadoRepository;
    private final ProntuarioRepository prontuarioRepository;
    private final CadenaService cadenaService;

    @Autowired
    public ExcusaService(ExcusaRepository excusaRepository,
                         EmpleadoRepository empleadoRepository,
                         ProntuarioRepository prontuarioRepository,
                         CadenaService cadenaService) {
        this.excusaRepository = excusaRepository;
        this.empleadoRepository = empleadoRepository;
        this.prontuarioRepository = prontuarioRepository;
        this.cadenaService = cadenaService;
    }

    // POST /excusas — registra y evalúa una excusa
    public ExcusaResponseDTO registrar(ExcusaRequestDTO dto) {
        // 1. Convertir motivo de String a enum (si es inválido, lanza excepción)
        TipoExcusa tipo;
        try {
            tipo = TipoExcusa.valueOf(dto.getMotivo().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Motivo inválido: " + dto.getMotivo());
        }

        // 2. Buscar el empleado por legajo
        Empleado empleado = empleadoRepository.findByNroLegajo(dto.getLegajo())
                .orElseThrow(() -> new IllegalArgumentException("No existe empleado con legajo: " + dto.getLegajo()));

        // 3. Crear la excusa del tipo correcto (polimorfismo)
        Excusa excusa = crearExcusa(tipo, empleado);

        // 4. Evaluar con la cadena de encargados
        cadenaService.evaluar(excusa);

        // 5. Guardar en base de datos
        excusaRepository.save(excusa);

        // 6. Crear prontuario SOLO si fue procesada por el CEO (excusa inverosímil)
        // El CEO es el único que genera prontuarios según el diseño del sistema
        if (excusa.isAceptada() && tipo == TipoExcusa.INVEROSIMIL) {
            prontuarioRepository.save(new Prontuario(empleado, excusa));
        }

        return toDTO(excusa);
    }

    // Guardar directamente (usado en startup)
    public Excusa guardar(Excusa excusa) {
        return excusaRepository.save(excusa);
    }

    // GET /excusas — con filtros opcionales por motivo y encargado
    public List<ExcusaResponseDTO> listarTodas(String motivo, String encargado) {
        List<Excusa> resultado;

        if (motivo != null && !motivo.isBlank()) {
            TipoExcusa tipo;
            try {
                tipo = TipoExcusa.valueOf(motivo.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Motivo inválido: " + motivo);
            }
            resultado = excusaRepository.findByMotivo(tipo);
        } else if (encargado != null && !encargado.isBlank()) {
            resultado = excusaRepository.findByAceptadaPor(encargado);
        } else {
            resultado = excusaRepository.findAll();
        }

        return resultado.stream().map(this::toDTO).collect(Collectors.toList());
    }

    // GET /excusas/{legajo}
    public List<ExcusaResponseDTO> buscarPorLegajo(int legajo) {
        return excusaRepository.findByEmpleadoNroLegajo(legajo)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    // GET /excusas/rechazadas
    public List<ExcusaResponseDTO> listarRechazadas() {
        return excusaRepository.findByAceptadaFalse()
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    // GET /excusas/busqueda?legajo=...&fechaDesde=...&fechaHasta=...
    public List<ExcusaResponseDTO> buscarPorLegajoYFecha(int legajo, LocalDate desde, LocalDate hasta) {
        return excusaRepository.findByEmpleadoNroLegajoAndFechaBetween(legajo, desde, hasta)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    // DELETE /excusas/eliminar?fechaLimite=...
    @Transactional
    public long eliminarAnterioresA(LocalDate fechaLimite) {
        return excusaRepository.deleteByFechaBefore(fechaLimite);
    }

    // Convierte TipoExcusa → instancia concreta (patrón Factory Method)
    private Excusa crearExcusa(TipoExcusa tipo, Empleado empleado) {
        return switch (tipo) {
            case TRIVIAL    -> new ExcusaTrivial(empleado);
            case FAMILIAR   -> new ExcusaFamiliar(empleado);
            case LUZ        -> new ExcusaLuz(empleado);
            case COMPLEJA   -> new ExcusaCompleja(empleado);
            case INVEROSIMIL -> new ExcusaInverosimil(empleado);
        };
    }

    private ExcusaResponseDTO toDTO(Excusa e) {
        return new ExcusaResponseDTO(
                e.getId(),
                e.getEmpleado().getNroLegajo(),
                e.getEmpleado().getNombre(),
                e.getMotivo() != null ? e.getMotivo().name() : "DESCONOCIDO",
                e.isAceptada(),
                e.getFecha(),
                e.getAceptadaPor()
        );
    }
}
