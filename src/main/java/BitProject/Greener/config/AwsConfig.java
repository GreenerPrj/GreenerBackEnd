package BitProject.Greener.config;



import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


@Configuration
@Log4j2
public class AwsConfig {


    //    @Value("${cloud.aws.credentials.accessKey}")
    public String accessKey = "AKIASYDPHV6B6BWB3WEF" ; // IAM Access Key
    //    @Value("${cloud.aws.credentials.secretKey}")
    public String secretKey = "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"; // IAM Secret Key
    //    @Value("${cloud.aws.region.static")

    public String region = "ap-northeast-2"; // Bucket Region

    @Bean
    @Primary
    public BasicAWSCredentials awsCredentialsProvider(){
        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessKey, secretKey);
        return basicAWSCredentials;
    }
    @Bean
    public AmazonS3 amazons3(){
        AmazonS3 s3Builder = AmazonS3ClientBuilder.standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentialsProvider()))
                .build();
        return s3Builder;
    }

    @Bean
    public AmazonS3Client amazonS3Client(){
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey,secretKey);
        return (AmazonS3Client)AmazonS3ClientBuilder.standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .build();
    }


}
//    @Bean
//    public AmazonS3Client amazonS3Client() {
//
//        return (AmazonS3Client) AmazonS3ClientBuilder.standard()
//                .withRegion(region)
//                .withCredentials(new AWSStaticCredentialsProvider(awsCredentialsProvider()))
//                .build();
//    }
//}
