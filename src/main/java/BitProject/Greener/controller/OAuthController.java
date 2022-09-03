package BitProject.Greener.controller;


import BitProject.Greener.service.OAuthService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

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
    public void kakaoCallback(@RequestParam String code) {
//       return oAuthService.getKakaoAccessToken(code);
        oAuthService.createKakaoUser(oAuthService.getKakaoAccessToken(code));
//       return oAuthService.createKakaoUser(oAuthService.getKakaoAccessToken(code));
    }


}
