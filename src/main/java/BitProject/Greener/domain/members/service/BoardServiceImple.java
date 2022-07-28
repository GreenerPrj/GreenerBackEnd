package BitProject.Greener.domain.members.service;



import BitProject.Greener.domain.members.domain.Dto.BoardDto;
import BitProject.Greener.domain.members.domain.Entity.BoardEntity;
import BitProject.Greener.domain.members.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardServiceImple implements BoardService {
    private final BoardRepository boardRepository;


    public List<BoardEntity> reading(){
        return boardRepository.findAll();
    }

    public BoardEntity creating(BoardDto boardDto){
        BoardEntity a = boardRepository.save(boardDto.toEntity());
        return a;
    }

    public String updating(BoardDto boardDto, Long id){
        BoardEntity boardEntity = boardRepository.findById(id).get();
        BoardDto a = boardEntity.toDto();
        a.setTitle(boardDto.getTitle());
        a.setContent(boardDto.getContent());
        boardRepository.save(a.toEntity());
        return "수정 성공";
    }

    public String deleteing(Long id){
        boardRepository.deleteById(id);
        return "삭제 성공";
    }

}
