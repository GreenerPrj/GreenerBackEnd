package BitProject.Greener.service;

import BitProject.Greener.domain.dto.UserDto;
import BitProject.Greener.domain.entity.UserEntity;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;


@Service
@Log4j2
@RequiredArgsConstructor

public class OAuthService {

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    public String clientid;
    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    public String redirecturi;
    @Value("${spring.security.oauth2.client.registration.kakao.authorization-grant-type}")
    public String granttype;
    @Value("${spring.security.oauth2.client.provider.kakao.token-uri}")
    public String tokenuri;
    @Value("${spring.security.oauth2.client.provider.kakao.user-info-uri}")
    public String userinfouri;

    private final UserServiceImple userServiceImple;

    public String getKakaoAccessToken(String code) {
        String access_Token = "";
        String refresh_Token = "";


        try {
            URL url = new URL(tokenuri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //POST 요청을 위해 기본값이 false인 setDoOutput을 true로
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            //POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=" + granttype);
            sb.append("&client_id=" + clientid); // TODO REST_API_KEY 입력
            sb.append("&redirect_uri=" + redirecturi); // TODO 인가코드 받은 redirect_uri 입력
            sb.append("&code=" + code);
            bw.write(sb.toString());
            bw.flush();

            //결과 코드가 200이라면 성공
//            int responseCode = conn.getResponseCode();
//            System.out.println("responseCode : " + responseCode);

            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
//            System.out.println("response body : " + result);

            //Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

            log.info("access_token : " + access_Token);
            log.info("refresh_token : " + refresh_Token);

            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return access_Token;
    }


    @Builder
    public Long createKakaoUser(String token, HttpServletResponse response) {
        String email = "";
        String nickname = "";
        Long id = null;

        //access_token을 이용하여 사용자 정보 조회
        try {
            URL url = new URL(userinfouri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Bearer " + token); //전송할 header 작성, access_token전송

            //결과 코드가 200이라면 성공
//            int responseCode = conn.getResponseCode();
//            System.out.println("responseCode : " + responseCode);

            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
//            System.out.println("response body : " + result);

            //Gson 라이브러리로 JSON파싱
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            id = element.getAsJsonObject().get("id").getAsLong();

            boolean hasEmail = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("has_email").getAsBoolean();

            if (hasEmail) {
                email = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("email").getAsString();
                nickname = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("profile").getAsJsonObject().get("nickname").getAsString();
            }

            log.info("id : " + id);
            log.info("email : " + email);
            log.info("nickname : " + nickname);

            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
//        return nickname;


        UserDto a = UserDto.builder()
                .nickName(nickname)
                .email(email)
                .id(id)
                .build();
        Long checkid = userServiceImple.create(a);
        UserEntity oauthentity = a.toEntity();
        userServiceImple.tokenstore(oauthentity,response);
        if (checkid != null) {
            return checkid;
        } else {
            checkid = Long.valueOf(0);
            return checkid;
        }
    }
}


// kauth.kakao.com/oauth/authorize?client_id=1bc56ac190718591528ae1dfa1592db7&redirect_uri=http://localhost:8080/oauth/kakao/callback&response_type=code
