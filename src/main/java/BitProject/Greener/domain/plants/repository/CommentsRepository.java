package BitProject.Greener.domain.plants.repository;

import BitProject.Greener.domain.members.config.boards.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentsRepository extends JpaRepository<Comments, Long> {
}
