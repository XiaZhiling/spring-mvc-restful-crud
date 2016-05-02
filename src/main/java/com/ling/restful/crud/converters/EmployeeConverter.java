package com.ling.restful.crud.converters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.ling.restful.crud.entities.Department;
import com.ling.restful.crud.entities.Employee;

@Component
public class EmployeeConverter implements Converter<String, Employee> {
	
	private static Logger log = LoggerFactory.getLogger(EmployeeConverter.class);

	@Override
	public Employee convert(String source) {
		log.info("EmployeeConverter :+source={}",source);
		if(source != null){
			String[] vals=source.split("-");
			if(vals !=null &&vals.length ==4){
				String lastName = vals[0];
				String email = vals[1];
				Integer gender = Integer.parseInt(vals[2]);
				Department department = new Department();
				department.setId(Integer.parseInt(vals[3]));
				
				return new Employee(null, lastName, email, gender, department);
			}
		}
		return null;
	}

}
