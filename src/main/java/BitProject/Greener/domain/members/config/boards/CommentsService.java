package BitProject.Greener.domain.members.config.boards;

import BitProject.Greener.domain.members.Domain.Entity.UserEntity;
import BitProject.Greener.domain.members.repository.MembersRepository;
import BitProject.Greener.domain.plants.repository.CommentsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CommentsService {

    private final BoardsRepository boardsRepository;
    private final MembersRepository membersRepository;
    private final CommentsRepository commentsRepository;

    public CommentsDTO createComments(CommentsCreateRequest request) {

        Boards boards = boardsRepository.findById(request.getBoardsid())
                .orElseThrow(() -> new RuntimeException("게시글 없음"));
        UserEntity members = membersRepository.findById(request.getMembersid())
                .orElseThrow(() -> new RuntimeException("아이디 없음"));
        Comments comments = Comments.of(request.getContent());
        comments.mapMembersAndBoards(members, boards);
        commentsRepository.save(comments);
        return CommentsDTO.convertToDTO(comments);
    }
}
