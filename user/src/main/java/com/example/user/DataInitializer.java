package com.example.user;

import com.example.user.entity.User;
import com.example.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class DataInitializer {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @EventListener(value= ApplicationReadyEvent.class)
    public void init(){
        log.info("start data initialization....");
        var initUsers= this.userRepository.deleteAll()
                .thenMany(
                        Flux.just("user","admin")
                                .flatMap(username->{
                                    List<String> roles="user".equals(username) ?
                                            Arrays.asList("ROLE_USER"): Arrays.asList("ROLE_USER","ROLE_ADMIN");
                                    User user = User.builder()
                                            .roles(roles)
                                            .username(username)
                                            .password(passwordEncoder.encode("password"))
                                            .email(username+"@example.com")
                                            .build();
                                    return this.userRepository.save(user);
                                })
                );
        initUsers.doOnSubscribe(data -> log.info("data:" + data))
                .thenMany(initUsers)
                .subscribe(
                        data -> log.info("data:" + data), err -> log.error("error:" + err),
                        () -> log.info("done initialization...")
                );
    }
}
