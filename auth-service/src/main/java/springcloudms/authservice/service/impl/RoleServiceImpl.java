package springcloudms.authservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springcloudms.authservice.model.Role;
import springcloudms.authservice.model.RoleNameEnum;
import springcloudms.authservice.repository.RoleRepository;
import springcloudms.authservice.service.RoleService;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    @Override
    @Transactional(readOnly = true)
    public Role findRoleByName(RoleNameEnum name) {
        return roleRepository.findRoleByName(name);
    }
}
