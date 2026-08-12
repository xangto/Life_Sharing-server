package xangto.projects.life.api.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.spring.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserServiceImpl extends CrudRepository<UserMapper, UserEntity> implements UserService {

    @Override
    public boolean registerUser(RegisterDTO user) {
        UserEntity registerUser = UserConverter.INSTANCE.registerUser(user);
        registerUser.setRole("Role_admin");
        return this.save(registerUser);
    }

    @Override
    public UserVO findByUsername(String username) {
        LambdaQueryWrapper<UserEntity> wrapper =
                new LambdaQueryWrapper<UserEntity>()
                        .eq(StringUtils.hasLength(username), UserEntity::getUsername, username);
        UserEntity one = this.getOne(wrapper);
        return UserConverter.INSTANCE.toVO(one);
    }
}
