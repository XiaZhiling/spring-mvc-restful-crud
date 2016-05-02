package com.ling.restful.crud.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ling.restful.crud.dao.EmployeeDao;
import com.ling.restful.crud.entities.Employee;

@Controller
public class EmployeeConverterHandler {
	
	private static Logger log = LoggerFactory.getLogger(EmployeeConverterHandler.class);
	@Autowired
	private EmployeeDao employeeDao;
	
	@RequestMapping("/testConverter")
	public String testConverter(@RequestParam("employee")Employee employee) {
		log.info("Test Converter:employee={}",employee);
		employeeDao.save(employee);
		return "redirect:emps";
	}
}
