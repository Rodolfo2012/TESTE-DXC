package com.example.TesteDXC.repository;

import com.example.TesteDXC.model.Cliente;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class ClienteRepositoryTest {

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    public void deveSalvarEEncontrarPorId() {
        Cliente cliente = new Cliente(null, "Maria", "maria@email.com", "987.654.321-00");

        Cliente savedCliente = clienteRepository.save(cliente);
        Optional<Cliente> foundCliente = clienteRepository.findById(savedCliente.getId());

        assertTrue(foundCliente.isPresent());
        assertEquals("Maria", foundCliente.get().getNome());
        assertEquals("maria@email.com", foundCliente.get().getEmail());
    }
}
