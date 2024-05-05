package ru.coursework.gradebook.record.lesson;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.coursework.gradebook.professor.Professor;
import ru.coursework.gradebook.studygroup.StudyGroup;
import ru.coursework.gradebook.subject.Subject;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "lessons")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lessonId;

//    @Column(name = "topic_id", nullable = false)
//    private Long topicId;

    @Column(name = "lesson_date", nullable = false)
    private LocalDate lessonDate;

    @Column(name = "topic", nullable = false)
    private String topic;

    // Ссылки на другие сущности
    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "study_group_id")
    private StudyGroup studygroup;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private Professor professor;
}
