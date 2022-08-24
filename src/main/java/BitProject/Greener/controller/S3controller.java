package BitProject.Greener.controller;


import BitProject.Greener.service.S3service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequiredArgsConstructor
public class S3controller {
//
//    @Value("${cloud.aws.s3.bucket}")
//    public String S3Bucket; // Bucket 이름
//
//    private final AmazonS3Client amazonS3Client;
//
//    @PostMapping("/upload")
//    public ResponseEntity<Object> upload(MultipartFile[] multipartFileList) throws Exception {
//        List<String> imagePathList = new ArrayList<>();
//
//
//        for(MultipartFile multipartFile: multipartFileList) {
//            String originalName = multipartFile.getOriginalFilename(); // 파일 이름
//            long size = multipartFile.getSize(); // 파일 크기
//
//            ObjectMetadata objectMetaData = new ObjectMetadata();
//            objectMetaData.setContentType(multipartFile.getContentType());
//            objectMetaData.setContentLength(size);
//            // S3에 업로드
//            amazonS3Client.putObject(
//                    new PutObjectRequest(S3Bucket, originalName, multipartFile.getInputStream(), objectMetaData)
//                            .withCannedAcl(CannedAccessControlList.PublicRead)
//            );
//
//            String imagePath = amazonS3Client.getUrl(S3Bucket, originalName).toString(); // 접근가능한 URL 가져오기
//            imagePathList.add(imagePath);
//        }
//
//        return new ResponseEntity<Object>(imagePathList, HttpStatus.OK);
//    }

    private final S3service s3service;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("images") MultipartFile multipartFile) throws IOException {
        return ResponseEntity.ok(
               s3service.upload(multipartFile.getInputStream(), multipartFile.getOriginalFilename(), multipartFile.getSize())
        );
    }

}
