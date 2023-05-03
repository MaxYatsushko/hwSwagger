package ru.hogwarts.school.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile(value = "prod")
public class InfoServiceProd implements InfoService{
    public int getPort(){
        return 8080;
    }
}
