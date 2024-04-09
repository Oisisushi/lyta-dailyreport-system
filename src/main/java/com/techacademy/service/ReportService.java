package com.techacademy.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techacademy.constants.ErrorKinds;
import com.techacademy.entity.Report;
import com.techacademy.repository.ReportRepository;

@Service
public class ReportService {

    private final ReportRepository reportRepository;

    @Autowired
    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    // 日報一覧全件表示処理
    public List<Report> findAll() {

        return reportRepository.findAll();
    }

    // 日報一覧1名分表示処理
    public List<Report> findByEmployeeCode(String employeeCode){

        return reportRepository.findByEmployeeCode(employeeCode);
    }

    // 日報保存処理
    public ErrorKinds save(Report report) {

        // 日報重複チェック
        ErrorKinds result = reportDateCheck(report);
        if (ErrorKinds.CHECK_OK != result) {
            return result;
        }

        report.setDeleteFlg(false);

        LocalDateTime now = LocalDateTime.now();
        report.setCreatedAt(now);
        report.setUpdatedAt(now);

        reportRepository.save(report);

        return ErrorKinds.SUCCESS;
    }

    // ログイン中の従業員 かつ 入力した日付 の日報データが存在するかをチェックする処理
    private ErrorKinds reportDateCheck(Report report) {

        if(reportRepository.existsByEmployeeCodeAndReportDate(report.getEmployeeCode(), report.getReportDate())) {
            return ErrorKinds.DATECHECK_ERROR;
        }

        return ErrorKinds.CHECK_OK;
    }



}
