package com.myway.tok.mybatis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myway.tok.model.Student;

@Service
public class MyBatisService {
	@Autowired
	private MyBatisDao myBatisDao;
	
	public List<Student> finAllStudent() {
		return myBatisDao.findAllStudent();
	}
	
	public Student findStudent(Integer seq) {
		return myBatisDao.findStudent(seq);
	}
	
	public Integer updateStudent (Student student) {
		return myBatisDao.updateStudent(student);
	}
	
	public Integer deleteStudent (Integer seq) {
		return myBatisDao.deleteStudent(seq);
	}
	
	public List<Student> findStudentByID(String id) {
		return myBatisDao.dynamicStudent(id);
	}
	
}
