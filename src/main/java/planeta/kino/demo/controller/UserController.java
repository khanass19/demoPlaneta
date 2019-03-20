package planeta.kino.demo.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import planeta.kino.demo.DTO.UserFullDTO;
import planeta.kino.demo.DTO.UserSimpleDTO;
import planeta.kino.demo.entity.Role;
import planeta.kino.demo.entity.User;
import planeta.kino.demo.entity.UserInfo;
import planeta.kino.demo.security.TokenUtils.TokenTool;
import planeta.kino.demo.service.UserInfoService;
import planeta.kino.demo.service.UserService;
import planeta.kino.demo.service.serviceImpl.UserServiceImpl;
import planeta.kino.demo.utils.ObjectMapperUtils;

import java.io.IOException;

@RestController
@CrossOrigin
public class UserController {

    private static final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapperUtils modelMapper;

    @Autowired
    private TokenTool tokenTool;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserInfoService userInfoService;

    @PostMapping("/public/register")
    public String saveUser(@RequestBody User user) throws Exception {
        user.setRole(Role.USER);
        logger.info("User was registered");
        String oldNotHashedPassword = user.getPassword();
        String newHashedPassword =
                passwordEncoder.encode(oldNotHashedPassword);
        user.setPassword(newHashedPassword);
        User userNew = userService.save(modelMapper.map(user, UserSimpleDTO.class));
        return tokenTool.createToken(userNew.getEmail(), user.getRole().name());
    }

    @PostMapping("/public/login")
    public String login(@RequestBody User user) throws Exception {
        User user1 = userService.getOne(modelMapper.map(user, UserSimpleDTO.class));
        if(user1 == null) {
            throw new Exception("User with such creds doesn't exists");
        }
        return tokenTool.createToken(user1.getEmail(), user1.getRole().name());
    }


    @PostMapping("/user-info/save")
    public void addUserInfo(@RequestBody UserFullDTO userFullDTO) throws IOException {
        userInfoService.addUserInfo(userFullDTO);
    }

    @GetMapping("/user-info/get/{id}")
    public String getUserInfo(@PathVariable("id") Long userId) throws IOException {
        return userInfoService.getUserInfo(userId);
    }



}
