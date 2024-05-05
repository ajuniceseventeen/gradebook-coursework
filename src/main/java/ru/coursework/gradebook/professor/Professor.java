package ru.coursework.gradebook.professor;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.coursework.gradebook.user.User;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "professors")
//@DiscriminatorValue("PROFESSOR")
@PrimaryKeyJoinColumn(name = "professor_id")
public class Professor extends User {
    /*      User

    private Long user_id; +

    private String username; +

    private String password;

    private String email; +

    private String roles;
     */

//    private Long student_id; вроде как не нужно повторно

    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String position;


}
