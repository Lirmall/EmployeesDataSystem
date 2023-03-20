package ru.klokov.employeesdatasystem.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.userdetails.UserDetails;
import ru.klokov.employeesdatasystem.entities.EmployeeEntity;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "security_users")
@Getter
@Setter
@NoArgsConstructor
public class SecurityUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "security_user_sequence")
    @GenericGenerator(
            name="security_user_sequence",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "security_user_sequence")
            }
    )
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "non_expired")
    private Boolean nonExpired;

    @Column(name = "non_locked")
    private Boolean nonLocked;

    @Column(name = "credentials_non_expired")
    private Boolean credentialsNonExpired;

    @Column(name = "enabled")
    private Boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "security_users_roles",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<SecurityRole> roles;

    public SecurityUser(String username, String password, Set<SecurityRole> roles) {
        this(username, password, true, true, true, true, roles);
    }

    public SecurityUser(String username, String password, Boolean nonExpired, Boolean nonLocked,
                        Boolean credentialsNonExpired, Boolean enabled, Set<SecurityRole> roles) {
        this.username = username;
        this.password = password;
        this.nonExpired = nonExpired;
        this.nonLocked = nonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
        this.roles = roles;
    }

    @Override
    public Set<SecurityPermission> getAuthorities() {
        Set<SecurityPermission> permissions = new HashSet<>();

        for (SecurityRole role: getRoles()) {
            permissions.addAll(role.getAuthorities());
        }

        return permissions;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public Set<SecurityRole> getRoles() {
        return roles;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoles(Set<SecurityRole> roles) {
        this.roles = roles;
    }
}

