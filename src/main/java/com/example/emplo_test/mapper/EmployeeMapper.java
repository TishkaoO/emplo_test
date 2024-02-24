package com.example.emplo_test.mapper;

import com.example.emplo_test.dto.EmployeeDto;
import com.example.emplo_test.repo.JobDao;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class EmployeeMapper {

    @RequiredArgsConstructor
    public static class EmployeeItemDtoMapper implements RowMapper<EmployeeDto> {
        private final JobDao jobDao;

        @Override
        public EmployeeDto mapRow(ResultSet rs, int i) throws SQLException {
            EmployeeDto employeeDto = new EmployeeDto();
            employeeDto.setId(rs.getLong("id"));
            employeeDto.setName(rs.getString("name"));
            employeeDto.setJobs(jobDao.findJobsByEmployeeId(employeeDto.getId()));
            return employeeDto;
        }

    }
}
