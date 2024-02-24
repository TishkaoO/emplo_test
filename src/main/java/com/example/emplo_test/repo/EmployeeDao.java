package com.example.emplo_test.repo;

import com.example.emplo_test.dto.EmployeeDto;
import com.example.emplo_test.dto.JobDto;
import com.example.emplo_test.mapper.EmployeeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class EmployeeDao {
    private final NamedParameterJdbcTemplate named;
    private final JobDao jobDao;

    @Transactional
    public void saveOrUpdateEmployee(EmployeeDto employee) {
        if (employee.getId() == null) {
            save(employee);
            jobDao.SaveOrUpdateJob(employee.getJobs());
        } else {
            EmployeeDto employeeDto = findById(employee.getId());
            employeeDto.setName(employeeDto.getName());
            update(employeeDto);
            jobDao.SaveOrUpdateJob(employeeDto.getJobs());
        }
    }


    public EmployeeDto findById(Long employeeId) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("id", employeeId);
        return named.query(findByIdQuery(), param, new EmployeeMapper.EmployeeItemDtoMapper(jobDao)).stream()
                .filter(emp -> emp.getId().equals(emp.getId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("employee is not found %s", employeeId)));
    }


    private String findByIdQuery() {
        return "select * from employee where id = :id";
    }

    private void save(EmployeeDto employee) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("id", employee.getId());
        param.addValue("name", employee.getName());
        named.update(insertQuery(), param);
    }

    private void update(EmployeeDto employee) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("id", employee.getId());
        param.addValue("name", employee.getName());
        named.update(insertQuery(), param);
    }

    private String insertQuery() {
        return " INSERT INTO employee (name) VALUES (:name)";
    }

    private String updateQuery() {
        return "UPDATE employee SET name = :name WHERE id = :id";
    }
}
