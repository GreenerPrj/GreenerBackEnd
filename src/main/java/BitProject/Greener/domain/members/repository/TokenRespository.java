package BitProject.Greener.domain.members.repository;


import BitProject.Greener.domain.members.domain.Entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRespository extends JpaRepository<TokenEntity,String> {
        Boolean existsByEmail(String email);
        TokenEntity findByEmail(String email);
}
