package br.com.joaobelmiro.todlist.repository;

import java.util.UUID;

import org.hibernate.mapping.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.joaobelmiro.todlist.model.TasksEntity;
import br.com.joaobelmiro.todlist.model.UserEntity;

@Repository
public interface TaskRepository extends JpaRepository<TasksEntity, UUID> {

    List findByUserEntity(UserEntity userEntity);
}
