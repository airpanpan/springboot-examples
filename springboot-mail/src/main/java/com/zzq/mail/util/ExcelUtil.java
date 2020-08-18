package com.zzq.mail.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.zzq.mail.entity.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : zhiqiang zeng
 * @version : 1.0
 * @date : 2020/8/18 14:19
 * @see https://www.cnblogs.com/JWMA/p/12768166.html
 * @see https://www.yuque.com/easyexcel/doc/write#fd54d9a7 官方文档
 */
@Component
public class ExcelUtil {


    @Autowired
    private MailService mailService;

    /**
     * 异步无模型读（默认读取sheet0,从第2行开始读）
     * @param excelListener 监听器，在监听器中可以处理行数据LinkedHashMap，表头数据，异常处理等
     * @param filePath 表头占的行数，从0开始（如果要连表头一起读出来则传0）
     * @return
     */
    public static void asyncRead(String filePath, AnalysisEventListener excelListener, Class cls){
        EasyExcelFactory.read(filePath, cls,excelListener).sheet().doRead();
    }

    /**
     * 异步无模型读（指定sheet和表头占的行数）
     * @param filePath
     * @param excelListener 监听器，在监听器中可以处理行数据LinkedHashMap，表头数据，异常处理等
     * @param sheetNo sheet页号，从0开始
     * @param headRowNum 表头占的行数，从0开始（如果要连表头一起读出来则传0）
     * @return
     */
    public static void asyncRead(String filePath, AnalysisEventListener excelListener, Class cls, Integer sheetNo, Integer headRowNum){
        EasyExcelFactory.read(filePath, cls, excelListener).sheet(sheetNo).headRowNumber(headRowNum).doRead();
    }

    /**
     * 按模板写文件
     * @param filePath
     * @param headClazz 表头模板
     * @param data 数据
     */
    public static void write(String filePath, Class headClazz, String sheetName, List data){
        EasyExcel.write(filePath, headClazz).sheet(sheetName).doWrite(data);
    }


    /**
     * 根据excel模板文件写入文件
     * @param filePath
     * @param templateFileName
     * @param data
     */
    public static void writeTemplate(String filePath, String templateFileName, List data){
        EasyExcel.write(filePath).withTemplate(templateFileName).sheet().doWrite(data);
    }



    public static <T> void writeFillTemplate(String filePath, String templateFileName, T t){
        EasyExcel.write(filePath).withTemplate(templateFileName).sheet().doFill(t);
    }


    public static <T> InputStream writeFillTemplate2(String filePath, String templateFileName, T t) throws IOException {
        ByteArrayOutputStream  outputStream = new ByteArrayOutputStream();
        BufferedOutputStream  bos = new BufferedOutputStream(outputStream);
        //FileOutputStream fos = new FileOutputStream(new File("E:/Demo.xlsx"));
        //BufferedOutputStream  bos = new BufferedOutputStream(fos);
        EasyExcel.write(bos).withTemplate(templateFileName).sheet().doFill(t);

        InputStream is = null;
        try {
        /*    //2.选择流
            is = new ByteArrayInputStream(outputStream.toByteArray());
            //3.操作
            byte[] flush = new byte[1024];//缓冲容器
            int len = -1;//接收长度
            while((len=is.read(flush)) != -1) {
                String str = new String(flush,0,len);
                System.out.print(str);
                outputStream.write(flush, 0, len);
            }*/


            return new ByteArrayInputStream(outputStream.toByteArray());
        }  catch (Exception e) {
            e.printStackTrace();
        }finally {
           /* try {
              *//*  if(is!=null) {
                    //4.释放资源
                    is.close();
                }*//*
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }
        return is;
    }


    public static void main(String[] args) {
        Report report = new Report();
        report.setIdCard("1111");
        report.setName("bbb");
        List<Report> list = new ArrayList();
        list.add(report);
        String sheetName = "";
        write("E:\\xxxx.xlsx", Report.class, sheetName, list);
    }
}
