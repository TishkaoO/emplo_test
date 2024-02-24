package com.example.emplo_test.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobDto {
    private Long id;
    private Long employeeId;
    private Long departmentId;
    private Date startDate;
    private Date endDate;
}
