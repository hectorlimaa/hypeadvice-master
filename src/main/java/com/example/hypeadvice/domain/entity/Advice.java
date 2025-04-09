package com.example.hypeadvice.domain.entity;

import com.google.gson.annotations.Expose;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@javax.persistence.Entity
@Table(name = "advice")
public class Advice extends Entity {
    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    @Column(name = "NOME", length = 100)
    private String nome;

    @NotBlank(message = "A descrição é obrigatória")
    @Column(name = "DESCRICAO", columnDefinition = "TEXT", length = 1000, nullable = false)
    private String descricao;

    @NotNull(message = "O tipo de conselho é obrigatório")
    @Enumerated(EnumType.STRING)
    private TipoConselho tipo;

    public Advice(String adviceStr) {
        this.descricao = adviceStr;
    }

    public Advice() {

    }

    @Override
    public Long getId() {
        return this.id;
    }
}
