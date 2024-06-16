package cz.cvut.fel.nss.sem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

@SpringBootApplication
public class UserMicroservice {
    public static void main(String[] args) throws IOException {
        SpringApplication.run(UserMicroservice.class, args);
    }
}
