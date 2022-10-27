package boot;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuyuxiang
 * @version 1.0.0
 * @package boot
 * @date 2022/10/26 19:22
 */
@RestController
public class MyController {

    @PostMapping("/login")
    //处理登录逻辑的请求
    private String login(String username, String password){
        return "666";
    }
}
