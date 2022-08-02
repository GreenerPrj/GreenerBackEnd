package BitProject.Greener.repository;


import BitProject.Greener.domain.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRespository extends JpaRepository<TokenEntity,String> {
        TokenEntity findByEmail(String email);
}
