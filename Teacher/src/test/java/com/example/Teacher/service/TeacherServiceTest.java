package com.example.Teacher.service;
import com.example.Teacher.entity.Teacher;
import com.example.Teacher.exception.EntityNotFoundException;
import com.example.Teacher.exception.TeacherNotFound;
import com.example.Teacher.repository.TeacherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import java.util.List;
import static org.mockito.Mockito.*;
public class TeacherServiceTest {
    @Mock
    private TeacherRepository teacherRepository;
    @InjectMocks
    private TeacherService teacherService;
    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }
   /* @Test
    public void testGetTeachers() {
        Teacher teacher1 = new Teacher(1, "John", "Doe", List.of(1, 2));
        Teacher teacher2 = new Teacher(2, "Jane", "Doe", List.of(3, 4));
        when(teacherRepository.findAll()).thenReturn(Flux.just(teacher1, teacher2));
        Flux<Teacher> result = teacherService.getTeachers();
        assertEquals(2, result.count().block());
    }*/
    @Test
   public void testGetTeachers() {
       Teacher teacher1 = new Teacher(1, "John", "Doe", List.of(1, 2));
       Teacher teacher2 = new Teacher(2, "Jane", "Doe", List.of(3, 4));
       when(teacherRepository.findAll()).thenReturn(Flux.just(teacher1, teacher2));
       Flux<Teacher> result = teacherService.getTeachers();
       StepVerifier.create(result.count())
               .expectNext(2L)
               .expectComplete()
               .verify();
   }
   @Test
   public void testGetTeacherById() {
        int teacherId = 1;
        Teacher teacher = new Teacher(teacherId, "John", "Doe", List.of(1, 2));
        when(teacherRepository.findById(teacherId)).thenReturn(Mono.just(teacher));
        Mono<Teacher> result = teacherService.getTeacherById(teacherId);
        StepVerifier.create(result)
                .expectNext(teacher)
                .expectComplete()
                .verify();
    }
    @Test
    public void testGetTeacherByIdWithInvalidId() {
        int teacherId = 10;
        when(teacherRepository.findById(teacherId)).thenReturn(Mono.empty());

        Mono<Teacher> result = teacherService.getTeacherById(teacherId);

        StepVerifier.create(result)
                .verifyError(TeacherNotFound.class);
    }
    @Test
    public void testAddTeacher() {
        Teacher teacher = new Teacher(1, "John", "Doe", List.of(1, 2));
        when(teacherRepository.save(teacher)).thenReturn(Mono.just(teacher));

        Mono<Teacher> result = teacherService.addTeacher(teacher);

        StepVerifier.create(result)
                .expectNext(teacher)
                .expectComplete()
                .verify();
    }
    @Test
    public void testDeleteTeacher() {
        int teacherId = 1;
        when(teacherRepository.deleteById(teacherId)).thenReturn(Mono.empty());

        Mono<Void> result = teacherService.deleteTeacher(teacherId);

        StepVerifier.create(result)
                .expectComplete()
                .verify();
    }
    @Test
    public void testUpdateTeacher() {
        int teacherId = 1;
        Teacher teacher = new Teacher(teacherId, "John", "Doe", List.of(1, 2));
        when(teacherRepository.findById(teacherId)).thenReturn(Mono.just(teacher));
        when(teacherRepository.save(teacher)).thenReturn(Mono.just(teacher));
        Mono<Teacher> result = teacherService.updateTeacher(teacherId, teacher);
        StepVerifier.create(result).expectNext(teacher).expectComplete().verify();
    }
    @Test
    public void testUpdateTeacherWithInvalidId() {
        int teacherId = 10;
        Teacher teacher = new Teacher(teacherId, "John", "Doe", List.of(1, 2));
        when(teacherRepository.findById(teacherId)).thenReturn(Mono.empty());
        Mono<Teacher> result = teacherService.updateTeacher(teacherId,teacher);
        StepVerifier.create(result).verifyError(EntityNotFoundException.class);
    }
}