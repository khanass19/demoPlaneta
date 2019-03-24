package planeta.kino.demo.service.serviceImpl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import planeta.kino.demo.DTO.MyMultipartFile;
import planeta.kino.demo.DTO.UserFullDTO;
import planeta.kino.demo.entity.User;
import planeta.kino.demo.entity.UserInfo;
import planeta.kino.demo.repository.UserInfoRepository;
import planeta.kino.demo.repository.UserRepository;
import planeta.kino.demo.service.UserInfoService;
import planeta.kino.demo.utils.ObjectMapperUtils;
import sun.misc.BASE64Decoder;

import java.io.*;
import java.util.UUID;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    private static final Logger LOGGER = LogManager
            .getLogger(UserInfoServiceImpl.class.getName());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapperUtils modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public void addUserInfo(UserFullDTO userFullDTO) throws IOException {
        UserInfo userInfo = new UserInfo();
        userInfo.setBirthDate(userFullDTO.getDateBirth());
        userInfo.setAvatarImage(userFullDTO.getAvatar());
        userInfo.setName(userFullDTO.getName());

        User user = userRepository.findById(userFullDTO.getId()).get();
        if(user != null) {
            if(user.getUserInfo() != null) {
                userInfo.setId(user.getUserInfo().getId());
            }
            BASE64Decoder base64Decoder = new BASE64Decoder();
            byte[] fileContent = base64Decoder
                    .decodeBuffer(userFullDTO
                            .getAvatar().split(",")[1]);

            String extension = userFullDTO
                    .getAvatar()
                    .split(",")[0]
                    .split("/")[1]
                    .split(";")[0];
            String name = UUID.randomUUID().toString();
            MyMultipartFile multipartFile =
                    new MyMultipartFile(name, extension, fileContent);
            saveOnMachine(multipartFile);
            userInfo.setAvatarImage(name + "." + extension);
            userInfo.setUser(user);
            userInfoRepository.save(userInfo);
            user.setUserInfo(userInfo);
            userRepository.save(user);
        }
    }

    @Override
    public String getUserInfo(Long userId) {
        User user = userRepository.findById(userId).get();
        return user.getUserInfo().getAvatarImage();
    }

    private void saveOnMachine(MultipartFile multipartFile) throws IOException {
        String PATH = "images";
        File folder = new File(PATH);
        if(!folder.exists()) {
            folder.mkdirs();
        }
        File newMultipartFile = new File
                (PATH + "/" + multipartFile.getOriginalFilename());
        OutputStream ous =
                new FileOutputStream(newMultipartFile);
        BufferedOutputStream buf = new BufferedOutputStream(ous);
        buf.write(multipartFile.getBytes()
        , 0, multipartFile.getBytes().length);
        buf.flush();
    }

}
