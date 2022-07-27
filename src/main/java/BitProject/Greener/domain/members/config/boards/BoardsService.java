package BitProject.Greener.domain.members.config.boards;

import BitProject.Greener.domain.members.Domain.Entity.UserEntity;
import BitProject.Greener.domain.members.repository.MembersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class BoardsService {

    private final BoardsRepository boardsRepository;
    private final MembersRepository membersRepository;

    public BoardsDTO createBoards(BoardsCreateRequest request) {
//        log.info(request.getMembersid());
//        log.info(request.getMembersid());
        UserEntity members = membersRepository.findById(request.getMembersid())
                .orElseThrow(() -> new RuntimeException("아이디 없음"));
        Boards boards = Boards.of(request.getTitle(), request.getContent(), request.getImagePath(), request.getBoardsType());
        boards.mapMembers(members);
        boardsRepository.save(boards);
        return BoardsDTO.convertToDTO(boards);
    }
}
