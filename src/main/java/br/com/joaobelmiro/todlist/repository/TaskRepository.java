package br.com.joaobelmiro.todlist.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.joaobelmiro.todlist.model.TasksEntity;

@Repository
public interface TaskRepository extends JpaRepository<TasksEntity, UUID> {

}
