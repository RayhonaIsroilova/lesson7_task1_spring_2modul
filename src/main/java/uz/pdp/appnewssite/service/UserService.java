package uz.pdp.appnewssite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appnewssite.entity.User;
import uz.pdp.appnewssite.payload.ApiResponse;
import uz.pdp.appnewssite.payload.UserDTO;
import uz.pdp.appnewssite.repository.RoleRepository;
import uz.pdp.appnewssite.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    public List<User> getList(){
        return userRepository.findAll();
    }

    public ApiResponse getId(Long id){
        Optional<User> byId = userRepository.findById(id);
        return byId.map(user -> new ApiResponse("Mana ol senga padarka", true, user)).orElseGet(() -> new ApiResponse("This id not found", false));
    }

    public ApiResponse addUser(UserDTO userDTO) {
        Optional<User> byUsername = userRepository.findByUsername(userDTO.getUsername());
        if (byUsername.isPresent())
            return new ApiResponse("This username already exist",false);
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setFullName(userDTO.getFullName());
        user.setPassword(userDTO.getPassword());
        user.setRole(roleRepository.getOne(userDTO.getRoleId()));
        userRepository.save(user);
        return new ApiResponse("Saved success",true);
    }


    public ApiResponse editUser(Long id,UserDTO userDTO) {
        Optional<User> byId = userRepository.findById(id);
        if (!byId.isPresent())
            return new ApiResponse("This id not found",false);
        Optional<User> byUsername = userRepository.findByUsername(userDTO.getUsername());
        if (byUsername.isPresent())
            return new ApiResponse("This username already exist",false);
        User user = byId.get();
        user.setUsername(userDTO.getUsername());
        user.setFullName(userDTO.getFullName());
        user.setPassword(userDTO.getPassword());
        user.setRole(roleRepository.getOne(userDTO.getRoleId()));
        userRepository.save(user);
        return new ApiResponse("edited success",true);
    }


    public ApiResponse deleteUser(Long id){
        Optional<User> byId = userRepository.findById(id);
        if (!byId.isPresent())
            return new ApiResponse("This id not found",false);
        userRepository.deleteById(id);
        return new ApiResponse("Delete success",true);
    }
}
