package BitProject.Greener.service;

import BitProject.Greener.domain.entity.Boards;
import BitProject.Greener.domain.entity.Comments;
import BitProject.Greener.controller.request.CommentsCreateRequest;
import BitProject.Greener.domain.dto.CommentsDTO;
import BitProject.Greener.repository.BoardsRepository;
import BitProject.Greener.repository.CommentsRepository;
import BitProject.Greener.domain.entity.UserEntity;
import BitProject.Greener.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentsService {

    private final BoardsRepository boardsRepository;
    private final UserRepository userRepository;
    private final CommentsRepository commentsRepository;

    public CommentsDTO createComments(CommentsCreateRequest request) {
        Boards boards = boardsRepository.findById(request.getBoardsid())
                .orElseThrow(() -> new RuntimeException("게시글 없음"));
        UserEntity userEntity = userRepository.findById(request.getMembersid())
                .orElseThrow(() -> new RuntimeException("아이디 없음"));
        Comments comments = Comments.of(request.getContent());
        comments.mapMembersAndBoards(userEntity, boards);
        commentsRepository.save(comments);
        return CommentsDTO.convertToDTO(comments);
    }
}
