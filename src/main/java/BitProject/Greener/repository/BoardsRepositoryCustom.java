package BitProject.Greener.repository;

import BitProject.Greener.domain.dto.SearchDTO;
import BitProject.Greener.domain.entity.Boards;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardsRepositoryCustom {
    List<Boards> boardsListQueryDSL(SearchDTO searchDTO);

    Page<Boards> boardsListPagingQueryDSL(SearchDTO searchDTO, Pageable pageable);
}