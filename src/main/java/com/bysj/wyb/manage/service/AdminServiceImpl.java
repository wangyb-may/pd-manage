package com.bysj.wyb.manage.service;

import com.bysj.wyb.manage.entity.Admin;
import com.bysj.wyb.manage.entity.Student;
import com.bysj.wyb.manage.entity.Teacher;
import com.bysj.wyb.manage.mapper.AdminMapper;
import com.bysj.wyb.manage.result.HandleResult;
import com.bysj.wyb.manage.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author admin
 */
@Service
@Slf4j
public class AdminServiceImpl implements AdminService {

    @Resource
    AdminMapper adminMapper;

    HandleResult hr=new HandleResult();

    @Override
    public Result logIn(Admin admin) {
        try {
            Admin exsistAdmin=new Admin();
            exsistAdmin=adminMapper.logIn(admin);
            if(admin.getPassword().equals(exsistAdmin.getPassword())){
                return hr.outResultWithData("0","登陆成功",exsistAdmin);
            }else {
                return hr.outResultWithoutData("1","登录名或密码不正确");
            }
        }catch (Exception e){
            log.error(e.getMessage());
            return hr.outResultWithoutData("1","账号不存在!");
        }
    }

    @Override
    public Result findStudentListByClass(String classNumber) {
        return hr.outResultWithData("0","查询成功",adminMapper.findStudentListByClass(classNumber));
    }

    @Override
    public Result findTeacherList() {
        return hr.outResultWithData("0","查询成功",adminMapper.findTeacherList());
    }

    @Override
    public Result delUser(String table, String uid) {
        try{
            if(1==adminMapper.delUser(table,uid)){
                return hr.outResultWithoutData("0","该账号已经停用");
            }else{
                return hr.outResultWithoutData("0","未知错误");
            }
        }catch (Exception e){
            log.error(e.getMessage());
            return hr.outResultWithoutData("0","未知错误");
        }
    }

    @Override
    public Result findStudentListByKey(String key) {

            List<Student> studentList=adminMapper.findStudentByKey(key);
            if(!studentList.isEmpty()){
                return hr.outResultWithData("0","查找成功！",studentList);
            }else{
                return hr.outResultWithoutData("1","没有相关账号信息！");
            }

    }

    @Override
    public XSSFWorkbook createVoidExcelModel() {
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("sheet1");

        CellStyle style=wb.createCellStyle();
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.CENTER);
        Font font=wb.createFont();
        font.setColor(IndexedColors.RED.index);
        style.setFont(font);

        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("学号");
        sheet.setColumnWidth(0, 256*15);
        row.createCell(1).setCellValue("姓名");
        sheet.setColumnWidth(1, 256*20);
        row.createCell(2).setCellValue("性别");
        sheet.setColumnWidth(2, 256*10);
        row.createCell(3).setCellValue("班级");
        sheet.setColumnWidth(3, 256*15);


        for(int i=0;i<4;i++){
            row.getCell(i).setCellStyle(style);
        }
        return wb;
    }

    @Override
    public Result createNewStudent(MultipartFile file,boolean low2007) {
        try {
            Workbook wb;
            //根据路径获取这个操作excel的实例
            if(low2007){
                wb = new HSSFWorkbook(file.getInputStream());
            }else {
                wb = new XSSFWorkbook(file.getInputStream());
            }
            //根据页面index 获取sheet页
            Sheet sheet = wb.getSheetAt(0);
            //实体类集合
            List<Student> StudentList = new ArrayList<>();
            Row row = null;
            //循环sesheet页中数据从第二行开始，第一行是标题
            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                System.out.println(i);
                //获取每一行数据
                row = sheet.getRow(i);
                Student student = new Student();
                //设置登录账号以及密码
                student.setNickName(getCellStringValue(row.getCell(0)));
                student.setPassword(getCellStringValue(row.getCell(0)));
                //姓名
                student.setName(getCellStringValue(row.getCell(1)));
                //性别
                student.setSex(getCellStringValue(row.getCell(2)));
                //班级
                student.setClassNumber(getCellStringValue(row.getCell(3)));
                //uid
                String strNow=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()).replaceAll("-","").replaceAll(":","").replaceAll(" ","");
                student.setUid(strNow);
                //创建新的账号信息
                StudentList.add(student);
            }

            for(Student s :StudentList) {
                adminMapper.createNewStudent(s);
            }

            return hr.outResultWithoutData("0","添加成功！");

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return hr.outResultWithoutData("1","添加失败,请检查是否按照模板上传");
        }

    }

    @Override
    public Result createNewTeacher(Teacher teacher) {
        try{
            String val = "";
            Random random = new Random();
            //length为几位密码
            for(int i = 0; i < 6; i++) {
                String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
                //输出字母还是数字
                if( "char".equalsIgnoreCase(charOrNum) ) {
                    //输出是大写字母还是小写字母
                    int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                    val += (char)(random.nextInt(26) + temp);
                } else if( "num".equalsIgnoreCase(charOrNum) ) {
                    val += String.valueOf(random.nextInt(10));
                }
            }
            teacher.setUid(val);
            adminMapper.createNewTeacher(teacher);
            return hr.outResultWithData("0","添加成功！",teacher);
        }catch(Exception e){
            log.error(e.getMessage());
            return hr.outResultWithoutData("1","生成失败");
        }

    }

    public String getCellStringValue(Cell cell) {
        String cellValue = "";
        switch (cell.getCellType()) {
            //字符串类型
            case STRING:
                cellValue = cell.getStringCellValue();
                if(cellValue.trim().equals("")||cellValue.trim().length()<=0) {
                    cellValue = " ";
                }
                break;
            //数值类型
            case NUMERIC:
                cell.setCellType(CellType.STRING);
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            //公式
            case FORMULA:
                cell.setCellType(CellType.NUMERIC);
                cellValue = String.valueOf((int)cell.getNumericCellValue());
                break;
            case BLANK:
                cellValue=" ";
                break;
            case BOOLEAN:
                break;
            case ERROR:
                break;
            default:
                break;
        }
        return cellValue;
    }


}
