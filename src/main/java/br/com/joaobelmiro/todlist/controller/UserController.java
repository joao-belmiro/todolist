package br.com.joaobelmiro.todlist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.joaobelmiro.todlist.model.UserEntity;
import br.com.joaobelmiro.todlist.repository.UserRepository;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody UserEntity user) {

        var hasUser = userRepository.findByUsername(user.getUsername());

        if (hasUser != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já cadastrado ");
        }

        String hashPassword = BCrypt.withDefaults()
                .hashToString(12, user.getPassword().toCharArray());

        user.setPassword(hashPassword);

        var userCreated = userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);

    }
}
