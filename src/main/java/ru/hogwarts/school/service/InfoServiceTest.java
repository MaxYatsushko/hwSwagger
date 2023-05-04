package ru.hogwarts.school.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("!prod")
public class InfoServiceTest implements InfoService {
    public int getPort(){
        return 8081;
    }
}
