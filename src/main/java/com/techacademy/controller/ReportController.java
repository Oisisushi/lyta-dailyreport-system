package com.techacademy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.techacademy.constants.ErrorKinds;
import com.techacademy.constants.ErrorMessage;
import com.techacademy.entity.Employee;
import com.techacademy.service.ReportService;
import com.techacademy.service.UserDetail;


@Controller
@RequestMapping("reports")
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController (ReportService reportService) {
        this.reportService = reportService;
    }

    // 日報一覧画面
    @GetMapping
    public String list(@AuthenticationPrincipal UserDetail userDetail, Model model) {

        // ログインユーザーの権限情報を取得
        Employee.Role role = userDetail.getEmployee().getRole();

        //　ログインユーザーによって検索内容を変更
        // ユーザーがADMINの場合
        if(Employee.Role.ADMIN.equals(role)) {

            model.addAttribute("listSize", reportService.findAll().size());
            model.addAttribute("reportList", reportService.findAll());

        // ユーザーがADMINではない場合
        } else {
            String employeeCode = userDetail.getEmployee().getCode();
            model.addAttribute("reportList", reportService.findByEmployeeCode(employeeCode));
            model.addAttribute("listSize", reportService.findByEmployeeCode(employeeCode).size());
        }

        return "reports/list";
    }

}
