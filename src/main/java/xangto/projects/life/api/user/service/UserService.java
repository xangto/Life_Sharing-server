package xangto.projects.life.api.user.service;

import com.baomidou.mybatisplus.extension.repository.IRepository;
import xangto.projects.life.api.user.dto.RegisterDTO;
import xangto.projects.life.api.user.entity.UserEntity;
import xangto.projects.life.api.user.vo.UserVO;

public interface UserService extends IRepository<UserEntity> {
    /**
     * 注册用户：用户名已存在等业务失败会抛出 {@link xangto.projects.life.common.BusinessException}
     */
    void registerUser(RegisterDTO user);

    UserVO findByUsername(String username);
}
