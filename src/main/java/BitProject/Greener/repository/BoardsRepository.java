package BitProject.Greener.repository;

import BitProject.Greener.domain.entity.Boards;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface BoardsRepository extends JpaRepository<Boards, Long> ,BoardsRepositoryCustom{



    @Query(value = "SELECT b FROM Boards b join fetch b.userEntity")
    List<Boards> findAllWithUser();

}
