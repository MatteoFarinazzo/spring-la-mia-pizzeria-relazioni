package org.learning.springpizzeria.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.net.URL;

@Entity
@Table(name = "pizzas")


public class Pizza {
    //attributi
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

private String nome;
private String descrizione;
private URL foto;
private BigDecimal prezzo;

// getter setter


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public URL getFoto() {
        return foto;
    }

    public void setFoto(URL foto) {
        this.foto = foto;
    }

    public BigDecimal getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(BigDecimal prezzo) {
        this.prezzo = prezzo;
    }
}
