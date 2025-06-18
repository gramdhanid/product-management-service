package id.mygilansyah.productmanagement.service;

import id.mygilansyah.productmanagement.dto.UserDTO;
import id.mygilansyah.productmanagement.model.User;
import id.mygilansyah.productmanagement.repository.UserRepository;
import id.mygilansyah.productmanagement.util.exception.CustomException;
import id.mygilansyah.productmanagement.util.exception.ErrorCode;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /*
    * CRUD
    * Create User
    * Read User (By ID, Get All)
    * Update User
    * Delete User
    */

    public UserDTO.ResponseDTO createUser(UserDTO.RegistrationDTO registrationDTO) throws CustomException {
        try {
            Optional<User> optionalUser = userRepository.findByUsernameAndEmailAndDeleted(registrationDTO.getUsername(), registrationDTO.getEmail(), false);
            User user = new User();
            if (optionalUser.isPresent()) {
                throw new CustomException("User isn't available", ErrorCode.GENERIC_FAILURE);
            }
            user.setUsername(registrationDTO.getUsername().toLowerCase(Locale.ROOT));
            user.setPassword(registrationDTO.getPassword());
            user.setFullName(registrationDTO.getFullName().toUpperCase(Locale.ROOT));
            user.setEmail(registrationDTO.getEmail().toLowerCase(Locale.ROOT));
            user.setPhoneNumber(registrationDTO.getPhoneNumber());
            user.setAddress(registrationDTO.getAddress());
            user.setCity(registrationDTO.getCity().toUpperCase(Locale.ROOT));
            user.setCountry(registrationDTO.getCountry().toUpperCase(Locale.ROOT));
            user.setActive(true);
//        user.setRole();
            user.setDeleted(false);
            user.setCreatedBy(user.getCreatedBy());
            user.setCreatedDate(user.getCreatedDate());
            user.setModifiedBy(user.getModifiedBy());
            user.setModifiedDate(user.getModifiedDate());
            return toDTO(userRepository.save(user));
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), ErrorCode.GENERIC_FAILURE);
        }
    }

    public UserDTO.ResponseDTO toDTO(User user) {
        UserDTO.ResponseDTO userDTO = new UserDTO.ResponseDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setFullName(user.getFullName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setAddress(user.getAddress());
        userDTO.setCity(user.getCity());
        userDTO.setCountry(user.getCountry());
        return userDTO;
    }

}
