package springcloudms.authservice.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Account implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_id_generator")
    @SequenceGenerator(name = "account_id_generator",
            sequenceName = "account_id_seq",
            allocationSize = 1,
            initialValue = 101)
    private Long id;

    @Email
    @NotNull
    @Column(length = 50, nullable = false, unique = true)
    private String email;

    @NotNull
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean active;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "account_roles",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public Account(String email, String password, Boolean active, Set<Role> roles) {
        this.email = email;
        this.password = password;
        this.active = active;
        this.roles = roles;
    }

    public void setRole(Role role) {
        roles.add(role);
    }

    @Override
    public String toString() {
        return "Account{" +
               "id=" + id +
               ", email='" + email + '\'' +
               ", password='" + password + '\'' +
               ", active=" + active +
               ", roles=" + roles +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(getId(), account.getId())
               && Objects.equals(getEmail(), account.getEmail())
               && Objects.equals(getPassword(), account.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getEmail(), getPassword(), getActive(), getRoles());
    }
}
