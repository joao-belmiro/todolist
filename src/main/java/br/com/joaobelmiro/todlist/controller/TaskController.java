package br.com.joaobelmiro.todlist.controller;

import java.util.UUID;

import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.joaobelmiro.todlist.model.TasksEntity;
import br.com.joaobelmiro.todlist.repository.TaskRepository;
import br.com.joaobelmiro.todlist.repository.UserRepository;

@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/save")
    public ResponseEntity create(@RequestBody TasksEntity task) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskRepository.save(task));
    }

    @PutMapping("/edit")
    public ResponseEntity edit(@RequestBody TasksEntity task) {
        var hastask = taskRepository.findById(task.getId());
        if (hastask != null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Não existe ou não foi possivel alterar o conteúdo");
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable String id) {
        var task = taskRepository.findById(UUID.fromString(id)).orElse(null);
        if (task != null) {
            return ResponseEntity.ok().body(task);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi possivel retornar o recurso solicitado");
    }

    @GetMapping("/all-for-user/{idUser}")
    public ResponseEntity findAllByUser(@PathVariable String idUser) {

        var user = userRepository.findById(UUID.fromString(idUser)).orElse(null);
        if (user != null) {
            List allTasks = taskRepository.findByUserEntity(user);
            return ResponseEntity.ok().body(allTasks);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("O usuário não existe");
    }

    @DeleteMapping("/delete-task/")
    public ResponseEntity deleteTask(@RequestParam(name = "idTaks") String idTask,
            @RequestParam(name = "idUser") String idUser) {

        var hasTask = taskRepository.findById(UUID.fromString(idTask));

        if (hasTask != null) {

            Boolean esqualsUser = hasTask.get().getUserEntity().getId().equals(UUID.fromString(idUser));

            if (esqualsUser) {

                taskRepository.deleteById(UUID.fromString(idTask));
                return ResponseEntity.status(HttpStatus.OK).body("Tarefa Excluída com sucesso");

            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não é possivel excluir essa tarefa");

        } else {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não existe tarefa Com esse código");

        }

    }

}
