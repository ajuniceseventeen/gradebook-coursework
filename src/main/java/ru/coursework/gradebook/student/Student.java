package ru.coursework.gradebook.student;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.coursework.gradebook.record.mark.Mark;
import ru.coursework.gradebook.studygroup.StudyGroup;
import ru.coursework.gradebook.user.User;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity

@Data

@Table(name = "students")
@AllArgsConstructor
@NoArgsConstructor
//@DiscriminatorValue("STUDENT") в теории возьмет имя класса
@PrimaryKeyJoinColumn(name = "student_id")
public class Student extends User {

    /*      User

    private Long user_id; +

    private String username; +

    private String password;

    private String email; +

    private String roles;
     */

//    private Long student_id; вроде как не нужно повторно

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;

    @ManyToOne
    @JoinColumn(name = "study_group_id")
    private StudyGroup studyGroup;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Mark> marks;
}
