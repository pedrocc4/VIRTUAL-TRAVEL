package com.bosonit.virtualtravel.autobus;

import com.bosonit.virtualtravel.autobus.service.IAutobusService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(classes =
        com.bosonit.virtualtravel.VirtualTravelApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AutobusServiceTest {
    @Autowired
    private IAutobusService service;

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void crearAutobusDiarioTest() {
        // Hay 3 autobuses por defecto
        Assertions.assertThat(service.getAutobuses()).hasSize(3);
        service.crearAutobusDiario();
        // Este metodo crea 4 diarios, por lo tanto 7
        Assertions.assertThat(service.getAutobuses()).hasSize(7);
    }

}
