package springcloudms.authservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private RoleNameEnum name;

    public Role(Long id, RoleNameEnum name) {
        this.id = id;
        this.name = name;
    }

    @Setter
    @Getter
    @ManyToOne(optional = false)
    @JsonBackReference
    private Account accountId;

}
