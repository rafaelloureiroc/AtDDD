package br.edu.infnet.attDDD.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class UsuarioTests {

    @Test
    public void deve_criar_um_usuario_corretamente() throws Exception {
        Usuario usuario = new Usuario();
        Cartao cartao = new Cartao();
        cartao.setAtivo(true);
        Plano plano = new Plano();

        usuario.criar("Xpto", "teste@teste.com", "123456", "39048007011", cartao, plano);

        Assertions.assertEquals("39048007011", usuario.getCpf());
        Assertions.assertEquals("Xpto", usuario.getNome());
        Assertions.assertEquals("teste@teste.com", usuario.getEmail());
        Assertions.assertEquals("MTIzNDU2", usuario.getSenha());
    }

    @Test
    public void nao_deve_criar_um_usuario_com_cpf_invalido() throws Exception {
        Usuario usuario = new Usuario();
        Cartao cartao = new Cartao(); // Crie um cartão válido para o teste
        Plano plano = new Plano(); // Crie um plano válido para o teste

        Assertions.assertThrows(Exception.class, () -> {
            usuario.criar("Xpto", "teste@teste.com", "123456", "12347896311", cartao, plano);
        });
    }
}

