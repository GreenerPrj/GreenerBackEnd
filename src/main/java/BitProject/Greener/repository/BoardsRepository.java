package BitProject.Greener.repository;

import BitProject.Greener.domain.entity.Boards;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface BoardsRepository extends JpaRepository<Boards, Long> {



    @Query(value = "SELECT b FROM Boards b join fetch b.userEntity")
    List<Boards> findAllWithUser();

    @Query(value = "SELECT b FROM Boards b join fetch b.userEntity join fetch b.category")
    List<Boards> findAllWithPagination(Pageable pageable);


}
