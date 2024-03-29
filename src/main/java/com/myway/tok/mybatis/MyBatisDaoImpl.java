package com.myway.tok.mybatis;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.myway.tok.model.Student;

@Repository
public class MyBatisDaoImpl implements MyBatisDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	private static final String NAME_SPACE = "com.myway.tok.mybatis.MyBatisDao";
	
	@Override
	public List<Student> findAllStudent() {
		// TODO Auto-generated method stub
		return sqlSession.selectList(NAME_SPACE + ".findAllStudent");
	}

	@Override
	public Student findStudent(Integer seq) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(NAME_SPACE+".findStudent", seq);
	}

	@Override
	public Integer updateStudent(Student student) {
		// TODO Auto-generated method stub
		return sqlSession.update(NAME_SPACE + ".updateStudent", student);
	}

	@Override
	public Integer deleteStudent(Integer seq) {
		// TODO Auto-generated method stub
		return sqlSession.delete(NAME_SPACE+".deleteStudent", seq);
	}

	@Override
	public List<Student> dynamicStudent(String value) {
		// TODO Auto-generated method stub
		return sqlSession.selectList(NAME_SPACE+".dynamicStudent", value);
	}
	
}
