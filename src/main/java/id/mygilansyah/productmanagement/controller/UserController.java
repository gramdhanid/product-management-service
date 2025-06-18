package id.mygilansyah.productmanagement.controller;

import id.mygilansyah.productmanagement.dto.UserDTO;
import id.mygilansyah.productmanagement.service.UserService;
import id.mygilansyah.productmanagement.util.messages.CustomResponse;
import id.mygilansyah.productmanagement.util.messages.CustomResponseGenerator;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    private final UserService userService;
    private final CustomResponseGenerator<Object> customResponseGenerator;

    public UserController(UserService userService, CustomResponseGenerator<Object> customResponseGenerator) {
        this.userService = userService;
        this.customResponseGenerator = customResponseGenerator;
    }

    @PostMapping("add")
    public CustomResponse<Object> addUser(@Valid @RequestBody UserDTO.RegistrationDTO registrationDTO) {
        try {
            return customResponseGenerator.successResponse(userService.createUser(registrationDTO), HttpStatus.CREATED.toString());
        } catch (Exception e){
            return customResponseGenerator.errorResponse(e.getMessage());
        }
    }
}
