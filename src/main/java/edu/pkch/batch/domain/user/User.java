package edu.pkch.batch.domain.user;

import edu.pkch.batch.domain.common.BaseEntity;
import edu.pkch.batch.domain.user.enums.Grade;
import edu.pkch.batch.domain.user.enums.UserStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(
        name = "MEMBER",
        indexes = {}
)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String password;

    @Column
    private String email;

    @Column
    private String principal;

    @Column
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @Column
    @Enumerated(EnumType.STRING)
    private Grade grade;

    @Builder
    public User(String name, String password, String email, String principal, UserStatus userStatus, Grade grade) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.principal = principal;
        this.userStatus = userStatus;
        this.grade = grade;
    }

    public void inactive() {
        this.userStatus = UserStatus.INACTIVE;
    }
}
