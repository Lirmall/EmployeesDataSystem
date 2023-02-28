package ru.klokov.employeesdatasystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import ru.klokov.employeesdatasystem.config.SecurityConfig;

@SpringBootApplication
@Import({SecurityConfig.class})
public class EmployeesDataSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeesDataSystemApplication.class, args);
    }

}
