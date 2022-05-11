package com.bosonit.virtualtravel.reserva;

import com.bosonit.virtualtravel.VirtualTravelApplication;
import com.bosonit.virtualtravel.reserva.domain.Reserva;
import com.bosonit.virtualtravel.reserva.infraestructure.controller.dto.input.ReservaInputDTO;
import com.bosonit.virtualtravel.reserva.infraestructure.controller.dto.output.ReservaOutputDTO;
import com.bosonit.virtualtravel.reserva.infraestructure.repository.IReservaRepositoryJPA;
import com.bosonit.virtualtravel.reserva.service.IReservaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;

import static com.bosonit.virtualtravel.utils.Constantes.BASE_URL;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = VirtualTravelApplication.class)
@DirtiesContext
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ReservaControllerTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IReservaService service;

    @Autowired
    private IReservaRepositoryJPA mockRepository;

    private static final String ID_RESERVA = "RSV00001";

    @BeforeAll
    public void init() {
        ReservaInputDTO reservaInputDTO = crearReserva();
        Reserva reserva = new Reserva();
        BeanUtils.copyProperties(reservaInputDTO, reserva);
        mockRepository.save(reserva);
    }


    ReservaInputDTO crearReserva() {
        return new ReservaInputDTO(
                "Barcelona",
                LocalDateTime.now().withHour(16),
                "Antonio",
                "Apellido",
                "antonio@gmail.com",
                "692967791");
    }

    @Test
    void getReservaTest() throws Exception {

        this.mockMvc.perform(get(BASE_URL + "reserva/" + ID_RESERVA))
                .andExpect(status().isOk())
                .andReturn();
        verify(service, times(1)).getReserva(ID_RESERVA);
    }

    @Test
    void addReservaTest() throws Exception {
        // Creamos Reserva
        ReservaInputDTO reservaInputDTO = crearReserva();

        // Convertimos a formato json e intentamos agregar
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule()); // para deserializar la fecha

        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(reservaInputDTO);

        MvcResult result = this.mockMvc.perform(post(BASE_URL + "reserva")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                        .header("authorize","vacio"))
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    void actReservaTest() throws Exception {
        // Creamos Reserva
        ReservaInputDTO reservaInputDTO = crearReserva();

        // Convertimos a formato json e intentamos agregar
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule()); // para deserializar la fecha

        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(reservaInputDTO);

        MvcResult result = this.mockMvc.perform(put(BASE_URL + "reserva/" + ID_RESERVA)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                        .header("authorize","vacio"))
                .andExpect(status().isOk()).andDo(print())
                .andReturn();
    }

    @Test
    void delReservaTest() throws Exception {
        ReservaOutputDTO reserva = service.addReserva(crearReserva());

        MvcResult result = this.mockMvc.perform(delete(BASE_URL + "reserva/" + ID_RESERVA)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void getReservasTest() throws Exception {
        MvcResult result = this.mockMvc.perform(get(BASE_URL + "reservas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void getReservasCiudadTest() throws Exception {
        MvcResult result = this.mockMvc.perform(get(BASE_URL + "reserva")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("ciudad", "Barcelona")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void reservasDisponiblesTest() throws Exception {
        MvcResult result = this.mockMvc.perform(get(BASE_URL + "disponible/Barcelona")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("fechaInferior", "2022-02-12")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }
}
