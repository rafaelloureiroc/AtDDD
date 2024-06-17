package br.edu.infnet.attDDD.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.*;

@Data
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private String nome;

    @Column
    private String senha;

    @Column
    private String cpf;

    @Column
    private String email;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_cartao", referencedColumnName = "id")
    private List<Cartao> cartoes = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_cartao", referencedColumnName = "id")
    private List<Playlist> playlists = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_assinatura", referencedColumnName = "id")
    private List<Assinatura> assinaturas = new ArrayList<>();

    private static final String DEFAULT_PLAYLIST_NAME = "Musicas Favoritas";

    public void criar(String nome, String email, String senha, String cpf, Cartao cartao, Plano plano) throws Exception {
        if (isValidoCpf(cpf) == false) {
            throw new Exception("CPF está invalido");
        }

        this.setCpf(cpf);
        this.setNome(nome);
        this.setEmail(email);
        this.setSenha(this.encodeSenha(senha));

        //Transacionar no cartao
        cartao.criarTransacao(plano.getNome(), plano.getValor(), plano.getDescricao());

        //Adicionar o cartao na lista do usuário
        this.cartoes.add(cartao);

        //Criando um assinatura
        Assinatura assinatura = new Assinatura();
        assinatura.setPlano(plano);
        assinatura.setDtAssinatura(LocalDateTime.now());
        assinatura.setAtivo(true);
        this.assinaturas.add(assinatura);

        //Criar uma playlist default
        Playlist playlist = new Playlist();
        playlist.setNome(DEFAULT_PLAYLIST_NAME);
        this.playlists.add(playlist);
    }

    public void favoritarMusica(UUID idPlaylist, Musica musica) {
        for (Playlist playlist: this.playlists) {
            if (playlist.getId().equals(idPlaylist)) {
                playlist.getMusicas().add(musica);
            }
        }
    }
    private String encodeSenha(String senha) {
        return Base64.getEncoder().encodeToString(senha.getBytes());
    }

    private boolean isValidoCpf(String CPF) {
        if (CPF.equals("00000000000") ||
                CPF.equals("11111111111") ||
                CPF.equals("22222222222") || CPF.equals("33333333333") ||
                CPF.equals("44444444444") || CPF.equals("55555555555") ||
                CPF.equals("66666666666") || CPF.equals("77777777777") ||
                CPF.equals("88888888888") || CPF.equals("99999999999") ||
                (CPF.length() != 11))
            return(false);

        char dig10, dig11;
        int sm, i, r, num, peso;

        // "try" - protege o codigo para eventuais erros de conversao de tipo (int)
        try {
            // Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 10;
            for (i=0; i<9; i++) {
                // converte o i-esimo caractere do CPF em um numero:
                // por exemplo, transforma o caractere "0" no inteiro 0
                // (48 eh a posicao de "0" na tabela ASCII)
                num = (int)(CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else dig10 = (char)(r + 48); // converte no respectivo caractere numerico

            // Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 11;
            for(i=0; i<10; i++) {
                num = (int)(CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig11 = '0';
            else dig11 = (char)(r + 48);

            // Verifica se os digitos calculados conferem com os digitos informados.
            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
                return(true);
            return(false);
        } catch (InputMismatchException erro) {
            return(false);
        }
    }
}
