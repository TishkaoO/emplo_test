package com.example.emplo_test.mapper;

import com.example.emplo_test.dto.EmployeeDto;
import com.example.emplo_test.dto.JobDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class JobMapper {

    public static class JobDtoMapper implements RowMapper<JobDto> {

        @Override
        public JobDto mapRow(ResultSet rs, int i) throws SQLException {
            JobDto job = new JobDto();
            job.setId(rs.getLong("jobId"));
            job.setDepartmentId(rs.getLong("employeeId"));
            job.setEmployeeId(rs.getLong("departmentId"));
            job.setStartDate(rs.getDate("start_date"));
            job.setEndDate(rs.getDate("end_date"));
            return job;
        }

    }
}
