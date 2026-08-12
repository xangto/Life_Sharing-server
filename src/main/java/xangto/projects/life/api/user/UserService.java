package xangto.projects.life.api.user;

import com.baomidou.mybatisplus.extension.repository.IRepository;
import org.springframework.stereotype.Service;

@Service
public interface UserService extends IRepository<UserEntity> {
    boolean registerUser(RegisterDTO user);
    UserVO findByUsername(String username);
}
