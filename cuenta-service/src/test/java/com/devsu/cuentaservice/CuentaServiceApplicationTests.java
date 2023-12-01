package com.devsu.cuentaservice;

import com.devsu.cuentaservice.dto.request.CuentaRequest;
import com.devsu.cuentaservice.enums.TipoCuenta;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = CuentaServiceApplication.class)
@AutoConfigureMockMvc
class ClienteServiceApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCrearCuenta() throws Exception {

        Long clienteId = 1L;
        Long numeroCuenta = 4787582L;
        TipoCuenta tipoCuenta = TipoCuenta.A;
        BigDecimal saldoInicial = BigDecimal.valueOf(2000);
        
        CuentaRequest cuentaRequest = new CuentaRequest();
        cuentaRequest.setClienteId(clienteId);
        cuentaRequest.setNumeroCuenta(numeroCuenta);
        cuentaRequest.setTipoCuenta(tipoCuenta);
        cuentaRequest.setSaldoInicial(saldoInicial);

        ResultActions resultActionsPost = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/cuentas")
                .content(objectMapper.writeValueAsString(cuentaRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());//200

        String responseContent = resultActionsPost.andReturn().getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(responseContent);

        assertTrue(jsonNode.has("id"));
        Integer idCuenta = jsonNode.get("id").asInt();
        assertTrue(jsonNode.has("numeroCuenta"));
        assertTrue(jsonNode.has("tipoCuenta"));
        assertTrue(jsonNode.has("saldoInicial"));
        assertTrue(jsonNode.has("clienteId"));

        ResultActions resultActionsGet = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/cuentas/{id}", idCuenta)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        responseContent = resultActionsGet.andReturn().getResponse().getContentAsString();
        jsonNode = objectMapper.readTree(responseContent);

        assertEquals(jsonNode.get("id").asInt(), idCuenta);
        assertEquals(jsonNode.get("numeroCuenta").asLong(), numeroCuenta);
        assertEquals(jsonNode.get("tipoCuenta").asText(), tipoCuenta.name());
        assertEquals(jsonNode.get("saldoInicial").asDouble(), saldoInicial.doubleValue());
        assertEquals(jsonNode.get("clienteId").asInt(), clienteId);
    }
    
    @Test
    public void testEliminarCuenta() throws Exception {
        
        Integer idCuenta  = 1;
                
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/cuentas/{id}", idCuenta)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        
        ResultActions resultActionsGet = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/cuentas/{id}", idCuenta)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        String responseContent = resultActionsGet.andReturn().getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(responseContent);

        assertEquals(jsonNode.get("id").asInt(), idCuenta);
        assertEquals(jsonNode.get("estado").asBoolean(), false);
    }

}

