package ru.klokov.employeesdatasystem.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "security_role")
@Getter
@Setter
@NoArgsConstructor
public class SecurityRole {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "security_role_sequence")
    @GenericGenerator(
            name="security_role_sequence",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "security_role_sequence")
            }
    )
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "security_role_permissions",
    joinColumns = @JoinColumn(name = "permission_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<SecurityPermission> authorities;

    @ManyToMany
    @JoinTable(name = "security_users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<SecurityUser> users;

    public SecurityRole(String name, Set<SecurityPermission> authorities) {
        this.name = name;
        this.authorities = authorities;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<SecurityPermission> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<SecurityPermission> authorities) {
        this.authorities = authorities;
    }
}
