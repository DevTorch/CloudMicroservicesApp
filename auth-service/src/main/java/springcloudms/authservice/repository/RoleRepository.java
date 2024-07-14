package springcloudms.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springcloudms.authservice.model.Role;
import springcloudms.authservice.model.RoleNameEnum;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findRoleByName(RoleNameEnum name);
}
