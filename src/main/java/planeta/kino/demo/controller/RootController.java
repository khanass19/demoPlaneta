package planeta.kino.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import planeta.kino.demo.DTO.UserSimpleDTO;
import planeta.kino.demo.entity.User;
import planeta.kino.demo.service.UserService;

import java.util.List;

@Controller
public class RootController {

    @Autowired
    private UserService userService;

    @GetMapping("/public/main")
    public String returnMainPage(){
        return "index";
    }

    @GetMapping("/home")
    public String returnHomePage(){
        return "home";
    }

}
