package id.mygilansyah.productmanagement.controller;

import id.mygilansyah.productmanagement.dto.AuthDTO;
import id.mygilansyah.productmanagement.service.AuthService;
import id.mygilansyah.productmanagement.util.exception.CustomException;
import id.mygilansyah.productmanagement.util.messages.CustomResponse;
import id.mygilansyah.productmanagement.util.messages.CustomResponseGenerator;
import id.mygilansyah.productmanagement.util.tracking.Network;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/auth")
public class AuthController {

    private final AuthService authService;
    private final CustomResponseGenerator<Object> customResponseGenerator;

    public AuthController(AuthService authService, CustomResponseGenerator<Object> customResponseGenerator) {
        this.authService = authService;
        this.customResponseGenerator = customResponseGenerator;
    }

    @GetMapping("/check")
    public CustomResponse<Object> checkApi (@RequestParam String date){
//        Map<String, String> stringMap = new HashMap<>();
//        stringMap.put("check", "Berhasil terhubung API");
//        return customResponseGenerator.successResponse(stringMap.get("check"), HttpStatus.OK.getReasonPhrase());
        try {
            return customResponseGenerator.successResponse(authService.testAuth(date), HttpStatus.OK.getReasonPhrase());
        } catch (Exception e){
            return customResponseGenerator.errorResponse(e.getMessage());
        }
    }

    @PostMapping("/login")
    public CustomResponse<Object> login(@RequestBody AuthDTO.LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) throws CustomException {
        try {
            String ip = Network.getClientIp(request);
            AuthDTO.LoginResponse loginResponse = authService.login(loginRequest);
            String token = authService.getJWTToken(loginResponse);
            response.addHeader("Authorization", token);
            return customResponseGenerator.successResponse(loginResponse, HttpStatus.OK.getReasonPhrase());
        } catch (Exception e){
            return customResponseGenerator.errorResponse(e.getMessage());
        }
    }

    @PostMapping(value = "/logout")
    public CustomResponse<Object> logout(@RequestHeader("token") String authHeader) throws CustomException {
        try {
            return customResponseGenerator.successResponse(authService.logout(authHeader), HttpStatus.OK.getReasonPhrase());
        } catch (Exception e){
            return customResponseGenerator.errorResponse(e.getMessage());
        }
    }
}
