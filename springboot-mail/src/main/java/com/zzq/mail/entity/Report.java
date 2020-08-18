package com.zzq.mail.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

import java.io.Serializable;

/**
 * @author : zhiqiang zeng
 * @version : 1.0
 * @date : 2020/8/18 13:53
 */
public class Report  implements Serializable {

    @ExcelProperty(value = "姓名" ,index = 0)
    private String name;
    @ExcelProperty(value = "身份证" ,index = 1)
    private String idCard;

    public Report() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }
}
