package common;

import dto.user_dto.UserSignUpDTO;
import dto.user_dto.UserDTO;
import dto.user_dto.UserLoginDTO;
import model.User;

public class Mapper {
    public UserDTO toDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getUserId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setAddress(user.getAddress());
        userDTO.setCreatedOn(user.getCreatedOn());
        userDTO.setUpdatedOn(user.getUpdatedOn());
        return userDTO;
    }

    public User toUser(UserSignUpDTO userDTO) {
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setAddress(userDTO.getAddress());
        user.setRole(userDTO.getRole());
        return user;
    }

    public User toUser(UserLoginDTO userLogin) {
        User user = new User();
        user.setEmail(userLogin.getEmail());
        user.setPassword(userLogin.getPassword());
        return user;
    }
}
