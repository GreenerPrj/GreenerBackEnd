package BitProject.Greener.service;

import BitProject.Greener.controller.request.CommentsUpdateRequest;
import BitProject.Greener.domain.entity.Boards;
import BitProject.Greener.domain.entity.Comments;
import BitProject.Greener.domain.dto.request.CommentsCreateRequest;
import BitProject.Greener.domain.dto.CommentsDTO;
import BitProject.Greener.repository.BoardsRepository;
import BitProject.Greener.repository.CommentsRepository;
import BitProject.Greener.domain.entity.UserEntity;
import BitProject.Greener.repository.UserRepository;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentsService {

    private final BoardsRepository boardsRepository;
    private final UserRepository userRepository;
    private final CommentsRepository commentsRepository;

    public CommentsDTO createComments(CommentsCreateRequest request, HttpServletRequest request2) {
        Boards boards = boardsRepository.findById(request.getBoardsid())
            .orElseThrow(() -> new RuntimeException("게시글 없음"));
        UserEntity userEntity = userRepository.findById(request.getMembersid())
            .orElseThrow(() -> new RuntimeException("아이디 없음"));
        if (Objects.nonNull(request.getParentCommentsId())) {
            Comments parentComments = commentsRepository.findById(request.getParentCommentsId())
                .orElseThrow(() -> new RuntimeException("상위 댓글이 존재하지 않습니다."));
            Comments childComments = Comments.of(request.getContent(),userEntity.getNickName(),request.getCreatedDateTime());
            childComments.mapMembersAndBoardsAndParentComments(userEntity, boards, parentComments);
            return CommentsDTO.convertToDTO(childComments);
        } else {
            Comments comments = Comments.of(request.getContent(), userEntity.getNickName(), request.getCreatedDateTime());
            comments.mapMembersAndBoards(userEntity, boards);
            commentsRepository.save(comments);
            return CommentsDTO.convertToDTO(comments);
        }

    }

    public Long update(Long id, CommentsUpdateRequest commentsUpdateRequest) {
        Comments comments = commentsRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));

        comments.update(commentsUpdateRequest.getContent());

        return id;
    }

    public void delete(Long id) {
        Comments comments = commentsRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));

        commentsRepository.delete(comments);
    }
}
