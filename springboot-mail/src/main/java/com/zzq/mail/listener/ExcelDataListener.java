package com.zzq.mail.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.zzq.mail.entity.Report;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : zhiqiang zeng
 * @version : 1.0
 * @date : 2020/8/18 13:50
 */
//ExcelDataListener 不能被spring管理，每次使用需要new
public class ExcelDataListener extends AnalysisEventListener<Report> {


    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 5;

    /**
     * 这个集合用于接收 读取Excel文件得到的数据
     */
    List<Report> list = new ArrayList<Report>();


    /**
     * 这个每一条数据解析都会来调用
     *
     */
    @Override
    public void invoke(Report report, AnalysisContext analysisContext) {
        list.add(report);
        System.out.print(" ============ " + report.getIdCard());
        if (list.size() >= BATCH_COUNT){
            saveData();
            list.clear();
        }
    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
    }

    private void saveData(){
        //入库

    }
}
