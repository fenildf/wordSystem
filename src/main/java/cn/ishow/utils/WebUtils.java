package cn.ishow.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.ishow.entity.Person;

import java.util.LinkedHashMap;
import java.util.List;

public abstract class WebUtils {
	
	private static  ServletRequestAttributes getServletRequestAttributes(){
		return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
	}
	

	public static HttpServletRequest getRequest(){
		return getServletRequestAttributes().getRequest();
	}


	public static HttpServletResponse getResponse(){
		return getServletRequestAttributes().getResponse();
	}


	public static void save2Request(String key,Object value){
		getRequest().setAttribute(key, value);
	}


	public static void save2Session(String key,Object value){
		getServletRequestAttributes().setAttribute(key, value, ServletRequestAttributes.SCOPE_SESSION);
	}


	public static void savePerson(Person person){
		save2Session("person", person);
	}


	public static Person getPerson(){
		return (Person) getServletRequestAttributes().getAttribute("person", ServletRequestAttributes.SCOPE_SESSION);
	}
	
	public static boolean isTeacher(Person person){
		if(person==null)
			return false;
		if(person.getRole()==0)
			return false;
		return true;
	}

	/**
	 * 批量导出excel
	 *
	 * @param head
	 * @param beans
	 * @param fileName
	 * @param <T>
	 */
	public static <T> void exportExcel(LinkedHashMap<String, String> head, List<T> beans, String fileName) {
		HttpServletResponse response = getResponse();
		response.reset();
		response.setContentType("application/x-excel");
		response.setCharacterEncoding("UTF-8");
		response.addHeader("Content-Disposition", "attachment;filename=" + System.currentTimeMillis() + ".xls");// excel文件名
		try {
			ExcelUtils.exportData(beans, response.getOutputStream(), head, fileName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

}
