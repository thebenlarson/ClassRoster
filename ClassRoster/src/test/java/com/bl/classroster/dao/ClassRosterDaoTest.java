/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bl.classroster.dao;

import com.bl.classroster.dto.Student;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author benth
 */
public class ClassRosterDaoTest {
    
    ClassRosterDao dao = new ClassRosterDaoFileImpl();
    
    public ClassRosterDaoTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() throws Exception {
        List<Student> studentList = dao.getAllStudents();
        for (Student student : studentList){
            dao.removeStudent(student.getStudentId());
        }
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of addStudent method, of class ClassRosterDao.
     */
    @Test
    public void testAddGetStudent() throws Exception {
        Student student = new Student("001");
        student.setFirstName("Joe");
        student.setLastName("Smith");
        student.setCohort("java");
        
        dao.addStudent(student.getStudentId(), student);
        
        Student fromDao = dao.getStudent(student.getStudentId());
        assertEquals(student, fromDao);
    }

    /**
     * Test of getAllStudents method, of class ClassRosterDao.
     */
    @Test
    public void testGetAllStudents() throws Exception {
        Student student = new Student("001");
        student.setFirstName("Joe");
        student.setLastName("Smith");
        student.setCohort("java");
        
        dao.addStudent(student.getStudentId(), student);
        
        Student student2 = new Student("002");
        student2.setFirstName("Jon");
        student2.setLastName("Smit");
        student2.setCohort("java");
        
        dao.addStudent(student2.getStudentId(), student2);
        
        assertEquals(2, dao.getAllStudents().size());
    }

    /**
     * Test of getStudent method, of class ClassRosterDao.
     */
    @Test
    public void testGetStudent() throws Exception {
        
    }

    /**
     * Test of removeStudent method, of class ClassRosterDao.
     */
    @Test
    public void testRemoveStudent() throws Exception {
        Student student = new Student("001");
        student.setFirstName("Joe");
        student.setLastName("Smith");
        student.setCohort("java");
        
        dao.addStudent(student.getStudentId(), student);
        
        Student student2 = new Student("002");
        student2.setFirstName("Jon");
        student2.setLastName("Smit");
        student2.setCohort("java");
        
        dao.addStudent(student2.getStudentId(), student2);
        
        dao.removeStudent(student.getStudentId());
        assertEquals(1, dao.getAllStudents().size());
        assertNull(dao.getStudent(student.getStudentId()));
        
        dao.removeStudent(student2.getStudentId());
        assertEquals(0, dao.getAllStudents().size());
        assertNull(dao.getStudent(student2.getStudentId()));
    }

}
