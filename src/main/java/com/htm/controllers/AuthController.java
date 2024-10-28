package com.htm.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.htm.jwt.JwtUtils;
import com.htm.models.ERole;
import com.htm.models.Role;
import com.htm.models.User;
import com.htm.repository.RoleRepository;
import com.htm.repository.UserRepository;
import com.htm.request.LoginRequest;
import com.htm.request.SignupRequest;
import com.htm.response.MessageResponse;
import com.htm.response.UserInfoResponse;
import com.htm.services.UserDetailsImpl;

import jakarta.validation.Valid;

//for Angular Client (withCredentials)
//@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600, allowCredentials="true")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;

  @PostMapping({"/signin"})
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
 Authentication authentication = this.authenticationManager.authenticate((Authentication)new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
   
      SecurityContextHolder.getContext().setAuthentication(authentication);
     
     UserDetailsImpl userDetails = (UserDetailsImpl)authentication.getPrincipal();
     
      String jwtCookie = this.jwtUtils.generateTokenFromUsername(userDetails.getUsername());

    List<String> roles = (List<String>)userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());

      return ((ResponseEntity.BodyBuilder)ResponseEntity.ok().header("Set-Cookie", new String[] { jwtCookie
       })).body(new UserInfoResponse(userDetails.getId(), userDetails  .getUsername(), userDetails .getEmail(), roles,jwtCookie));
   }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
    }

    // Create new user's account
    User user = new User(signUpRequest.getUsername(),
                         signUpRequest.getEmail(),
                         encoder.encode(signUpRequest.getPassword()));

    Set<String> strRoles = signUpRequest.getRole();
    Set<Role> roles = new HashSet<>();

    if (strRoles == null) {
      Role userRole = roleRepository.findByName(ERole.ROLE_USER)
          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        switch (role) {
        case "admin":
          Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(adminRole);

          break;
        case "mod":
          Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(modRole);

          break;
        default:
          Role userRole = roleRepository.findByName(ERole.ROLE_USER)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(userRole);
        }
      });
    }

    user.setRoles(roles);
    userRepository.save(user);

    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }

  @PostMapping("/signout")
  public ResponseCookie logoutUser() {
    ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
    return ResponseCookie.from("jwt", "")   
            .path("/")                          
            .maxAge(0)                          
            .httpOnly(true)                     
            .build();
  }
}
