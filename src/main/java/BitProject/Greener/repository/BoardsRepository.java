package BitProject.Greener.repository;

import BitProject.Greener.domain.entity.Boards;
import org.springframework.data.jpa.repository.JpaRepository;



public interface BoardsRepository extends JpaRepository<Boards, Long> {
}
