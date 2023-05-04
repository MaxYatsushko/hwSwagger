package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.service.InfoService;
import ru.hogwarts.school.service.InfoServiceProd;

@RestController
public class InfoController {

    @Autowired
    private InfoService infoService;


    @GetMapping("/getPort")
    public int getPort(){
        return infoService.getPort();
    }

}
