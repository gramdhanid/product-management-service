package id.mygilansyah.productmanagement.controller;

import id.mygilansyah.productmanagement.dto.RolesDTO;
import id.mygilansyah.productmanagement.service.RolesServices;
import id.mygilansyah.productmanagement.util.exception.CustomException;
import id.mygilansyah.productmanagement.util.messages.CustomResponse;
import id.mygilansyah.productmanagement.util.messages.CustomResponseGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/roles")
public class RolesController {

    private final RolesServices rolesServices;
    private final CustomResponseGenerator<Object> customResponseGenerator;

    public RolesController(RolesServices rolesServices, CustomResponseGenerator<Object> customResponseGenerator) {
        this.rolesServices = rolesServices;
        this.customResponseGenerator = customResponseGenerator;
    }

    @PostMapping("/add")
    public CustomResponse<Object> addRole(@RequestBody RolesDTO.RequetRolesDTO rolesDTO) throws CustomException {
        try {
            return customResponseGenerator.successResponse(rolesServices.addUpdateRole(rolesDTO), HttpStatus.CREATED.getReasonPhrase());
        } catch (Exception e) {
            return customResponseGenerator.errorResponse(e.getMessage());
        }
    }

    @GetMapping("")
    public CustomResponse<Object> getRolesById(@RequestParam Long id) throws CustomException {
        try {
            return customResponseGenerator.successResponse(rolesServices.getRoleById(id), HttpStatus.OK.getReasonPhrase());
        } catch (Exception e){
            return customResponseGenerator.errorResponse(e.getMessage());
        }
    }

    @GetMapping("/all")
    public CustomResponse<Object> getAllRoles() throws CustomException {
        try {
            return customResponseGenerator.successResponse(rolesServices.getAllRoles(), HttpStatus.OK.getReasonPhrase());
        } catch (Exception e){
            return customResponseGenerator.errorResponse(e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public CustomResponse<Object> deleteRoleById(@RequestParam Long id) throws CustomException {
        try {
            return customResponseGenerator.successResponse(rolesServices.deleteRole(id), HttpStatus.OK.getReasonPhrase());
        } catch (Exception e){
            return customResponseGenerator.errorResponse(e.getMessage());
        }
    }

}
