package com.xxxx.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxxx.server.pojo.Employee;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.pojo.RespPageBean;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author shi
 * @since 2021-04-16
 */
public interface IEmployeeService extends IService<Employee> {


    RespPageBean getEmpByPage(Integer currentPage, Integer size,Employee employee, LocalDate[] beginDataScope);

    RespBean addEmp(Employee employee);


    RespBean getMaxWorkId();

    void exportExcel(HttpServletResponse response);

    void importExcel(MultipartFile file);

    Integer insertEmp(List<Employee> employeeList);
}
