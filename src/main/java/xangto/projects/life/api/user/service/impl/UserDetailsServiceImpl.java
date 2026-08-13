package xangto.projects.life.api.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.spring.repository.CrudRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.jspecify.annotations.NonNull;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import xangto.projects.life.api.user.converter.UserConverter;
import xangto.projects.life.api.user.dto.RegisterDTO;
import xangto.projects.life.api.user.dto.UpdatePwdDTO;
import xangto.projects.life.api.user.entity.UserEntity;
import xangto.projects.life.api.user.mapper.UserMapper;
import xangto.projects.life.api.user.service.UserService;
import xangto.projects.life.api.user.vo.UserVO;
import xangto.projects.life.common.BusinessException;
import xangto.projects.life.common.ResultCode;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl extends CrudRepository<UserMapper, UserEntity> implements UserService, UserDetailsService {
    /**
     * 密码加密器：注册时对明文密码 BCrypt 加密后入库，与登录校验（AuthenticationManager + BCryptPasswordEncoder）保持一致
     */
    private final PasswordEncoder passwordEncoder;

    @NonNull
    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<UserEntity> wrapper =
                new LambdaQueryWrapper<UserEntity>().eq(StringUtils.isNotBlank(username), UserEntity::getUsername, username);
        UserEntity one = this.getOne(wrapper);
        if (one == null) {
            throw new UsernameNotFoundException(username);
        }

        return User.builder()
                .username(one.getUsername())
                .password(one.getPassword())
                .authorities(new SimpleGrantedAuthority(one.getRole()))
                .disabled(false)
//                .accountExpired(false)   //账号是否过期
//                .credentialsExpired(false) //凭证(密码)是否过期
//                .accountLocked(false)    //账号是否锁定
                .build();
    }

    @Override
    public void registerUser(RegisterDTO user) {
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
        LambdaQueryWrapper<UserEntity> eq =
                new LambdaQueryWrapper<UserEntity>().eq(StringUtils.isNotBlank(username), UserEntity::getUsername, username);
        UserEntity userEntity = this.getOne(eq);
        return UserConverter.INSTANCE.toVO(userEntity);
    }

    @Override
    public void updateUser(UpdatePwdDTO dto) {
        UserEntity userEntity = this.getById(dto.getUserId());
        if (userEntity == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "用户不存在");
        }
        boolean b = passwordEncoder.matches(dto.getOldPwd(), userEntity.getPassword());
        if (b) {
            userEntity.setPassword(passwordEncoder.encode(dto.getNewPwd()));
            this.updateById(userEntity);
        } else {
            throw new BusinessException(ResultCode.ERROR, "旧密码错误");
        }
    }
}
