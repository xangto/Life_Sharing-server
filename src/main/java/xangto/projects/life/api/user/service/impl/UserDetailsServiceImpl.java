package xangto.projects.life.api.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import xangto.projects.life.api.user.entity.UserEntity;
import xangto.projects.life.api.user.service.UserService;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserService userService;

    @NonNull
    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<UserEntity>().eq("username", username);
        UserEntity one = userService.getOne(wrapper);
        if (one == null) {
            throw new UsernameNotFoundException(username);
        }

        return User.builder()
                .username(one.getUsername())
                .password(one.getPassword())
                .authorities(new SimpleGrantedAuthority(one.getRole()))
                .disabled(false)
                .build();
    }
}
