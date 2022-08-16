package BitProject.Greener.service;

import BitProject.Greener.controller.request.BoardsUpdateRequest;
import BitProject.Greener.domain.dto.BoardsWithBoardFilesDTO;
import BitProject.Greener.domain.dto.BoardsWithUserDTO;
import BitProject.Greener.domain.dto.request.BoardsCreateRequest;
import BitProject.Greener.domain.entity.BoardFiles;
import BitProject.Greener.domain.entity.Boards;
import BitProject.Greener.domain.dto.BoardsDTO;
import BitProject.Greener.domain.entity.UserEntity;
import BitProject.Greener.jwt.TokenProvider;
import BitProject.Greener.repository.BoardFilesRepository;
import BitProject.Greener.repository.UserRepository;
import BitProject.Greener.repository.BoardsRepository;

import java.io.*;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class BoardsService {

    private final BoardsRepository boardsRepository;
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final BoardFilesRepository boardFilesRepository;


//    public BoardsDTO createBoards(BoardsCreateRequest request, MultipartFile file) {
//        UserEntity userEntity = userRepository.findById(request.getMembersid())
//                .orElseThrow(() -> new RuntimeException("아이디 없음"));
//        Boards boards = Boards.of(request.getTitle(), request.getContent(), request.getNickName(),request.getBoardsType());


    public BoardsDTO createBoards(BoardsCreateRequest request, MultipartFile file, HttpServletRequest request2) {
        String username = null;
        try {
            String token = tokenProvider.parseBearerToken(request2);
            username = tokenProvider.tokenEncry(token);
        } catch (ExpiredJwtException e) {
            username = e.getClaims().getSubject();
        } finally {

            UserEntity userEntity = userRepository.findByEmail(username);
//        .orElseThrow(() -> new RuntimeException("아이디 없음"));

            Boards boards = Boards.of(request.getTitle(), request.getContent(), userEntity.getNickName(), request.getBoardsType());
            boards.mapMembers(userEntity);
            boardsRepository.save(boards);

            if (file != null) {
                String originFileName = file.getOriginalFilename();
                String fileName = UUID.randomUUID().toString();
                String absPath = "src/main/resources/static/images/";
                String savePath = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String filePath = savePath + "/" + fileName + ".png";
                String filePath2 = Paths.get(absPath+savePath, fileName+".png").toString();
                File saveFile = new File(absPath+savePath);

                if (!saveFile.exists()) { //저장 디렉토리가 없으면 생성
                    saveFile.mkdir();
                }

                try {
                    file.transferTo(Paths.get(filePath2)); // 사진저장
                } catch (Exception e) {
                    e.printStackTrace();
                }
                BoardFiles boardFiles = BoardFiles.of(originFileName, fileName, filePath);
                boardFiles.mapBoards(boards);
                boardFilesRepository.save(boardFiles);
            }
            return BoardsDTO.convertToDTO(boards);
        }
    }

    public Long update(Long id, BoardsUpdateRequest boardsUpdateRequest) {
        Boards boards = boardsRepository.findById(id)
                .orElseThrow(() -> new
                        IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        boards.update(boardsUpdateRequest.getTitle(),
                boardsUpdateRequest.getContent());

        return id;
    }

    public void delete(Long id) {
        BoardFiles boards2 = boardFilesRepository.findByBoardsId(id);
                boardFilesRepository.delete(boards2);
        Boards boards = boardsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다."));

        boardsRepository.delete(boards);
    }

    public List<Boards> reading() {
        return boardsRepository.findAll();
    }

    public BoardsWithBoardFilesDTO getDetailWithBoardFiles(Long boardsId) throws IOException {
        // 게시글 찾기
        Boards boards = boardsRepository.findById(boardsId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다."));
        // 첨부파일은 있을수도 없을수도 있어서 optional로 받았음
        Optional<BoardFiles> boardFiles = boardFilesRepository.findByBoards(boards);
        // 우선 board는 필수니까 DTO로 변환해주고

        BoardsWithBoardFilesDTO boardsWithBoardFilesDTO = BoardsWithBoardFilesDTO.convertToBoardDTO(boards);

        String addressPath = "http://localhost:8080/images/";
        try {
            boardsWithBoardFilesDTO.setImg(addressPath + boardFiles.get().getFilePath());
        } catch (Exception e) {}

        // 파일이 있으면 변환한 DTO에 파일 정보도 세팅해서
        boardFiles.ifPresent(boardsWithBoardFilesDTO::mapBoardsFile);
        // 리턴해주면 끝

        return boardsWithBoardFilesDTO;
    }

    public List<BoardsWithUserDTO> getBoardsWithUserDTO() {

        List<Boards> boardList = boardsRepository.findAllWithUser();
        Collections.reverse(boardList);

        return boardList.stream().map(board -> {
            return BoardsWithUserDTO.convertToDto(board, board.getUserEntity());
        }).collect(Collectors.toList());
    }
}


