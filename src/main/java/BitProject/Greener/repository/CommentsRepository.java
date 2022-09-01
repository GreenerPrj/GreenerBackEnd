package BitProject.Greener.repository;


import BitProject.Greener.domain.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface CommentsRepository extends JpaRepository<Comments, Long> {

    List<Comments> findByBoardsId(Long id);


}
