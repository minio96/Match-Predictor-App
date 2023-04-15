package pl.sypek.predictor.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;
import pl.sypek.predictor.model.PlayerForm;
import pl.sypek.predictor.util.Role;

@Service
public class RegisterService {
    final PasswordEncoder passwordEncoder;
    final InMemoryUserDetailsManager inMemoryUserDetailsManager;

    private static final Logger logger = LoggerFactory.getLogger(RegisterService.class);

    public RegisterService(PasswordEncoder passwordEncoder, InMemoryUserDetailsManager inMemoryUserDetailsManager) {
        this.passwordEncoder = passwordEncoder;
        this.inMemoryUserDetailsManager = inMemoryUserDetailsManager;
    }

    public boolean registerPlayer(PlayerForm player) {
        logger.debug("Registering player: " + player.getName());
        UserDetails userDetails = User.withUsername(player.getName())
                .password(passwordEncoder.encode(player.getPassword()))
                .roles(Role.USER.toString())
                .build();
        inMemoryUserDetailsManager.createUser(userDetails);
        return inMemoryUserDetailsManager.userExists(player.getName());
    }

    public boolean unregisterPlayer(Long id) {
        //TODO
        return true;
    }
}
