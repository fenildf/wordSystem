package cn.ishow.web;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.fasterxml.jackson.databind.util.BeanUtil;

import cn.ishow.entity.Person;
import cn.ishow.service.IPersonService;
import cn.ishow.utils.BeanUtils;
import cn.ishow.utils.ServerResponse;
import cn.ishow.utils.WebUtils;
import cn.ishow.vo.StudentVo;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * <p>
 *  测试
 * </p>
 *
 * @author 测试
 * @since 2017-12-11
 */
@Controller
@RequestMapping("/person")
public class PersonController {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private IPersonService personService;
	
	@RequestMapping("/helloword")
	@ResponseBody
	public Object helloword(){
		return ServerResponse.success("hello world");
	}
	
	@RequestMapping("/login")
	@ResponseBody
	public Object login(String loginName,String password){
		if(BeanUtils.strIsNUll(loginName))
			return ServerResponse.fail("请输入账号");
		if(BeanUtils.strIsNUll(password))
			return ServerResponse.fail("请输入密码");
		Wrapper<Person> wrapper = new EntityWrapper<>();
		wrapper.where("login_name = {0} and password = {1}", loginName,password);
		Person person = personService.selectOne(wrapper);
		if(person==null)
			return ServerResponse.fail("你输入的账号或密码有误");
		WebUtils.savePerson(person);
		return ServerResponse.success();
	}
	
	@RequestMapping("/main")
	public String goWhichPage(){
		Person person = WebUtils.getPerson();
	    boolean teacherFlag = WebUtils.isTeacher(person);
	    if(teacherFlag)
	    	return "teacher/student_main";
	    return "student/main";
	}
	
	
	@RequestMapping("/listStudentPage")
	@ResponseBody
	public Object listStudentPage(StudentVo vo){
		Page<Person> page = new Page<>(vo.getPageNum()==null?1:vo.getPageNum(),vo.getPageSize()==null?10:vo.getPageSize());
		Wrapper<Person> wrapper = new EntityWrapper<>();
		wrapper.where("role = 0 and delete_flag = 0 ");
		if(!BeanUtils.strIsNUll(vo.getSearch())){
			wrapper.and(" (name like {0} or login_name like {1} )", vo.getSearch()+"%",vo.getSearch()+"%");
		}
		
		if(!BeanUtils.strIsNUll(vo.getGender())){
			wrapper.and(" gender = {0} ",vo.getGender());
		}
		
		Page<Person> record = personService.selectPage(page,wrapper);
		
		return ServerResponse.successWithData(record).toBootStrapTable();
	}
	
	@RequestMapping("/saveStudent")
	@ResponseBody
	public Object saveStudent(Person person){
		person.setDeleteFlag(0);
		//密码默认和学号一致
		person.setPassword(person.getLoginName());
		person.setRole(Person.STUDENT_ROLE);
		
		//判断学号是否唯一
		Wrapper<Person> wrapper = new EntityWrapper<>();
		wrapper.where(" delete_flag=0 and login_name = {0}", person.getLoginName());
		if(person.getId()!=null){
			wrapper.and(" id!={0} ",person.getId());
		}
		int count = personService.selectCount(wrapper);
		if(count>0){
			return ServerResponse.fail("该学号已经存在");
		}
		if(person.getId()==null){
			personService.insert(person);
			return ServerResponse.success("保存成功");
		}else{
			personService.updateById(person);
			return ServerResponse.success("修改成功");
		}
	}
	
	@RequestMapping("/deleteStudent")
	@ResponseBody
	public Object deleteStudent(Integer id){
	 boolean flag =	personService.deleteById(id);
	 if(flag){
		 return ServerResponse.success("删除成功");
	 }
	 return ServerResponse.fail("删除失败");
	}
	
	
	@RequestMapping("/getPersonById")
	@ResponseBody
	public Object getPersonById(Integer id){
		Person person = personService.selectById(id);
		if(person==null)
			return ServerResponse.fail("未找到数据");
		return ServerResponse.successWithData(person);
	}
	
	
	@RequestMapping("/batchImport")
	@ResponseBody
	public Object batchImport(MultipartFile file){
		String fileName = file.getOriginalFilename();
		logger.info(">>>>>>>>>>>fileName:"+fileName);
		 if(!fileName.matches("^.+\\.(?i)((xls)|(xlsx))$")){
			 return ServerResponse.fail("请选择excel文件");
		 }
		 InputStream is = null;
		 try {
			is = file.getInputStream();
			 boolean is03Excel = fileName.matches("^.+\\.(?i)(xls)$");
			 //1.读取工作簿
	         Workbook workbook = is03Excel?new HSSFWorkbook(is):new XSSFWorkbook(is);
	         //2.读取工作表
	        Sheet sheet = workbook.getSheetAt(0);
	        if(sheet.getPhysicalNumberOfRows() > 2){
	        	List<Person> persons = new LinkedList<>();
	        	//跳过前两行
                for(int k=2;k<sheet.getPhysicalNumberOfRows();k++ )
                {
                	Person person = null;
                    //读取单元格
                    Row row0 = sheet.getRow(k);
                    person = new Person();
                    //学号
                    Cell cell0 = row0.getCell(0);
                   int type =  cell0.getCellType();
                   if(type==HSSFCell.CELL_TYPE_STRING){
                	   person.setLoginName(cell0.getStringCellValue());
                   }else{  
                	  String loginName = new DecimalFormat("#.######").format(cell0.getNumericCellValue());
                	  person.setLoginName(loginName);
                   }
                   
                    //姓名
                    Cell cell1 = row0.getCell(1);
                    person.setName(cell1.getStringCellValue());
                    //性别
                    Cell cell2 = row0.getCell(2);
                    person.setGender(cell2.getStringCellValue());
                    person.setPassword(person.getLoginName());
                    person.setDeleteFlag(0);
                    person.setRole(Person.STUDENT_ROLE);
                    persons.add(person);
                }
                personService.insertBatch(persons);
            }
            workbook.close();
            return ServerResponse.success("批插入成功");
		} catch (IOException e) {
			e.printStackTrace();
			return ServerResponse.fail("批插入失败");
		}finally{
			if(is!=null){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@RequestMapping("/studentMain")
	public String studentMain(){
		return "/teacher/student_main";
	}
	
	@RequestMapping("/wordNoteMain")
	public String wordNoteMain(){
		return "/student/main";
	}
	
	@RequestMapping("/wordDetailMain")
	public String wordDetailMain(){
		return "/student/word_main";
	}
}
