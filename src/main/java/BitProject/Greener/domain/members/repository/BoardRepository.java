package BitProject.Greener.domain.members.repository;


import BitProject.Greener.domain.members.domain.Entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<BoardEntity,Long> {
}
