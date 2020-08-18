package com.zzq.mail.util;

import com.zzq.SpringbootMailApplication;
import com.zzq.mail.entity.Report;
import com.zzq.mail.listener.ExcelDataListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootMailApplication.class)
@WebAppConfiguration
public class EasyExcelTest {



    /**
     * 测试发送html邮件
     */
    @Test
    public void write() {
        Report report = new Report();
        report.setIdCard("1111");
        report.setName("bbtb");
        List<Report> list = new ArrayList();
        list.add(report);
        String sheetName = "统计报表";
        ExcelUtil.write("E:\\xxxx.xlsx", Report.class, sheetName, list);
    }

    @Test
    public void writeTemplate() {
        Report report = new Report();
        report.setIdCard("1111");
        report.setName("bbtb");
        List<Report> list = new ArrayList();
        list.add(report);
        ExcelUtil.writeTemplate("E:\\xxxx.xlsx", "E:\\xb.xlsx", list);
    }

    @Test
    public void writeFillTemplate() {
        Report report = new Report();
        report.setIdCard("44148111111");
        report.setName("su san ");
        ExcelUtil.writeFillTemplate("E:\\t1.xlsx", "E:\\xb.xlsx", report);
    }


    @Test
    public void asyncRead() {
        ExcelUtil.asyncRead("E:\\xxxx.xlsx",  new ExcelDataListener(), Report.class);
    }



}
