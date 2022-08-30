package BitProject.Greener.repository;

import BitProject.Greener.domain.entity.Boards;
import BitProject.Greener.domain.entity.Comments;
import BitProject.Greener.domain.entity.MyPlants;
import BitProject.Greener.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface CommentsRepository extends JpaRepository<Comments, Long> {
    Optional<List<Comments>> findByBoardsId(Long id);


}
