package BitProject.Greener.service;

import BitProject.Greener.controller.request.BoardsUpdateRequest;
import BitProject.Greener.domain.dto.*;
import BitProject.Greener.domain.dto.request.BoardsCreateRequest;
import BitProject.Greener.domain.entity.*;
import BitProject.Greener.jwt.TokenProvider;
import BitProject.Greener.repository.*;

import java.io.*;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardsService {

    private final BoardsRepository boardsRepository;
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final BoardFilesRepository boardFilesRepository;
    private final BoardsCategoryRepository boardsCategoryRepository;
    private final CommentsRepository commentsRepository;
    private static final String absPath = "src/main/resources/static/images/boards";


//    public BoardsDTO createBoards(BoardsCreateRequest request, MultipartFile file) {
//        UserEntity userEntity = userRepository.findById(request.getMembersid())
//                .orElseThrow(() -> new RuntimeException("아이디 없음"));
//        Boards boards = Boards.of(request.getTitle(), request.getContent(), request.getNickName(),request.getBoardsType());

    @Transactional
    public BoardsDTO createBoards(BoardsCreateRequest request, MultipartFile file, HttpServletRequest request2) {
        String username = null;
        try {
            String token = tokenProvider.parseBearerToken(request2);
            username = tokenProvider.tokenEncry(token);
        } catch (ExpiredJwtException e) {
            username = e.getClaims().getSubject();
        } finally {
            UserEntity userEntity = userRepository.findByEmail(username);
            BoardsCategory boardsCategory = boardsCategoryRepository.findById(request.getCategoryid())
                    .orElseThrow(() -> new RuntimeException("카테고리가 없습니다."));
            Boards boards = Boards.of(request.getTitle(), request.getContent(), userEntity.getNickName(), boardsCategory);


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

    @Transactional
    public Long update(Long id, BoardsUpdateRequest boardsUpdateRequest, MultipartFile file) {

        Boards boards = boardsRepository.findById(id)
                .orElseThrow(() -> new
                        IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

//         업데이트
        boards.update(boardsUpdateRequest.getTitle(),
                boardsUpdateRequest.getContent());

//         기존 이미지 삭제 후 다시 요청온 이미지 저장

            try {
                boardFilesRepository.findByBoardsId(id).ifPresent(boardFiles -> {
                    String fullname = absPath + "/" + boardFiles.getFilePath();
                    //현재 게시판에 존재하는 파일객체를 만듬
                    File files = new File(fullname);

                    if (file!=null&&!boardFiles.getOriginFileName().equals(file.getOriginalFilename())) { // 파일이 존재하면
                        files.delete(); // 파일 삭제
                        boardFilesRepository.delete(boardFiles);
                    }

                    log.info("22222");

                });

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (file != null) {
                    String originFileName = file.getOriginalFilename();
                    String fileName = UUID.randomUUID().toString();
                    String absPath = "src/main/resources/static/images/";
                    String savePath = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    String filePath = savePath + "/" + fileName + ".png";
                    String filePath2 = Paths.get(absPath + savePath, fileName + ".png").toString();
                    File saveFile = new File(absPath + savePath);

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

//            if(!file.isEmpty()){
//                files.stream().map(file -> {
//                    String originFileName = file.getOriginalFilename();
//                    String fileName = UUID.randomUUID().toString();
//                    String savePath = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//                    String filePath = savePath + "/" + fileName + ".png";
//                    String filePath2 = Paths.get(absPath+savePath, fileName+".png").toString();
//                    File saveFile = new File(absPath+savePath);
//                    if (!saveFile.exists()) { //저장 디렉토리가 없으면 생성
//                        saveFile.mkdir();
//                    }
//
//                    try {
//                        file.transferTo(Paths.get(filePath2)); // 사진저장
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    BoardFiles boardFiles = BoardFiles.of(originFileName, fileName, filePath);
//                    boardFiles.mapBoards(boards);
//                    return boardFilesRepository.save(boardFiles);
//                });

//            }
            }



        return id;
    }


    public void delete(Long id) {
        Boards boards = boardsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다."));

        //파일 경로 지정
        String path = "src/main/resources/static/images/boards";
        try {


            boardFilesRepository.findByBoardsId(id).ifPresent(boardFiles -> {
                String fullname = path + boardFiles.getFilePath();
                //현재 게시판에 존재하는 파일객체를 만듬
                File file = new File(fullname);
                if (file.exists()) { // 파일이 존재하면
                    file.delete(); // 파일 삭제
                }
                boardFilesRepository.delete(boardFiles);
            });


        }
        catch (Exception e){

        }

        boardsRepository.delete(boards);
    }
    @Transactional
    public BoardsWithBoardFilesDTO getDetailWithBoardFiles(Long boardsId) throws IOException {
        // 게시글 찾기
        log.info(boardsId);
        Boards boards = boardsRepository.findById(boardsId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다."));

//        Optional<List<Comments>> commentsList = commentsRepository.findByBoardsId(boardsId);
//        commentsList.get().forEach(Comments -> log.info(Comments.getNickName()));
//        commentsList.get().forEach(Comments -> log.info(Comments.getCreatedDateTime()));
        // 첨부파일은 있을수도 없을수도 있어서 optional로 받았음
        Optional<BoardFiles> boardFiles = boardFilesRepository.findByBoards(boards);
        // 우선 board는 필수니까 DTO로 변환해주고
        BoardsWithBoardFilesDTO boardsWithBoardFilesDTO = BoardsWithBoardFilesDTO.convertToBoardDTO(boards);

        String addressPath = "./src/main/resources/static/images/";

        try {
            InputStream imageStream = new FileInputStream(addressPath + boardFiles.get().getFilePath());
            byte[] imageByteArray = IOUtils.toByteArray(imageStream);
            boardsWithBoardFilesDTO.setImg("http://localhost:8080/api/v1/boards/" + boardsId + "/detail/images");
            boardsWithBoardFilesDTO.setImg2(imageByteArray);
            boardsWithBoardFilesDTO.setUserId(boards.getUserEntity().getId());
        } catch (Exception e) {
            boardsWithBoardFilesDTO.setUserId(boards.getUserEntity().getId());
        }

        // 파일이 있으면 변환한 DTO에 파일 정보도 세팅해서
        boardFiles.ifPresent(boardsWithBoardFilesDTO::mapBoardsFile);
//        List<String> comments = commentsList.get().stream().map(Comments -> {
//            return Comments.getContent();
//        }).collect(Collectors.toList());
//
//        List<LocalDateTime> comments1 = commentsList.get().stream().map(Comments -> {
//            return Comments.getCreatedDateTime();
//        }).collect(Collectors.toList());
//        List<String> comments2 = commentsList.get().stream().map(Comments -> {
//            return Comments.getNickName();
//        }).collect(Collectors.toList());
//        boardsWithBoardFilesDTO.mapComments(comments,comments1,comments2);

        // 리턴해주면 끝
        return boardsWithBoardFilesDTO;

    }
    @Transactional
    public Page<BoardsWithUserDTO> getBoardsWithUserDTO(Pageable pageable) {

        List<Boards> boardList = boardsRepository.findAllWithPagination(pageable);

//        log.info(boardList.stream().map(board -> {
//            return BoardsWithUserDTO.convertToDto(board, board.getUserEntity());
//        }).collect(Collectors.toList()));
        List<BoardsWithUserDTO> boards = boardList.stream().map(board -> {
            return BoardsWithUserDTO.convertToDto(board, board.getUserEntity(),
                    board.getCategory());

        }).collect(Collectors.toList());
        return new PageImpl<>(boards, pageable, boardList.size());

    }

    @Transactional(readOnly = true)
    public List<BoardsCategoryDTO> boardsCategoryList() {
        List<BoardsCategory> boardsCategoryList = boardsCategoryRepository.findAll();

        Stream<BoardsCategory> stream = boardsCategoryList.stream();

        return stream.map(BoardsCategoryDTO::convertToBoardsCategoryDTO).collect(Collectors.toList());
    }





}






