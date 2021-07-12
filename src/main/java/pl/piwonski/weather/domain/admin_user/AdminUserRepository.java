package pl.piwonski.weather.domain.admin_user;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.piwonski.weather.model.AdminUser;

import java.util.Optional;

public interface AdminUserRepository extends JpaRepository<AdminUser, Long> {
    Optional<AdminUser> findByUsername(String username);

    boolean existsByUsername(String admin);
}