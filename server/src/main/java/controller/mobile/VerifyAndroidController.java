package controller.mobile;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import json.SuccessJson;
import util.VerifySource;

@Controller
@RequestMapping("/mobile/verify")
public class VerifyAndroidController {
    
    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody SuccessJson post(int userId, String verify) {
        
//        System.out.println("/mobile/verify");
        
        SuccessJson successJson = new SuccessJson();
        if (userId == 0 || verify == null || "".equals(verify)) {
            return successJson;
        }
        if (VerifySource.getMD5(userId).equals(verify)) {
            successJson.setIsSuccess(true);
        }
        
        return successJson;
    }

}