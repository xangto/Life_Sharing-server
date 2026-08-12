package xangto.projects.life.api.user.service;

import com.baomidou.mybatisplus.extension.repository.IRepository;
import xangto.projects.life.api.user.dto.RegisterDTO;
import xangto.projects.life.api.user.entity.UserEntity;
import xangto.projects.life.api.user.vo.UserVO;

public interface UserService extends IRepository<UserEntity> {
    boolean registerUser(RegisterDTO user);

    UserVO findByUsername(String username);
}
