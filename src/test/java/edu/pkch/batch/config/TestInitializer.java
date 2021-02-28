package edu.pkch.batch.config;

import edu.pkch.batch.domain.user.User;
import edu.pkch.batch.domain.user.UserRepository;
import edu.pkch.batch.domain.user.enums.Grade;
import edu.pkch.batch.domain.user.enums.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

@Component
public class TestInitializer implements ApplicationRunner {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        List<User> users = IntStream.rangeClosed(1, 100)
//                .mapToObj(index -> User.builder()
//                        .name("name" + index)
//                        .email("name" + index + "@woowahan.com")
//                        .password("qwer1234")
//                        .userStatus(UserStatus.ACTIVE)
//                        .grade(Grade.VIP)
//                        .build()
//                )
//                .collect(toList());
//
//        userRepository.saveAll(users);
    }
}
