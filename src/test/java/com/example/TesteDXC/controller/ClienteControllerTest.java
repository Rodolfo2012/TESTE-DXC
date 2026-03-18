package com.example.TesteDXC.controller;

import com.example.TesteDXC.model.Cliente;
import com.example.TesteDXC.repository.ClienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        clienteRepository.deleteAll();
    }

    @Test
    public void testCriarCliente() throws Exception {
        Cliente cliente = new Cliente(null, "João Silva", "joao.silva@email.com", "123.456.789-00");

        mockMvc.perform(post("/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cliente)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("João Silva"))
                .andExpect(jsonPath("$.email").value("joao.silva@email.com"));
    }

    @Test
    public void testListarClientes() throws Exception {
        Cliente cliente1 = new Cliente(null, "João Silva", "joao.silva@email.com", "123.456.789-00");
        Cliente cliente2 = new Cliente(null, "Maria Santos", "maria.santos@email.com", "987.654.321-99");
        clienteRepository.save(cliente1);
        clienteRepository.save(cliente2);

        mockMvc.perform(get("/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nome").value("João Silva"))
                .andExpect(jsonPath("$[1].nome").value("Maria Santos"));
    }

    @Test
    public void testBuscarClientePorId() throws Exception {
        Cliente cliente = new Cliente(null, "João Silva", "joao.silva@email.com", "123.456.789-00");
        Cliente salvo = clienteRepository.save(cliente);

        mockMvc.perform(get("/clientes/" + salvo.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(salvo.getId()))
                .andExpect(jsonPath("$.nome").value("João Silva"));
    }

    @Test
    public void testAlterarCliente() throws Exception {
        Cliente cliente = new Cliente(null, "João Silva", "joao.silva@email.com", "123.456.789-00");
        Cliente salvo = clienteRepository.save(cliente);

        Cliente clienteAtualizado = new Cliente(salvo.getId(), "João da Silva", "joao.silva.novo@email.com", "123.456.789-00");

        mockMvc.perform(put("/clientes/" + salvo.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clienteAtualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("João da Silva"))
                .andExpect(jsonPath("$.email").value("joao.silva.novo@email.com"));
    }

    @Test
    public void testExcluirCliente() throws Exception {
        Cliente cliente = new Cliente(null, "João Silva", "joao.silva@email.com", "123.456.789-00");
        Cliente salvo = clienteRepository.save(cliente);

        mockMvc.perform(delete("/clientes/" + salvo.getId()))
                .andExpect(status().isNoContent());
    }
}
