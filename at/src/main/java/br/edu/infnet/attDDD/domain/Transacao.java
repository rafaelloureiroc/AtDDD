package br.edu.infnet.attDDD.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID codigoAutorizacao;

    @Column
    private double valor;

    @Column
    private LocalDateTime dtTransacao;

    @Column
    private String merchant;

    @Column
    private String descricao;

    @ManyToOne
    private Cartao cartao;
}
