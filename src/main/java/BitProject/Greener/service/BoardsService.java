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

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;
    @Value("${cloud.aws.s3.dir}")
    public String dir;

    @Transactional
    public BoardsDTO createBoards(BoardsCreateRequest request, MultipartFile file, HttpServletRequest request2) throws IOException {
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
                Long fileSize = file.getSize();
                InputStream inputStream = file.getInputStream();
                String originFileName = file.getOriginalFilename();
                String fileName = UUID.randomUUID().toString();
                String savePath = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String filePath = "/board/" + savePath;

                ObjectMetadata objMeta = new ObjectMetadata();
                objMeta.setContentLength(fileSize);
                String bucket_full = bucket + dir + filePath;
                amazonS3Client.putObject(bucket_full, fileName, inputStream, objMeta);


                BoardFiles boardFiles = BoardFiles.of(originFileName, fileName, filePath);
                boardFiles.mapBoards(boards);
                boardFilesRepository.save(boardFiles);
            }
            return BoardsDTO.convertToDTO(boards);
        }
    }

    @Transactional
    public Long update(Long id, BoardsUpdateRequest boardsUpdateRequest, MultipartFile file) throws IOException {

        Boards boards = boardsRepository.findById(id)
                .orElseThrow(() -> new
                        IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
//         업데이트
        boards.update(boardsUpdateRequest.getTitle(),
                boardsUpdateRequest.getContent());

//
        Optional<BoardFiles> boardFiles = boardFilesRepository.findByBoardsId(id);
        log.info(boardFiles == null);
        log.info(!boardFiles.isPresent());
        if (file != null) {
            if (boardFiles.isPresent()) {
                String bucket_full = bucket + dir + boardFiles.get().getFilePath();
                DeleteObjectRequest request = new DeleteObjectRequest(bucket_full, boardFiles.get().getFileName());
                amazonS3Client.deleteObject(request);
                boardFilesRepository.delete(boardFiles.get());


                String originFileName = file.getOriginalFilename();
                String fileName = UUID.randomUUID().toString();
//                String savePath = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String savePath2 = boardFiles.get().getFilePath().split("/")[2];
                String filePath = "/board/" + savePath2;

                ObjectMetadata objMeta = new ObjectMetadata();
                objMeta.setContentLength(file.getSize());
                InputStream inputStream = null;
                try {
                    inputStream = file.getInputStream();
                } catch (IOException e) {
                    log.info(e);
                }
                amazonS3Client.putObject(bucket_full, fileName, inputStream, objMeta);


                BoardFiles boardFiles2 = BoardFiles.of(originFileName, fileName, filePath);
                boardFiles2.mapBoards(boards);
                boardFilesRepository.save(boardFiles2);
            }
            else{
                Long fileSize = file.getSize();
                InputStream inputStream = file.getInputStream();
                String originFileName = file.getOriginalFilename();
                String fileName = UUID.randomUUID().toString();
                String savePath2 = boards.getCreatedDateTime().toString().split("T")[0];

                String filePath = "/board/" + savePath2;
                ObjectMetadata objMeta = new ObjectMetadata();
                objMeta.setContentLength(fileSize);
                String bucket_full = bucket + dir + filePath;
                amazonS3Client.putObject(bucket_full, fileName, inputStream, objMeta);


                BoardFiles boardFiles2 = BoardFiles.of(originFileName, fileName, filePath);
                boardFiles2.mapBoards(boards);
                boardFilesRepository.save(boardFiles2);
            }
        }

        return id;
    }


    public void delete(Long id) {
        Boards boards = boardsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다."));
        //파일 경로 지정
        try {
            boardFilesRepository.findByBoardsId(id).ifPresent(boardFiles -> {
                String bucket_full = bucket + dir + boardFiles.getFilePath();
                DeleteObjectRequest request = new DeleteObjectRequest(bucket_full, boardFiles.getFileName());
                amazonS3Client.deleteObject(request);
                boardFilesRepository.delete(boardFiles);
            });

        } catch (Exception e) {
        }

        boardsRepository.delete(boards);
    }

    @Transactional
    public BoardsWithBoardFilesDTO getDetailWithBoardFiles(Long boardsId) throws IOException {
        // 게시글 찾기
        Boards boards = boardsRepository.findById(boardsId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다."));

//        Optional<List<Comments>> commentsList = commentsRepository.findByBoardsId(boardsId);

//        comments.get().forEach(Comments -> Comments.getContent());

        // 첨부파일은 있을수도 없을수도 있어서 optional로 받았음
        Optional<BoardFiles> boardFiles = boardFilesRepository.findByBoards(boards);
        // 우선 board는 필수니까 DTO로 변환해주고
        //boardFiles.get().getOriginFileName() == boards.
        BoardsWithBoardFilesDTO boardsWithBoardFilesDTO = BoardsWithBoardFilesDTO.convertToBoardDTO(boards);
        if (boardFiles.isPresent()) {
            boardsWithBoardFilesDTO.setImg("https://cl6-2.s3.ap-northeast-2.amazonaws.com" + dir + boardFiles.get().getFilePath() + "/" + boardFiles.get().getFileName());
            // 파일이 있으면 변환한 DTO에 파일 정보도 세팅해서
            boardFiles.ifPresent(boardsWithBoardFilesDTO::mapBoardsFile);
//        List<String> comments = commentsList.get().stream().map(Comments -> {
//            return Comments.getContent();
//        }).collect(Collectors.toList());
//
//        boardsWithBoardFilesDTO.mapComments(comments);
        }
        boardsWithBoardFilesDTO.setUserId(boards.getUserEntity().getId());
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


}






