package ar.edu.davinci.excusas.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ExcusasControllerTest {

    @Autowired MockMvc mockMvc;

    @Test
    void postExcusas_bodyValido_devuelve201() throws Exception {
        // legajo 501 (Dima) is pre-seeded by CommandLineRunner
        mockMvc.perform(post("/excusas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"legajo\": 501, \"motivo\": \"TRIVIAL\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.motivo").value("TRIVIAL"));
    }

    @Test
    void postExcusas_motivoVacio_devuelve400() throws Exception {
        mockMvc.perform(post("/excusas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"legajo\": 501, \"motivo\": \"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errores.motivo").exists());
    }

    @Test
    void postExcusas_motivoInvalido_devuelve400() throws Exception {
        mockMvc.perform(post("/excusas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"legajo\": 501, \"motivo\": \"INVENTADO\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").exists());
    }

    @Test
    void getExcusas_sinFiltros_devuelve200() throws Exception {
        mockMvc.perform(get("/excusas"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void deleteEliminar_sinFechaLimite_devuelve400() throws Exception {
        mockMvc.perform(delete("/excusas/eliminar"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").exists());
    }

    @Test
    void deleteEliminar_conFechaLimite_devuelve200() throws Exception {
        mockMvc.perform(delete("/excusas/eliminar")
                        .param("fechaLimite", "2020-01-01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eliminadas").exists());
    }
}
