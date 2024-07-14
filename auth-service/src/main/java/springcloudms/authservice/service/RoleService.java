package springcloudms.authservice.service;

import springcloudms.authservice.model.Role;
import springcloudms.authservice.model.RoleNameEnum;

public interface RoleService {

    Role findRoleByName(RoleNameEnum name);
}
