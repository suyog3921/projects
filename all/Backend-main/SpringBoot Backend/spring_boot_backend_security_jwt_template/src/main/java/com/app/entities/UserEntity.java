package com.app.entities;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "secure_users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "password") // Exclude password from toString
public class UserEntity extends BaseEntity {

    @Column(length = 20, nullable = false)
    private String firstName;

    @Column(length = 20, nullable = false)
    private String lastName;

    @Column(length = 30, unique = true, nullable = false)
    private String email;


    @Column(length = 70,nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(20) default 'ROLE_USER'")
    private UserRole role;

    @PrePersist
    public void prePersist() {
        if (this.role == null) {
            this.role = UserRole.ROLE_USER;
        }
    }
    

}
