package BitProject.Greener.repository;

import BitProject.Greener.domain.entity.BoardFiles;
import BitProject.Greener.domain.entity.Boards;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BoardFilesRepository extends JpaRepository<BoardFiles, Long> {


    Optional<BoardFiles> findByBoards(Boards boards);

}
