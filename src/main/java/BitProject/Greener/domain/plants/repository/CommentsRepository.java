package BitProject.Greener.domain.plants.repository;

import BitProject.Greener.domain.boards.Boards;
import BitProject.Greener.domain.boards.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentsRepository extends JpaRepository<Comments, Long> {
}
