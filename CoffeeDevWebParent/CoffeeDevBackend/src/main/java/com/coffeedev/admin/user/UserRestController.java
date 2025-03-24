package com.coffeedev.admin.user;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.coffeedev.admin.FileUploadUtil;
import com.coffeedev.common.dto.UserDTO;
import com.coffeedev.common.entity.Role;
import com.coffeedev.common.entity.User;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private UserService service;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModelMapper modelMapper;

    UserRestController(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> listAllUsers() {
        List<User> users = service.listAll();
        List<UserDTO> userDTOs = users.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDTOs);
    }

    @GetMapping("/page/{pageNum}")
    public ResponseEntity<Page<UserDTO>> listByPage(
            @PathVariable(name = "pageNum") int pageNum,
            @RequestParam(defaultValue = "name") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) String keyword) {

        Page<User> page = service.listByPage(pageNum, sortField, sortDir, keyword);
        Page<UserDTO> pageDTO = page.map(this::convertToDTO);

        return ResponseEntity.ok(pageDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Integer id) {
        try {
            User user = service.get(id);
            return ResponseEntity.ok(convertToDTO(user));
        } catch (UserNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/test-auth")
    public ResponseEntity<?> testAuth(Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Không có Authentication!");
        }

        return ResponseEntity.ok("User: " + authentication.getName() +
                ", Roles: " + authentication.getAuthorities());
    }

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody User user) {
        System.out.println("Password 124: " + user.getPassword());

        // Mã hóa password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Lấy Role từ DB thay vì sử dụng đối tượng Role mới
        Set<Role> roles = new HashSet<>();
        for (Role role : user.getRoles()) {
            Role existingRole = roleRepository.findByName(role.getName());
            if (existingRole != null) {
                roles.add(existingRole);
            }
        }
        user.setRoles(roles);

        service.createUser(user);
        return ResponseEntity.ok("User created successfully");
    }

    @GetMapping("/admins")
    public List<User> getAdmins() {
        return service.getAdmins();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Integer id, @Valid @RequestBody UserDTO userDTO) {
        try {
            User existingUser = service.get(id);
            User updatedUser = convertToEntity(userDTO);
            updatedUser.setId(id);

            // Giữ lại mật khẩu nếu không được cập nhật
            if (updatedUser.getPassword() == null || updatedUser.getPassword().isEmpty()) {
                updatedUser.setPassword(existingUser.getPassword());
            }

            User savedUser = service.save(updatedUser);
            return ResponseEntity.ok(convertToDTO(savedUser));
        } catch (UserNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        try {
            service.delete(id);
            return ResponseEntity.noContent().build();
        } catch (UserNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/enabled/{status}")
    public ResponseEntity<UserDTO> updateUserEnabledStatus(
            @PathVariable("id") Integer id,
            @PathVariable("status") boolean enabled) {
        try {
            service.updateUserEnabledStatus(id, enabled);
            User user = service.get(id);
            return ResponseEntity.ok(convertToDTO(user));
        } catch (UserNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/photo")
    public ResponseEntity<UserDTO> uploadUserPhoto(
            @PathVariable Integer id,
            @RequestParam("image") MultipartFile multipartFile) throws IOException {
        try {
            User user = service.get(id);

            if (!multipartFile.isEmpty()) {
                String fileName = multipartFile.getOriginalFilename();
                user.setPhoto(fileName);
                User savedUser = service.save(user);

                String uploadDir = "user-photos/" + savedUser.getId();
                FileUploadUtil.cleanDir(uploadDir);
                FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

                return ResponseEntity.ok(convertToDTO(savedUser));
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (UserNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    private UserDTO convertToDTO(User user) {
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        userDTO.setRoles(user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet()));
        return userDTO;
    }

    private User convertToEntity(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setPhoto(userDTO.getPhoto());
        user.setEnabled(userDTO.isEnabled());

        // ✅ Fix: Lấy danh sách Role từ DB thay vì nhận trực tiếp từ Request Body
        Set<Role> roles = new HashSet<>();
        for (String roleName : userDTO.getRoles()) {
            Role role = roleRepository.findByName(roleName);
            if (role == null) {
                throw new RuntimeException("Role not found: " + roleName);
            }
            roles.add(role);
        }
        user.setRoles(roles);

        return user;
    }

}