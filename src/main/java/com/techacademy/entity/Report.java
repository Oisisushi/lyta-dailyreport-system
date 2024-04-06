package com.techacademy.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.SQLRestriction;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="reports")
@SQLRestriction("delete_flg = false")
public class Report {

    // ID
    private String code;

    // 日付
    private LocalDate reportDate;

    // タイトル
    private String title;

    // 内容
    private String content;

    // 社員番号
    private String employeeCode;

    // 削除フラグ
    private boolean deleteFlg;

    // 登録日時
    private LocalDateTime criatedAt;

    // 更新日時
    private LocalDateTime updatedAt;


//    @ManyToOne
//    @JoinColumn(name = "employee_code", referencedColumnName = "code", nullable = false)
//    private Employee employee;

}
