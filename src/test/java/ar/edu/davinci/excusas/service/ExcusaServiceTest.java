package ar.edu.davinci.excusas.service;

import ar.edu.davinci.excusas.model.domain.Empleado;
import ar.edu.davinci.excusas.model.domain.Excusa;
import ar.edu.davinci.excusas.model.domain.excusas.ExcusaTrivial;
import ar.edu.davinci.excusas.model.dto.ExcusaRequestDTO;
import ar.edu.davinci.excusas.model.dto.ExcusaResponseDTO;
import ar.edu.davinci.excusas.model.repository.EmpleadoRepository;
import ar.edu.davinci.excusas.model.repository.ExcusaRepository;
import ar.edu.davinci.excusas.model.repository.ProntuarioRepository;
import ar.edu.davinci.excusas.model.service.CadenaService;
import ar.edu.davinci.excusas.model.service.ExcusaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExcusaServiceTest {

    @Mock ExcusaRepository excusaRepository;
    @Mock EmpleadoRepository empleadoRepository;
    @Mock ProntuarioRepository prontuarioRepository;
    @Mock CadenaService cadenaService;

    @InjectMocks ExcusaService service;

    private Empleado empleado;

    @BeforeEach
    void setUp() {
        empleado = new Empleado("Juan", "juan@empresa.com", 501);
    }

    @Test
    void registrar_conMotivoValido_retornaDTO() {
        ExcusaRequestDTO dto = new ExcusaRequestDTO();
        dto.setLegajo(501);
        dto.setMotivo("TRIVIAL");

        when(empleadoRepository.findByNroLegajo(501)).thenReturn(Optional.of(empleado));
        when(excusaRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        ExcusaResponseDTO result = service.registrar(dto);

        assertThat(result.getMotivo()).isEqualTo("TRIVIAL");
        verify(cadenaService).evaluar(any(Excusa.class));
        verify(excusaRepository).save(any());
    }

    @Test
    void registrar_conMotivoInvalido_lanzaIllegalArgumentException() {
        ExcusaRequestDTO dto = new ExcusaRequestDTO();
        dto.setLegajo(501);
        dto.setMotivo("INVENTADO");

        assertThrows(IllegalArgumentException.class, () -> service.registrar(dto));
        verifyNoInteractions(empleadoRepository, cadenaService);
    }

    @Test
    void registrar_conEmpleadoInexistente_lanzaIllegalArgumentException() {
        ExcusaRequestDTO dto = new ExcusaRequestDTO();
        dto.setLegajo(999);
        dto.setMotivo("TRIVIAL");

        when(empleadoRepository.findByNroLegajo(999)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> service.registrar(dto));
        verifyNoInteractions(cadenaService);
    }

    @Test
    void listarRechazadas_retornaSoloExcusasNoAceptadas() {
        Excusa excusa = new ExcusaTrivial(empleado); // aceptada = false por defecto
        when(excusaRepository.findByAceptadaFalse()).thenReturn(List.of(excusa));

        List<ExcusaResponseDTO> result = service.listarRechazadas();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).isAceptada()).isFalse();
    }

    @Test
    void eliminarAnterioresA_retornaCantidadEliminada() {
        LocalDate fecha = LocalDate.of(2025, 1, 1);
        when(excusaRepository.deleteByFechaBefore(fecha)).thenReturn(3L);

        long eliminadas = service.eliminarAnterioresA(fecha);

        assertThat(eliminadas).isEqualTo(3);
    }
}
