package com.ling.restful.crud.handlers;

import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ling.restful.crud.dao.DepartmentDao;
import com.ling.restful.crud.dao.EmployeeDao;
import com.ling.restful.crud.entities.Employee;

@Controller
public class EmployeeHandler {
	
	private static Logger log = LoggerFactory.getLogger(EmployeeHandler.class);
	
	@Autowired
	private EmployeeDao employeeDao;
	@Autowired
	private DepartmentDao departmentDao;
	
	@RequestMapping(value="/emps")
	public String list(Map<String, Object> map) {
		log.info("显示所有员工信息！");
		map.put("employees", employeeDao.getAll());
		return "list";
	}
	
	@RequestMapping(value ="/emp" ,method=RequestMethod.GET)
	public String input(Map<String, Object> map) {
		log.info("input Method called!");
		map.put("departments", departmentDao.getDepartments());
		map.put("employee", new Employee());
		return "input";
	}
	
	@RequestMapping(value="/emp" ,method=RequestMethod.POST)
	public String save(@Valid Employee employee,BindingResult result,Map<String,Object> map) {
		log.info("保存员工信息：employee={}",employee);
		if(result.getFieldErrorCount() > 0){
			log.error("数据绑定出错了！");
			for(FieldError error:result.getFieldErrors()){
				log.error("错误字段名为{},错误信息{}",error.getField(),error.getDefaultMessage());
			}
			//验证出错
			map.put("departments", departmentDao.getDepartments());
			return "input";
		}
		employeeDao.save(employee);
		return "redirect:emps";
	}
	
	@RequestMapping(value = "/emp/{id}",method=RequestMethod.DELETE )
	public String delete(@PathVariable(value="id")Integer id) {
		log.info("删除员工信息！employee id={}",id);
		employeeDao.delete(id);
		return "redirect:../emps";
	}
	
	@RequestMapping(value="/emp/{id}",method=RequestMethod.GET)
	public String input(@PathVariable("id")Integer id,Map<String, Object> map) {
		log.info("进入修改界面...");
		//employee 的名称必须跟页面上回显得名称一致
		map.put("employee", employeeDao.get(id));
		map.put("departments", departmentDao.getDepartments());
		return "input";
	}
	
	@RequestMapping(value="/emp",method=RequestMethod.PUT)
	public String update(Employee employee) {
		log.info("修改员工：employee={}",employee);
		employeeDao.save(employee);
		return "redirect:emps";
	}
	
	@ModelAttribute
	public void getEmployee(@RequestParam(value="id",required=false)Integer id,Map<String, Object> map) {
		log.info("注意，先执行ModelAttribute注解的方法");
		if(id !=null){
			map.put("employee", employeeDao.get(id));
		}
		
	}

}
