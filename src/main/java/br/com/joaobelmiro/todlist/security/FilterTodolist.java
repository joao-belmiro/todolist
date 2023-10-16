package br.com.joaobelmiro.todlist.security;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.joaobelmiro.todlist.repository.UserRepository;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTodolist extends OncePerRequestFilter {

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        var path = request.getServletPath();

        if (path.contains("/task/")) {

            var authorization = request.getHeader("Authorization");

            var credentials = authorization.substring("Basic".length()).trim();

            byte[] decode = Base64.getDecoder().decode(credentials);

            String decodedCredentials = new String(decode, StandardCharsets.UTF_8);
            String[] usernameAndPAss = decodedCredentials.split(":");
            String login = usernameAndPAss[0];
            String password = usernameAndPAss[1];

            var hasUser = this.userRepository.findByUsername(login);

            if (hasUser == null) {
                response.sendError(401, "Usuário não autorizado");
            } else {
                var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), hasUser.getPassword());

                if (passwordVerify.verified) {
                    filterChain.doFilter(request, response);
                } else {
                    response.sendError(401);
                }
            }
        } else {
            filterChain.doFilter(request, response);
        }

    }

}
