package planeta.kino.demo.service;

import planeta.kino.demo.DTO.UserFullDTO;

import java.io.IOException;

public interface UserInfoService {

    void addUserInfo(UserFullDTO userFullDTO) throws IOException;

    String getUserInfo(Long userId);
}
