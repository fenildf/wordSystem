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
 *  ����
 * </p>
 *
 * @author ����
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
			return ServerResponse.fail("�������˺�");
		if(BeanUtils.strIsNUll(password))
			return ServerResponse.fail("����������");
		Wrapper<Person> wrapper = new EntityWrapper<>();
		wrapper.where("login_name = {0} and password = {1}", loginName,password);
		Person person = personService.selectOne(wrapper);
		if(person==null)
			return ServerResponse.fail("��������˺Ż���������");
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
		//����Ĭ�Ϻ�ѧ��һ��
		person.setPassword(person.getLoginName());
		person.setRole(Person.STUDENT_ROLE);
		
		//�ж�ѧ���Ƿ�Ψһ
		Wrapper<Person> wrapper = new EntityWrapper<>();
		wrapper.where(" delete_flag=0 and login_name = {0}", person.getLoginName());
		if(person.getId()!=null){
			wrapper.and(" id!={0} ",person.getId());
		}
		int count = personService.selectCount(wrapper);
		if(count>0){
			return ServerResponse.fail("��ѧ���Ѿ�����");
		}
		if(person.getId()==null){
			personService.insert(person);
			return ServerResponse.success("����ɹ�");
		}else{
			personService.updateById(person);
			return ServerResponse.success("�޸ĳɹ�");
		}
	}
	
	@RequestMapping("/deleteStudent")
	@ResponseBody
	public Object deleteStudent(Integer id){
	 boolean flag =	personService.deleteById(id);
	 if(flag){
		 return ServerResponse.success("ɾ���ɹ�");
	 }
	 return ServerResponse.fail("ɾ��ʧ��");
	}
	
	
	@RequestMapping("/getPersonById")
	@ResponseBody
	public Object getPersonById(Integer id){
		Person person = personService.selectById(id);
		if(person==null)
			return ServerResponse.fail("δ�ҵ�����");
		return ServerResponse.successWithData(person);
	}
	
	
	@RequestMapping("/batchImport")
	@ResponseBody
	public Object batchImport(MultipartFile file){
		String fileName = file.getOriginalFilename();
		logger.info(">>>>>>>>>>>fileName:"+fileName);
		 if(!fileName.matches("^.+\\.(?i)((xls)|(xlsx))$")){
			 return ServerResponse.fail("��ѡ��excel�ļ�");
		 }
		 InputStream is = null;
		 try {
			is = file.getInputStream();
			 boolean is03Excel = fileName.matches("^.+\\.(?i)(xls)$");
			 //1.��ȡ������
	         Workbook workbook = is03Excel?new HSSFWorkbook(is):new XSSFWorkbook(is);
	         //2.��ȡ������
	        Sheet sheet = workbook.getSheetAt(0);
	        if(sheet.getPhysicalNumberOfRows() > 2){
	        	List<Person> persons = new LinkedList<>();
	        	//����ǰ����
                for(int k=2;k<sheet.getPhysicalNumberOfRows();k++ )
                {
                	Person person = null;
                    //��ȡ��Ԫ��
                    Row row0 = sheet.getRow(k);
                    person = new Person();
                    //ѧ��
                    Cell cell0 = row0.getCell(0);
                   int type =  cell0.getCellType();
                   if(type==HSSFCell.CELL_TYPE_STRING){
                	   person.setLoginName(cell0.getStringCellValue());
                   }else{  
                	  String loginName = new DecimalFormat("#.######").format(cell0.getNumericCellValue());
                	  person.setLoginName(loginName);
                   }
                   
                    //����
                    Cell cell1 = row0.getCell(1);
                    person.setName(cell1.getStringCellValue());
                    //�Ա�
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
            return ServerResponse.success("������ɹ�");
		} catch (IOException e) {
			e.printStackTrace();
			return ServerResponse.fail("������ʧ��");
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
