package BitProject.Greener.boards.repository;

import BitProject.Greener.boards.Boards;
import org.springframework.data.jpa.repository.JpaRepository;



public interface BoardsRepository extends JpaRepository<Boards, Long> {
}
