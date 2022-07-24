package BitProject.Greener.domain.boards;

import BitProject.Greener.domain.members.Members;
import BitProject.Greener.domain.members.repository.MembersRepository;
import BitProject.Greener.domain.members.repository.BoardsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardsService {

    private final BoardsRepository boardsRepository;
    private final MembersRepository membersRepository;

    public BoardsDTO createBoards(BoardsCreateRequest request) {
        Members members = membersRepository.findById(request.getMembersid())
                .orElseThrow(() -> new RuntimeException("아이디 없음"));
        Boards boards = Boards.of(request.getTitle(), request.getContent(), request.getImagePath(), request.getBoardsType());
        boards.mapMembers(members);
        boardsRepository.save(boards);
        return BoardsDTO.convertToDTO(boards);
    }
}
