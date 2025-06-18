package id.mygilansyah.productmanagement.controller;

import id.mygilansyah.productmanagement.service.UserService;
import id.mygilansyah.productmanagement.util.messages.CustomResponse;
import id.mygilansyah.productmanagement.util.messages.CustomResponseGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/auth")
public class AuthController {

    private final UserService userService;
    private final CustomResponseGenerator<Object> customResponseGenerator;

    public AuthController(UserService userService, CustomResponseGenerator<Object> customResponseGenerator) {
        this.userService = userService;
        this.customResponseGenerator = customResponseGenerator;
    }

    @GetMapping("/check")
    public CustomResponse<Object> checkApi (){
        Map<String, String> stringMap = new HashMap<>();
        stringMap.put("check", "Berhasil terhubung API");
        return customResponseGenerator.successResponse(stringMap.get("check"), HttpStatus.OK.getReasonPhrase());
    }
}
