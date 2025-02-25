package kr.co.codenation.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import kr.co.codenation.entity.Role;
import kr.co.codenation.entity.User;

@Component
public class SampleDataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // 기존 데이터 삭제
        userRepository.deleteAll();

        // 암호화된 비밀번호로 사용자 추가
        User student = new User();
        student.setEmail("student@codenation.com");
        student.setPassword(passwordEncoder.encode("student123")); // 암호화
        student.setName("Student");
        student.setRole(Role.ROLE_STUDENT);
        userRepository.save(student);

        System.out.println("샘플 데이터 초기화 완료!");
    }
}