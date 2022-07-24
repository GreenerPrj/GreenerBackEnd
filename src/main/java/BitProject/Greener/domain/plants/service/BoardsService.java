package BitProject.Greener.domain.plants.service;


import BitProject.Greener.domain.members.repository.MembersRepository;
import BitProject.Greener.domain.plants.dto.BoardsDTO;
import BitProject.Greener.domain.plants.dto.MyPlantsDTO;
import BitProject.Greener.domain.plants.repository.BoardsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardsService {

    private final BoardsRepository boardsRepository;

    public Long save(BoardsDTO boardsDTO) {
        return boardsRepository.save(boardsDTO.toEntity()).getId();

    }

}
