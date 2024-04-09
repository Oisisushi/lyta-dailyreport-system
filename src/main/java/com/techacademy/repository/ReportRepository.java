package com.techacademy.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techacademy.entity.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, String> {

    // employee_codeが一致する日報を検索してリスト化する
    List<Report> findByEmployeeCode(String employeeCode);

    // employee_codeとreport_dateが一致する日報を検索する
    boolean existsByEmployeeCodeAndReportDate(String employeeCode, LocalDate reportDate);

}
