package com.example.Teacher.service;
import com.example.Teacher.entity.Teacher;
import com.example.Teacher.exception.EntityNotFoundException;
import com.example.Teacher.exception.TeacherNotFound;
import com.example.Teacher.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.List;
@Service
public class TeacherService {
    @Autowired
    TeacherRepository teacherRepository;
    public Flux<Teacher> getTeachers(){
        return teacherRepository.findAll();
    }
    public Mono<Teacher> getTeacherById(int teacherId){
        return teacherRepository.findById(teacherId).switchIfEmpty(Mono.error(new TeacherNotFound("There is no teacher with id: "+teacherId)));
    }
    public Mono<Teacher> addTeacher(Teacher subjectToAdd){
        return teacherRepository.save(subjectToAdd);
    }
    public Mono<Void> deleteTeacher(int teacherId){
        return teacherRepository.deleteById(teacherId);
    }
    public Mono<Teacher> updateTeacher(int teacherId, Teacher teacher){
        return teacherRepository.findById(teacherId)
                .flatMap(s -> {
                    s.setFirstName(teacher.getFirstName());
                    s.setLastName(teacher.getLastName());
                    s.setSubjectCodes(teacher.getSubjectCodes());
                    return teacherRepository.save(s);
                })
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Teacher not found")));
    }

    public Flux<Teacher> getTeacherByFirstName(String firstName){
        return teacherRepository.getTeacherByFirstName(firstName).switchIfEmpty(Mono.error(new TeacherNotFound("There is no teacher with name like:"+firstName)));
    }
}