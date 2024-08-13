package springcloudms.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import springcloudms.authservice.model.Account;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("""
            SELECT a FROM Account a JOIN FETCH a.roles WHERE a.email = :email AND a.password = :password
            """)
    Optional<Account> findByEmailAndPassword(@Param("email") String email, @Param("password") String password);

//    @Query("SELECT a FROM Account a JOIN a.roles WHERE a.email = :email")
    Optional<Account> findByEmail(String email);

    Long findAccountIdByEmail(String email);
}
