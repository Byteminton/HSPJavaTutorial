package com.chapter26_manhan_tower.service;

import com.chapter26_manhan_tower.dao.EmployeeDAO;
import com.chapter26_manhan_tower.domain.Employee;

/**
 * 通过调用EmployeeDAO完成对 employee 表的各种操作
 */
public class EmployeeService {
    private EmployeeDAO employeeDAO = new EmployeeDAO();

    public Employee getEmployee(String empId, String pwd) {
        return employeeDAO.queryReturnSingleRow("select * from employee where empId = ? and pwd = md5(?)",
                Employee.class, empId, pwd);
    }
}
