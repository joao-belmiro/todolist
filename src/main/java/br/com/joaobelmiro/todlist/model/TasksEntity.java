package br.com.joaobelmiro.todlist.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "TB_TASKS")
public class TasksEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "ID_TASK")
    private UUID id;

    @Column(name = "DESCRICAO")
    private String descrição;

    @Column(name = "TITULO")
    private String titulo;

    @CreationTimestamp
    @Column(name = "INI_AT")
    private LocalDateTime ini_at;

    @CreationTimestamp
    @Column(name = "FIM_AT")
    private LocalDateTime fim_at;

    @CreationTimestamp
    @Column(name = "created")
    private LocalDateTime created_at;

    @Column(name = "PRIORIDADE")
    private Boolean prioridade;

    @ManyToOne
    @JoinColumn(name = "ID_USER")
    private UserEntity userEntity;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDescrição() {
        return descrição;
    }

    public void setDescrição(String descrição) {
        this.descrição = descrição;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public LocalDateTime getIni_at() {
        return ini_at;
    }

    public void setIni_at(LocalDateTime ini_at) {
        this.ini_at = ini_at;
    }

    public LocalDateTime getFim_at() {
        return fim_at;
    }

    public void setFim_at(LocalDateTime fim_at) {
        this.fim_at = fim_at;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public Boolean getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Boolean prioridade) {
        this.prioridade = prioridade;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    @PrePersist
    private void created() {
        setCreated_at(LocalDateTime.now());
    }
}
