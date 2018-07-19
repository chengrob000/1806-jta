package com.revature.services;

import org.codehaus.jackson.map.ObjectMapper;

import com.revature.beans.Employee;
import com.revature.dao.EmployeeDaoImpl;

public class EmployeeService {
	public static boolean registerEmployee(
			String usern,
			String passw,
			String fname,
			String lname,
			int dirsupid,
			int depid,
			int emptype
		){
	Employee employee = new Employee(usern,passw,fname,lname,dirsupid,depid,emptype);
	EmployeeDaoImpl empDao = new EmployeeDaoImpl();
	if(!(empDao.selectEmployeeByUserN(usern) == null)){
		return false;
	}
	if(empDao.insertEmployeeViaSp(employee)) return true;
	return false;
	}
	
	public static boolean employeeLogin(String usern,String passw) {
		EmployeeDaoImpl empDao = new EmployeeDaoImpl();
		Employee employee;
		if((employee = empDao.selectEmployeeByUserN(usern)) == null) {
			return false;
		}
		if(!employee.getPassW().equals(passw)) {
			return false;
		}
		return true;
	}
	
	public static String getEmpInfoJSON(String usern,String passw){
		EmployeeDaoImpl empDao = new EmployeeDaoImpl();
		Employee employee;
		employee = empDao.selectEmployeeByUserN(usern);
		Employee dirSup;
		dirSup = empDao.selectEmployeeById(employee.getDirSupId());
		employee.setDepId(DepartmentService.department.getDepNameMap().get(employee.getDepName()));
		employee.setEmpType(EmployeeTypeService.emptypes.getEmpTypeMap().get(employee.getEmpTypeName()));
		employee.setDirSupName(dirSup.getFirstN() + " " + dirSup.getLastN());
		ObjectMapper mapper = new ObjectMapper();
		String json = "";
		
		try{
			json = mapper.writeValueAsString(employee);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return json;
	}
}
