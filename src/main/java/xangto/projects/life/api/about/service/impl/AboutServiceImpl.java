package xangto.projects.life.api.about.service.impl;

import com.baomidou.mybatisplus.spring.repository.CrudRepository;
import org.springframework.stereotype.Service;
import xangto.projects.life.api.about.entity.AboutEntity;
import xangto.projects.life.api.about.mapper.AboutMapper;
import xangto.projects.life.api.about.service.AboutService;

@Service
public class AboutServiceImpl extends CrudRepository<AboutMapper, AboutEntity> implements AboutService {
}
