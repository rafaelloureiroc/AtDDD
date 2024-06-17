package br.edu.infnet.attDDD.domain;


import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
public class Assinatura {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column
    private boolean ativo;

    @Column
    private LocalDateTime dtAssinatura;

    @ManyToOne
    private Plano plano;
}
