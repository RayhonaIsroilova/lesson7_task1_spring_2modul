package uz.pdp.appnewssite.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.pdp.appnewssite.entity.Role;
import uz.pdp.appnewssite.entity.User;
import uz.pdp.appnewssite.entity.enums.PermissionType;
import uz.pdp.appnewssite.repository.RoleRepository;
import uz.pdp.appnewssite.repository.UserRepository;
import uz.pdp.appnewssite.utils.AppConstants;

import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Value("${spring.datasource.initialization-mode}")
    private String initialMode;


    @Override
    public void run(String... args) throws Exception {
       if (initialMode.equals("always")){
           PermissionType[] values = PermissionType.values();
           Role admin = roleRepository.save(new Role(
                   AppConstants.ADMIN,
                   Arrays.asList(values),
                   "system egasi"
           ));
           Role user = roleRepository.save(new Role(
                   AppConstants.USER,
                   Arrays.asList(PermissionType.ADD_COMMENT,
                           PermissionType.EDIT_COMMENT,
                           PermissionType.DELETE_MY_COMMENT),
                   "oddiy user"
           ));
           userRepository.save(new User(
                   "Admin",
                   "admin",
                   passwordEncoder.encode("admin123"),
                   admin,
                   true
           ));
           userRepository.save(new User(
                   "User",
                   "user",
                   passwordEncoder.encode("user123"),
                   user,
                   true
           ));
       }

    }
}
