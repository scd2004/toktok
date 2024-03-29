package com.myway.tok.mybatis;

import java.util.List;

import com.myway.tok.model.Student;

public interface MyBatisDao {
	public List<Student> findAllStudent();
	
	public Student findStudent (Integer seq);
	
	public Integer updateStudent(Student student );
	
	public Integer deleteStudent(Integer seq);
	
	public List<Student> dynamicStudent (String id);
}
