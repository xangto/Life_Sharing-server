package xangto.projects.life.api.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.spring.repository.CrudRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import xangto.projects.life.api.user.mapper.UserMapper;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends CrudRepository<UserMapper, UserEntity> implements UserService {

    /** 密码加密器：注册时对明文密码 BCrypt 加密后入库，与登录校验（AuthenticationManager + BCryptPasswordEncoder）保持一致 */
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean registerUser(RegisterDTO user) {
        // 用户名唯一性检查：已存在则注册失败，避免重复注册或触发数据库唯一键异常
        if (findByUsername(user.getUsername()) != null) {
            return false;
        }
        UserEntity registerUser = UserConverter.INSTANCE.registerUser(user);
        // 密码加密后入库（此前存明文，与登录侧的 BCrypt 比对矛盾导致登录必失败）
        registerUser.setPassword(passwordEncoder.encode(user.getPassword()));
        // 使用 Spring Security 约定：hasRole("USER") 需要权限字符串以 ROLE_ 开头
        registerUser.setRole("ROLE_USER");
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
