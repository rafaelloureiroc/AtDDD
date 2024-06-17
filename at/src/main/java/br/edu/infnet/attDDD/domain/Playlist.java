package br.edu.infnet.attDDD.domain;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column
    private String nome;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Musica> musicas;
}
