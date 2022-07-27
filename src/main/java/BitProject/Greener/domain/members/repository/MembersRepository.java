package BitProject.Greener.domain.members.repository;

import BitProject.Greener.domain.members.Domain.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MembersRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
}
