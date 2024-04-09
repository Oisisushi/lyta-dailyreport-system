package com.techacademy.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    // 日報一覧1名分の全件表示処理
    public List<Report> findByEmployeeCode(String employeeCode){

        return reportRepository.findByEmployeeCode(employeeCode);
    }

    // 1件を検索
    public Report findById(Integer id) {
        // findByIdで検索(reportテーブルのidカラムはint型なのでtoStringでキャスト)
        Optional<Report> option = reportRepository.findById(id.toString());
        // 取得できなかった場合はnullを返す
        Report report = option.orElse(null);
        return report;
    }

    // 日報保存処理
    @Transactional
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

    // 日報削除処理
    @Transactional
    public void delete(Integer id) {

        Report report = findById(id);
        LocalDateTime now = LocalDateTime.now();
        report.setUpdatedAt(now);
        report.setDeleteFlg(true);

    }

    // ログイン中の従業員 かつ 入力した日付 の日報データが存在するかをチェックする処理
    private ErrorKinds reportDateCheck(Report report) {

        if(reportRepository.existsByEmployeeCodeAndReportDate(report.getEmployeeCode(), report.getReportDate())) {
            return ErrorKinds.DATECHECK_ERROR;
        }

        return ErrorKinds.CHECK_OK;
    }

}
