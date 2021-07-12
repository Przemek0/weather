package pl.piwonski.weather.model;

import javax.persistence.*;

@Table(name = "admin_user")
@Entity
public class AdminUser {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "adminUserSeq")
    @SequenceGenerator(name = "adminUserSeq", sequenceName = "admin_user_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username", nullable = false, unique = true, length = 25)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false, length = 15)
    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}