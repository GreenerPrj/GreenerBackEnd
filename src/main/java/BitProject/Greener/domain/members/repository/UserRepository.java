package BitProject.Greener.domain.members.repository;

import BitProject.Greener.domain.members.domain.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
}
