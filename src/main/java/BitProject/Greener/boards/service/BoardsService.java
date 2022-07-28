package BitProject.Greener.boards.service;

import BitProject.Greener.boards.Boards;
import BitProject.Greener.boards.controller.request.BoardsCreateRequest;
import BitProject.Greener.boards.dto.BoardsDTO;
import BitProject.Greener.domain.members.domain.Entity.BoardEntity;
import BitProject.Greener.domain.members.domain.Entity.UserEntity;
import BitProject.Greener.domain.members.repository.UserRepository;
import BitProject.Greener.boards.repository.BoardsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class BoardsService {

    private final BoardsRepository boardsRepository;
    private final UserRepository userRepository;

    public BoardsDTO createBoards(BoardsCreateRequest request) {
        UserEntity userEntity = userRepository.findById(request.getMembersid())
                .orElseThrow(() -> new RuntimeException("아이디 없음"));
        Boards boards = Boards.of(request.getTitle(), request.getContent(), request.getImagePath(), request.getBoardsType());
        boards.mapMembers(userEntity);
        boardsRepository.save(boards);
        return BoardsDTO.convertToDTO(boards);
    }

    public List<Boards> reading(){
        return boardsRepository.findAll();
    }
}
