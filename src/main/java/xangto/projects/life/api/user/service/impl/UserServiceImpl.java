package xangto.projects.life.api.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.spring.repository.CrudRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import xangto.projects.life.api.user.converter.UserConverter;
import xangto.projects.life.api.user.dto.RegisterDTO;
import xangto.projects.life.api.user.entity.UserEntity;
import xangto.projects.life.api.user.mapper.UserMapper;
import xangto.projects.life.api.user.service.UserService;
import xangto.projects.life.api.user.vo.UserVO;
import xangto.projects.life.common.BusinessException;
import xangto.projects.life.common.ResultCode;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends CrudRepository<UserMapper, UserEntity> implements UserService {

    /** 密码加密器：注册时对明文密码 BCrypt 加密后入库，与登录校验（AuthenticationManager + BCryptPasswordEncoder）保持一致 */
    private final PasswordEncoder passwordEncoder;

    @Override
    public void registerUser(RegisterDTO user) {
        // 用户名唯一性检查：已存在则抛出业务异常，由全局异常处理器返回 HTTP 409
        if (findByUsername(user.getUsername()) != null) {
            throw new BusinessException(ResultCode.CONFLICT, "用户名已存在");
        }
        UserEntity registerUser = UserConverter.INSTANCE.registerUser(user);
        // 密码加密后入库（此前存明文，与登录侧的 BCrypt 比对矛盾导致登录必失败）
        registerUser.setPassword(passwordEncoder.encode(user.getPassword()));
        // 使用 Spring Security 约定：hasRole("USER") 需要权限字符串以 ROLE_ 开头
        registerUser.setRole("ROLE_USER");
        try {
            this.save(registerUser);
        } catch (DuplicateKeyException e) {
            // 并发兜底：数据库唯一索引保证原子性，两个同名注册同时通过预检查时，
            // 后插入者命中唯一索引抛 DuplicateKeyException，同样转为 409
            throw new BusinessException(ResultCode.CONFLICT, "用户名已存在");
        }
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
