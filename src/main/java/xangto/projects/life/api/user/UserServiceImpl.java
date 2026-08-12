package xangto.projects.life.api.user;

import com.baomidou.mybatisplus.spring.repository.CrudRepository;

public class UserServiceImpl extends CrudRepository<UserMapper, UserEntity> implements UserService {

    @Override
    public boolean registerUser(RegisterDTO user) {
        UserEntity registerUser = UserConverter.INSTANCE.registerUser(user);
        registerUser.setRole("Role_admin");
        return this.save(registerUser);
    }
}
