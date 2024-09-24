package net.javatrain.train_backend.controller;

import net.javatrain.train_backend.entity.Account;
import net.javatrain.train_backend.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Đăng ký tài khoản mới
    @PostMapping("/register")
    public ResponseEntity<?> registerAccount(@RequestBody Account account) {
        // Kiểm tra xem username đã tồn tại chưa
        if (accountRepository.findByUsername(account.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username đã tồn tại!");
        }

        // Mã hóa mật khẩu trước khi lưu
        account.setPassword(passwordEncoder.encode(account.getPassword()));

        // Lưu tài khoản vào cơ sở dữ liệu
        accountRepository.save(account);

        return ResponseEntity.ok("Tài khoản đã được tạo thành công!");
    }

    // Lấy danh sách tất cả các tài khoản (Chỉ ADMIN và MANAGER)
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @GetMapping("/all")
    public ResponseEntity<List<Account>> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return ResponseEntity.ok(accounts);
    }

    // Xóa tài khoản theo ID(ADMIN)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable int id) {
        Optional<Account> account = accountRepository.findById(id);
        if (!account.isPresent()) {
            return ResponseEntity.badRequest().body("Tài khoản không tồn tại!");
        }
        accountRepository.deleteById(id);
        return ResponseEntity.ok("Tài khoản đã được xóa!");
    }

    // Cập nhật tài khoản (ADMIN, MANAGER)
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateAccount(@PathVariable int id, @RequestBody Account updatedAccount) {
        Optional<Account> account = accountRepository.findById(id);

        if (!account.isPresent()) {
            return ResponseEntity.badRequest().body("Tài khoản không tồn tại!");
        }

        Account existingAccount = account.get();
        existingAccount.setUsername(updatedAccount.getUsername());
        existingAccount.setRole(updatedAccount.getRole());

        // Kiểm tra nếu mật khẩu được cập nhật
        if (updatedAccount.getPassword() != null && !updatedAccount.getPassword().isEmpty()) {
            existingAccount.setPassword(passwordEncoder.encode(updatedAccount.getPassword()));
        }

        accountRepository.save(existingAccount);

        return ResponseEntity.ok("Tài khoản đã được cập nhật thành công!");
    }
}
