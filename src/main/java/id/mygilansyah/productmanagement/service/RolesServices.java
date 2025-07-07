package id.mygilansyah.productmanagement.service;

import id.mygilansyah.productmanagement.dto.RolesDTO;
import id.mygilansyah.productmanagement.model.Roles;
import id.mygilansyah.productmanagement.repository.RolesRepository;
import id.mygilansyah.productmanagement.util.exception.CustomException;
import id.mygilansyah.productmanagement.util.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@Slf4j
public class RolesServices {

    private final RolesRepository rolesRepository;

    public RolesServices(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    public RolesDTO.ResponseRolesDTO addUpdateRole(RolesDTO.RequetRolesDTO rolesDTO) throws CustomException {
        try {
            log.info("HIT Service ADD / UPDATE Roles");
            Roles roles = new Roles();
            Optional<Roles> rolesOptional = rolesRepository.findTopByRoleCodeOrRoleNameAndDeleted(rolesDTO.getRoleCode().toUpperCase(Locale.ROOT), rolesDTO.getRoleName().toUpperCase(Locale.ROOT), false);
            if (rolesOptional.isPresent()) {
                if (rolesDTO.getId() != null) {
                    roles = rolesOptional.get();
                } else {
                    log.info("Role already exists");
                    throw new CustomException("Role Name / Role Code isn't available", ErrorCode.GENERIC_FAILURE);
                }
            }
            roles.setRoleName(rolesDTO.getRoleName().toUpperCase(Locale.ROOT));
            roles.setRoleCode(rolesDTO.getRoleCode().toUpperCase(Locale.ROOT));
            roles.setDeleted(false);
            rolesRepository.save(roles);
            log.info("Role added/updated");
            return toDTO(roles);
        } catch (Exception e){
            throw new CustomException(e.getMessage(), ErrorCode.GENERIC_FAILURE);
        }
    }

    public RolesDTO.ResponseRolesDTO getRoleById(Long id) throws CustomException {
        log.info("Get role by id: {}", id);
        return toDTO(rolesRepository.findById(id)
                .orElseThrow(() -> new CustomException("Role not found", ErrorCode.GENERIC_FAILURE)));
    }

    public List<RolesDTO.ResponseRolesDTO> getAllRoles() throws CustomException {
        log.info("Get all roles");
        return toListRolesDTO(rolesRepository.findAllByIdAndDeleted(false));
    }

    public Boolean deleteRole(Long id) throws CustomException {
        try {
            log.info("Delete role by id: {}", id);
            Roles roles = rolesRepository.findByIdAndDeleted(id, false)
                    .orElseThrow(() -> new CustomException("Role not found", ErrorCode.GENERIC_FAILURE));
            roles.setDeleted(true);
            rolesRepository.save(roles);
            return true;
        } catch (Exception e){
            throw new CustomException(e.getMessage(), ErrorCode.GENERIC_FAILURE);
        }
    }

    public RolesDTO.ResponseRolesDTO toDTO(Roles role) {
        RolesDTO.ResponseRolesDTO responseRolesDTO = new RolesDTO.ResponseRolesDTO();
        responseRolesDTO.setId(role.getId());
        responseRolesDTO.setRoleName(role.getRoleName());
        responseRolesDTO.setRoleCode(role.getRoleCode());
        return responseRolesDTO;
    }

    public List<RolesDTO.ResponseRolesDTO> toListRolesDTO(List<Roles> rolesList) throws CustomException {
        List<RolesDTO.ResponseRolesDTO> responseRolesDTOList = new ArrayList<>();
        for(Roles roles : rolesList) {
            responseRolesDTOList.add(toDTO(roles));
        }
        return responseRolesDTOList;
    }
}
