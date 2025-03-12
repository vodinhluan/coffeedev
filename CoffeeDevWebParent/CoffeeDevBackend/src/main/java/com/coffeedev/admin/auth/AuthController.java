package com.coffeedev.admin.auth;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.coffeedev.admin.security.CoffeeDevUserDetails;
import com.coffeedev.admin.security.jwt.JwtTokenUtil;
import com.coffeedev.common.dto.AuthRequest;
import com.coffeedev.common.dto.AuthResponse;
import com.coffeedev.common.dto.UserDTO;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody AuthRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenUtil.generateJwtToken(authentication);

        CoffeeDevUserDetails userDetails = (CoffeeDevUserDetails) authentication.getPrincipal();
        UserDTO userDTO = convertToDTO(userDetails);

        return ResponseEntity.ok(new AuthResponse(jwt, userDTO));
    }

    private UserDTO convertToDTO(CoffeeDevUserDetails userDetails) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userDetails.getId());
        userDTO.setEmail(userDetails.getUsername());
        userDTO.setName(userDetails.getFullname());
        userDTO.setEnabled(userDetails.isEnabled());

        userDetails.getAuthorities().forEach(authority -> {
            userDTO.getRoles().add(authority.getAuthority());
        });

        return userDTO;
    }
}