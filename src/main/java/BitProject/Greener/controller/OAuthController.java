package BitProject.Greener.controller;


import BitProject.Greener.service.OAuthService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class OAuthController {
        private final OAuthService oAuthService;
    /**
     * 카카오 callback
     * [GET] /oauth/kakao/callback
     */
    @ResponseBody
    @GetMapping("/kakao/callback")
    public void kakaoCallback(@RequestParam String code,HttpServletResponse response) {
//       return oAuthService.getKakaoAccessToken(code);
        oAuthService.createKakaoUser(oAuthService.getKakaoAccessToken(code),response);
//       return oAuthService.createKakaoUser(oAuthService.getKakaoAccessToken(code));
    }

    @GetMapping("/kakao/callback2/{code}")
    public ResponseEntity<?> kakaoCallback2(@PathVariable String code, HttpServletResponse response) {
//       return oAuthService.getKakaoAccessToken(code);
//        oAuthService.createKakaoUser(oAuthService.getKakaoAccessToken(code));
//       return oAuthService.createKakaoUser(oAuthService.getKakaoAccessToken(code));
        return ResponseEntity.ok().body(oAuthService.createKakaoUser(oAuthService.getKakaoAccessToken(code),response));
    }


}
