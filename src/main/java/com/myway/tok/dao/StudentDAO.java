package com.myway.tok.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.myway.tok.mapper.StudentMapper;
import com.myway.tok.model.Student;

@Component
public class StudentDAO {
	private JdbcTemplate jdbcTemplate;
	
	
	@Autowired //<- 스프링 세팅에서 자동으로 찾아주는 기능
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	
	//학생 등록 메소드
	public void create(Student student) {
		String sql = "INSERT INTO Student(id, name, age) values(?, ?, ?)";
		jdbcTemplate.update(sql, student.getId(), student.getName(), student.getAge());
	}
	
	//학생 조회 메소드 -리스트
	public List<Student> select() {
		String sql = "select * from student";
		List<Student> students =  jdbcTemplate.query(sql, new StudentMapper());
		return students;
	}
	
	//학생 단건 조회 메소드 -리스트
		public Student select(Integer seq) {
			String sql = "select * from student where seq = ?";
			Student students =  jdbcTemplate.queryForObject(sql, new Object [] {seq},new StudentMapper());
			return students;
		}
	
	//학생 수정 메소드
	public void update(Student student) {
		String sql = "update student set id=?, name=?, age=? where seq=?";
		jdbcTemplate.update(sql, student.getId(), student.getName(), student.getAge(), student.getSeq());
		
	}
	
	//학생 삭제 메소드
	public Integer delete(Integer seq) {
		String sql = "delete from student where seq = ?";
		return jdbcTemplate.update(sql, seq);
	}
	
	//학생 입력 후 에러가 발생하는 학생 수정 메소드를 호출하는 상황
	@Transactional
	public void sampleTransaction(Student student) {
		this.create(student);
		this.update(student);
	}
	
}
