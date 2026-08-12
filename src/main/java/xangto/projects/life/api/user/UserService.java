package xangto.projects.life.api.user;

import com.baomidou.mybatisplus.extension.repository.IRepository;

public interface UserService extends IRepository<UserEntity> {
    boolean registerUser(RegisterDTO user);

    UserVO findByUsername(String username);
}
