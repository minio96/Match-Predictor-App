package pl.sypek.predictor.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import pl.sypek.predictor.util.Role;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(HttpMethod.DELETE, "/register/").hasAnyRole(Role.ADMIN.toString())
                        .requestMatchers(HttpMethod.DELETE, "/player/**").hasAnyRole(Role.ADMIN.toString())
                        .requestMatchers("/player/**").authenticated()
                        .requestMatchers("/prediction").authenticated()
                        .requestMatchers("/match/**").authenticated()
                        .requestMatchers("/ranking/update").hasRole(Role.ADMIN.toString())
                        .requestMatchers("/ranking/**").authenticated()
                        .requestMatchers("/register").permitAll()
                        .requestMatchers("/").permitAll()
                        .anyRequest().permitAll()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                        .defaultSuccessUrl("/success")
                )
                .logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/"))
                .csrf().disable(); //for Postman’s sake

        return http.build();
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource driver = new DriverManagerDataSource();
        driver.setDriverClassName("org.postgresql.Driver");
        driver.setUrl("jdbc:postgresql://db:5432/db_predictor");
        driver.setUsername("postgres");
        driver.setPassword("password");
        return driver;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        InMemoryUserDetailsManager detailsManager = new InMemoryUserDetailsManager();
        detailsManager.createUser(User.withUsername("admin")
                .password(passwordEncoder().encode("pass"))
                .roles(Role.ADMIN.toString())
                .build());
        return detailsManager;
    }
}
