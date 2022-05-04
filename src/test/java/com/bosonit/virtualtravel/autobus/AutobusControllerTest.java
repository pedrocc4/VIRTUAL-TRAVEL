package com.bosonit.virtualtravel.autobus;

import com.bosonit.virtualtravel.VirtualTravelApplication;
import com.bosonit.virtualtravel.autobus.infraestructure.controller.dto.input.AutobusInputDTO;
import com.bosonit.virtualtravel.autobus.infraestructure.repository.IAutobusRepositoryJPA;
import com.bosonit.virtualtravel.autobus.service.IAutobusService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;

import static com.bosonit.virtualtravel.utils.Constantes.BASE_URL;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = VirtualTravelApplication.class)
@DirtiesContext
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AutobusControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IAutobusService service;

    @Autowired
    private IAutobusRepositoryJPA mockRepository;

    AutobusInputDTO crearAutobus() {
        return new AutobusInputDTO(
                "40",
                "Madrid",
                16.0f,
                LocalDate.now().plusMonths(1)
        );
    }

    @Test
    void getAutobus() throws Exception {
        this.mockMvc.perform(get(BASE_URL + "autobus/BUS1"))
                .andExpect(status().isOk())
                .andReturn();
        verify(service, times(1)).getAutobus("BUS1");
    }

    @Test
    void addAutobus() throws Exception {
        // Creamos autobus
        AutobusInputDTO autobusInputDTO = crearAutobus();

        // Convertimos a formato json e intentamos agregar
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule()); // para deserializar la fecha

        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(autobusInputDTO);

        MvcResult result = this.mockMvc.perform(post(BASE_URL + "autobus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    void actAutobus() throws Exception {
        // Creamos Autobus
        AutobusInputDTO autobusInputDTO = crearAutobus();

        // Convertimos a formato json e intentamos agregar
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule()); // para deserializar la fecha

        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(autobusInputDTO);

        MvcResult result = this.mockMvc.perform(put(BASE_URL +"autobus/BUS1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void delAutobus() throws Exception {
        MvcResult result = this.mockMvc.perform(delete(BASE_URL + "autobus/BUS1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void getAutobuses() throws Exception {
        MvcResult result = this.mockMvc.perform(get(BASE_URL + "autobuses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }
}
