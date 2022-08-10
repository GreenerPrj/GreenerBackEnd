package BitProject.Greener.repository;

import BitProject.Greener.domain.entity.BoardsCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardsCategoryRepository extends JpaRepository<BoardsCategory, Long> {
    BoardsCategory findByName(String name);
}
