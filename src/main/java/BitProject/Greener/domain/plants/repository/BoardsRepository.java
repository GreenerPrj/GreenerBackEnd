package BitProject.Greener.domain.plants.repository;

import BitProject.Greener.domain.boards.Boards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardsRepository extends JpaRepository<Boards, Long> {
}
