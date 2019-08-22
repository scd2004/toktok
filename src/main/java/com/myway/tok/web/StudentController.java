package com.myway.tok.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.myway.tok.dao.StudentDAO;
import com.myway.tok.model.Student;

@Controller
public class StudentController {
	private static final Logger logger = LoggerFactory.getLogger(StudentController.class);
	
	@Autowired
	private StudentDAO studentDAO;
	
	//방법 1 : return 값은 String으로 만들어 주고 model을 따로 장착 시키는 방법
	//return은 view의 이름을 명시해주고. view에서 사용된 model은 따로 주입하는 방법
	@RequestMapping(value = "/student01.do", method = RequestMethod.GET)
	public String student01(Model model) {
		model.addAttribute("student", new Student());
		return "student/student";
	}
	
	//방법 2 : model and view 를 활용하는 방식
	//방법 1의 단계를 하나로 합치는 방식
	@RequestMapping(value = "/student02.do", method= RequestMethod.GET)
	public ModelAndView student02() {
		studentDAO.select();
		
		return new ModelAndView("student/student", "student", new Student());
	}
	
	//student.jsp 의 요청에서 값을 입력받아서  result.jsp로 전달하는 맵핑
	@RequestMapping(value = "/add/student.do", method = RequestMethod.POST)
	public String addStudent(@ModelAttribute Student student, Model model) {
		model.addAttribute("student" , student);
		//학생 객체를 db에 입력
		studentDAO.create(student);
		//studentDAO.sampleTransaction(student);
		return "redirect:/student_list.do";
	}
	
	//student 조회 요청
	@RequestMapping(value = "/student_list.do", method = RequestMethod.GET)
	public String getStudents(Model model,
			@RequestParam(value = "seq", required = false, defaultValue = "0") Integer seq) {
		if(seq==0) {
			//학생 리스트 조회
			List<Student> students = studentDAO.select();
			model.addAttribute("students", students);
			return "student/list";
		} else {
			//학생 단건 조회
			model.addAttribute("student", studentDAO.select(seq));
			return "student/result";
		}
	}
	
	//student 삭제요청
	@RequestMapping(value = "/student_delete.do", method = RequestMethod.GET)
																		//seq없으면 에러나니까 required 필수!
	public String delete(@RequestParam(value = "seq", required = true) Integer seq) {
		studentDAO.delete(seq);
		//redirect(컨트롤러 요청)로 리스트페이지를 호출해줌
		return "redirect:/student_list.do";
	}
	
	//student 수정페이지 이동 요청
	@RequestMapping(value = "/move_update.do", method = RequestMethod.GET)
	public String moveUpdate(Model model, @RequestParam(value = "seq", required = true) Integer seq) {
		model.addAttribute("student", studentDAO.select(seq));
		return "student/student";
	}
	
	//student 수정 요청
	@RequestMapping(value = "/update/student.do", method = RequestMethod.POST)
	public String update(@ModelAttribute Student student) {
		studentDAO.update(student);
		return "redirect:/student_list.do?seq="+student.getSeq();
	}
	
	
}
