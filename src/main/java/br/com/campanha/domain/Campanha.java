package br.com.campanha.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Entidade representa os dados da base de Campanhas.
 * @author : Claudio Nazareth  chtnazareth@gmail.com
 */

@Document
public class Campanha {

    @Id
    private String id;

    @Indexed
    @Size(min=5, max=100, message="Nome tem capacidade de 5 a 100 caracteres.")
    @NotNull(message="Nome da campanha é obrigatório!")
    @Field(value = "nome")
    private String nome;

    @NotNull(message="A identifição do time é obrigatório!")
    @Field(value = "timeCoracao")
    private String timeCoracaoId;

    @Indexed
    @NotNull(message="O inicio da vigencia é obrigatório!")
    @Field(value = "inicioVigencia")
    private LocalDate inicioVigencia;

    @Indexed
    @NotNull(message="O fim vigencia é obrigatório!")
    @Field(value = "fimVigencia")
    private LocalDate fimVigencia;

    public Campanha() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTimeCoracaoId() {
        return timeCoracaoId;
    }

    public void setTimeCoracaoId(String timeCoracaoId) {
        this.timeCoracaoId = timeCoracaoId;
    }

    public LocalDate getInicioVigencia() {
        return inicioVigencia;
    }

    public void setInicioVigencia(LocalDate inicioVigencia) {
        this.inicioVigencia = inicioVigencia;
    }

    public LocalDate getFimVigencia() {
        return fimVigencia;
    }

    public void setFimVigencia(LocalDate fimVigencia) {
        this.fimVigencia = fimVigencia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Campanha campanha = (Campanha) o;
        return Objects.equals(getNome(), campanha.getNome()) &&
                Objects.equals(getTimeCoracaoId(), campanha.getTimeCoracaoId()) &&
                Objects.equals(getInicioVigencia(), campanha.getInicioVigencia()) &&
                Objects.equals(getFimVigencia(), campanha.getFimVigencia());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNome(), getTimeCoracaoId(), getInicioVigencia(), getFimVigencia());
    }

    @Override
    public String toString() {
        return com.google.common.base.MoreObjects.toStringHelper(this)
                .add("nome", nome)
                .add("timeCoracaoId", timeCoracaoId)
                .add("inicioVigencia", inicioVigencia)
                .add("fimVigencia", fimVigencia)
                .toString();
    }
}
