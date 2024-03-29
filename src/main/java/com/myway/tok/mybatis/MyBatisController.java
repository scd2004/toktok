package com.myway.tok.mybatis;

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

import com.myway.tok.model.Student;

@Controller
public class MyBatisController {
	private static final Logger logger = LoggerFactory.getLogger(MyBatisController.class);

	@Autowired
	private MyBatisService myBatisService;

	@RequestMapping(value = "/mybatis/list.do", method = RequestMethod.GET)
	public String getStudentList(Model model, 
			@RequestParam(value = "id", required = false) String id) {
		List<Student> students = myBatisService.findStudentByID(id);
		model.addAttribute("students", students);
		return "student/list";
	}

	@RequestMapping(value = "/mybatis/student_list.do", method = RequestMethod.GET)
	public String getStudent(Model model,
			@RequestParam(value = "seq", required = false, defaultValue = "0") Integer seq) {
		if(seq == 0) {
			List<Student> students = myBatisService.finAllStudent();
			model.addAttribute("students", students);
			return "student/list";
		} else {
			Student student = myBatisService.findStudent(seq);
			model.addAttribute("student", student);
			return "student/result";
		}
	}

	@RequestMapping(value = "/mybatis/move_update.do", method = RequestMethod.GET)
	public String moveUpdate(Model model, @RequestParam(value = "seq", required = true) Integer seq) {
		model.addAttribute("student", myBatisService.findStudent(seq));
		return "student/student";
	}

	@RequestMapping(value = "/mybatis/update/student.do")
	public String updateStudent(@ModelAttribute Student student) {
		Integer state = myBatisService.updateStudent(student);
		if (state > 0) {
			// 성공했을때 로직
			logger.info("데이터 수정 성공");
		}

		return "redirect:/mybatis/student_list.do?seq=" + student.getSeq();
	}

	@RequestMapping(value = "/mybatis/student_delete.do", method = RequestMethod.GET)
																// seq없으면 에러나니까 required 필수!
	public String deleteStudent(@RequestParam(value = "seq", required = true) Integer seq) {
		myBatisService.deleteStudent(seq);
				//redirect(컨트롤러 요청)로 리스트페이지를 호출해줌
		return "redirect:/mybatis/list.do";
	}

}
