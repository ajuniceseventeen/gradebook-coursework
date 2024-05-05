package ru.coursework.gradebook.studygroup;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.coursework.gradebook.student.Student;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "study_groups")
public class StudyGroup {

    // getStudy_group_id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long study_group_id;

    @Column(nullable = false)
    private int grade;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "studyGroup")
    private List<Student> students;
}
