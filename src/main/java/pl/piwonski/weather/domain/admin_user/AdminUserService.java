package pl.piwonski.weather.domain.admin_user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.piwonski.weather.model.AdminUser;

import javax.annotation.PostConstruct;

@Service
public class AdminUserService {
    private final AdminUserRepository adminUserRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminUserService(AdminUserRepository adminUserRepository, PasswordEncoder passwordEncoder) {
        this.adminUserRepository = adminUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void basicAdminUser() {
        if (!adminUserRepository.existsByUsername("admin")) {
            final AdminUser adminUser = new AdminUser();
            adminUser.setUsername("admin");
            adminUser.setPassword(passwordEncoder.encode("password"));
            adminUser.setRole("ROLE_ADMIN");

            adminUserRepository.save(adminUser);
        }
    }
}
