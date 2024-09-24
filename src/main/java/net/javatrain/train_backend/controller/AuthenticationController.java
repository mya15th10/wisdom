package net.javatrain.train_backend.controller;

import net.javatrain.train_backend.dto.LoginRequestDTO;
import net.javatrain.train_backend.dto.JwtResponseDTO;
import net.javatrain.train_backend.security.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider jwtProvider;

    // Xác thực người dùng, trả JWT token
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequestDTO loginRequestDTO) {
        try {
            // Xác thực thông tin người dùng
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequestDTO.getUsername(),
                            loginRequestDTO.getPassword()
                    )
            );

            // Đặt Authentication vào SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Tạo JWT
            String jwt = jwtProvider.generateToken(authentication);

            // Lấy thông tin người dùng
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // Trả về JWT và thông tin người dùng
            return ResponseEntity.ok(new JwtResponseDTO(jwt, userDetails.getUsername(), userDetails.getAuthorities()));

        } catch (BadCredentialsException e) {
            // Trả về lỗi khi thông tin đăng nhập không đúng
            return ResponseEntity.status(401).body("Tên đăng nhập hoặc mật khẩu không đúng");
        }
    }
}
