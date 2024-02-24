package com.example.emplo_test.repo;

import com.example.emplo_test.dto.JobDto;
import com.example.emplo_test.mapper.JobMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class JobDao {
    private final NamedParameterJdbcTemplate named;

    public List<JobDto> findJobsByEmployeeId(Long employeeId) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("id", employeeId);
        return named.query(findJobsByEmployeeIdQuery(), param, new JobMapper.JobDtoMapper());
    }

    public void SaveOrUpdateJob(List<JobDto> jobs) {
        List<JobDto> saveJob = jobs.stream()
                .filter(job -> job.getId() == null)
                .toList();

        List<JobDto> updateJob = jobs.stream()
                .filter(job -> job.getId() != null)
                .toList();

        saveJob.forEach(this::save);
        updateJob.forEach(this::update);
    }

    private void save(JobDto job) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("employeeId", job.getEmployeeId());
        param.addValue("departmentId", job.getDepartmentId());
        param.addValue("startDate", job.getStartDate());
        param.addValue("endDate", job.getEndDate());
        named.update(insertQuery(), param);
    }

    private void update(JobDto job) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("id", job.getId());
        param.addValue("employeeId", job.getEmployeeId());
        param.addValue("departmentId", job.getDepartmentId());
        param.addValue("startDate", job.getStartDate());
        param.addValue("endDate", job.getEndDate());
        named.update(updateQuery(), param);
    }

    private String insertQuery() {
        return "INSERT INTO job (employee_id_fk, department_id_fk, start_date, end_date) VALUES (:employeeId, :departmentId, :startDate, :endDate)";
    }

    private String updateQuery() {
        return "UPDATE job SET employee_id_fk = :employeeId, department_id_fk = :departmentId, start_date = :startDate, end_date = :endDate WHERE id = :id";
    }

    private String findJobsByEmployeeIdQuery() {
        return "select job.id as jobId,\n" +
                " em.id as employeeId,\n" +
                " dep.id as departmentId,\n" +
                " start_date, end_date from job\n" +
                "    join department dep on dep.id = job.department_id_fk\n" +
                "    join employee em on em.id = dep.employee_id_fk\n" +
                "where dep.employee_id_fk = :id";
    }
}
