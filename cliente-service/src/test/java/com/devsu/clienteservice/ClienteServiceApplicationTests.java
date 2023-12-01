package com.devsu.clienteservice;

import com.devsu.clienteservice.controller.ClienteController;
import com.devsu.clienteservice.entity.Cliente;
import com.devsu.clienteservice.enums.Genero;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.Charset;
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

@SpringBootTest(classes = ClienteServiceApplication.class)
@AutoConfigureMockMvc
class ClienteServiceApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCrearCliente() throws Exception {

        String nombre = "Jose Lema";
        String genero = Genero.M.name();
        int edad = 28;
        String identificacion = "12345";
        String direccion = "Otavalo sn y principal";
        String telefono = "098254785";
//        String clienteId = "jose123";
        String contraseña = "1234";

        Cliente clienteRequest = new Cliente();
        clienteRequest.setNombre(nombre);
        clienteRequest.setGenero(genero);
        clienteRequest.setEdad(edad);
        clienteRequest.setIdentificacion(identificacion);
        clienteRequest.setDireccion(direccion);
        clienteRequest.setTelefono(telefono);
//        clienteRequest.setClienteId(clienteId);
        clienteRequest.setContraseña(contraseña);

        ResultActions resultActionsPost = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/clientes")
                .content(objectMapper.writeValueAsString(clienteRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());//200

        String responseContent = resultActionsPost.andReturn().getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(responseContent);

        assertTrue(jsonNode.has("id"));
        Integer idCliente = jsonNode.get("id").asInt();
        assertTrue(jsonNode.has("nombre"));
        assertTrue(jsonNode.has("genero"));
        assertTrue(jsonNode.has("edad"));
        assertTrue(jsonNode.has("identificacion"));
        assertTrue(jsonNode.has("direccion"));
        assertTrue(jsonNode.has("telefono"));

        ResultActions resultActionsGet = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/clientes/{id}", idCliente)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        responseContent = resultActionsGet.andReturn().getResponse().getContentAsString();
        jsonNode = objectMapper.readTree(responseContent);

        assertEquals(jsonNode.get("id").asInt(), idCliente);
        assertEquals(jsonNode.get("nombre").asText(), nombre);
        assertEquals(jsonNode.get("genero").asText(), genero);
        assertEquals(jsonNode.get("edad").asInt(), edad);
        assertEquals(jsonNode.get("identificacion").asText(), identificacion);
        assertEquals(jsonNode.get("direccion").asText(), direccion);
        assertEquals(jsonNode.get("telefono").asText(), telefono);
        assertEquals(jsonNode.get(new String(
                "contraseña".getBytes(Charset.forName("UTF-8")),
                "ISO-8859-1"
        )).asText(), contraseña);
        assertEquals(jsonNode.get("estado").asBoolean(), true);
    }
    
    @Test
    public void testEliminarCliente() throws Exception {
        
        Integer idCliente  = 1;
                
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/clientes/{id}", idCliente)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        
        ResultActions resultActionsGet = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/clientes/{id}", idCliente)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        String responseContent = resultActionsGet.andReturn().getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(responseContent);

        assertEquals(jsonNode.get("id").asInt(), idCliente);
        assertEquals(jsonNode.get("estado").asBoolean(), false);
    }

}
