/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bl.classroster.service;

import com.bl.classroster.dao.*;
import com.bl.classroster.dto.Student;
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
public class ClassRosterServiceLayerTest {
    
    private ClassRosterServiceLayer service;
    
    public ClassRosterServiceLayerTest() {
        ClassRosterDao dao = new ClassRosterDaoStubImpl();
        ClassRosterAuditDao auditDao = new ClassRosterAuditDaoStubImpl();
        
        service = new ClassRosterServiceLayerImpl(dao, auditDao);
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of createStudent method, of class ClassRosterServiceLayer.
     */
    @Test
    public void testCreateStudent() throws Exception {
        Student onlyStudent = new Student("0012");
        onlyStudent.setFirstName("joe");
        onlyStudent.setLastName("mama");
        onlyStudent.setCohort("java");
        
        service.createStudent(onlyStudent);
    }
    
    @Test
    public void testCreateStudentDuplicate() throws Exception {
        Student onlyStudent = new Student("001");
        onlyStudent.setFirstName("joe");
        onlyStudent.setLastName("mama");
        onlyStudent.setCohort("java");
        
        try{
            service.createStudent(onlyStudent);
            fail("Expected ClassRosterDuplicatIdException was not thrown");
        } catch(ClassRosterDuplicateIdException e){
            return;
        }
    }
    
    public void testCreateStudentInvalidData() throws Exception {
        Student onlyStudent = new Student("002");
        onlyStudent.setFirstName("");
        onlyStudent.setLastName("mama");
        onlyStudent.setCohort("java");
        
        try{
            service.createStudent(onlyStudent);
            fail("Expected ClassRosterDataValidationException was not thrown");
        } catch(ClassRosterDataValidationException e){
            return;
        }
    }

    /**
     * Test of getAllStudents method, of class ClassRosterServiceLayer.
     */
    @Test
    public void testGetAllStudents() throws Exception {
        assertEquals(1, service.getAllStudents().size());
    }

    /**
     * Test of getStudent method, of class ClassRosterServiceLayer.
     */
    @Test
    public void testGetStudent() throws Exception {
        Student student = service.getStudent("001");
        assertNotNull(student);
        
        student = service.getStudent("081");
        assertNull(student);
    }

    /**
     * Test of removeStudent method, of class ClassRosterServiceLayer.
     */
    @Test
    public void testRemoveStudent() throws Exception {
        Student student = service.removeStudent("001");
        assertNotNull(student);
        
        student = service.removeStudent("081");
        assertNull(student);
    }
    
}
