package com.xxxx.server.query;

import com.xxxx.server.pojo.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageQueryParams {
    private Integer currentPage=1;
    private Integer size=10;
    private Employee employee;
    private LocalDate[] beginDataScope;
}
