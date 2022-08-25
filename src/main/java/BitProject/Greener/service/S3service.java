package BitProject.Greener.service;




import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class S3service {
    @Value("${cloud.aws.s3.bucket}")
    public String bucket;

    @Value("${cloud.aws.s3.dir}")
    public String dir;

    private final AmazonS3Client amazonS3Client;

    public String upload(InputStream inputStream, String originFileName, Long fileSize) {
        String s3FileName = UUID.randomUUID() + "-" + originFileName;

        ObjectMetadata objMeta = new ObjectMetadata();
        objMeta.setContentLength(fileSize);

        amazonS3Client.putObject(bucket, s3FileName, inputStream, objMeta);

        return amazonS3Client.getUrl(bucket, dir + s3FileName).toString();
    }
}
