package uz.pdp.appnewssite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appnewssite.entity.Role;
import uz.pdp.appnewssite.payload.ApiResponse;
import uz.pdp.appnewssite.payload.RoleDTO;
import uz.pdp.appnewssite.repository.RoleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    public List<Role> getList(){
        return roleRepository.findAll();
    }

    public ApiResponse getId(Long id){
        Optional<Role> byId = roleRepository.findById(id);
        return byId.map(role -> new ApiResponse("Mana ol ", true, role)).orElseGet(() -> new ApiResponse("This id not found", false));
    }

    public ApiResponse addRole(RoleDTO roleDTO) {
        if (roleRepository.existsByName(roleDTO.getName()))
            return new ApiResponse("This name already exist", false);
        Role role = new Role(
                roleDTO.getName(),
                roleDTO.getPermissionTypes(),
                roleDTO.getDescription()
        );
        roleRepository.save(role);
        return new ApiResponse("Saved successfully", true);

    }

    public ApiResponse editRole(Long id, RoleDTO roleDTO) {
        Optional<Role> byId = roleRepository.findById(id);
        if (!byId.isPresent())
            return new ApiResponse("This id not found", false);
        if (roleRepository.existsByName(roleDTO.getName()))
            return new ApiResponse("This name already exist", false);
        Role role = byId.get();
        role.setName(roleDTO.getName());
        role.setPermissionTypes(roleDTO.getPermissionTypes());
        role.setDescription(roleDTO.getDescription());

        roleRepository.save(role);
        return new ApiResponse("edited successfully", true);

    }

    public ApiResponse deleteRole(Long id){
        Optional<Role> byId = roleRepository.findById(id);
        if (!byId.isPresent())
            return new ApiResponse("This id not found",false);
        roleRepository.deleteById(id);
        return new ApiResponse("Delete successfully",true);
    }
}
