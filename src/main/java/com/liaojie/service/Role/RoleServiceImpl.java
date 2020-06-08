package com.liaojie.service.Role;

import com.liaojie.dao.Role.roleMapper;
import com.liaojie.entity.Role;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Resource
    private roleMapper mapper;

    @Override
    public List<Role> getRoleList() {
        return mapper.getRoleList();
    }
}
