package cn.ishow.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.ishow.entity.Person;

public abstract class WebUtils {
	
	private static  ServletRequestAttributes getServletRequestAttributes(){
		return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
	}
	
	/**
	 * 获取request对象
	 * @return
	 */
	public static HttpServletRequest getRequest(){
		return getServletRequestAttributes().getRequest();
	}
	
	/**
	 * 获取response对象
	 * @return
	 */
	public static HttpServletResponse getResponse(){
		return getServletRequestAttributes().getResponse();
	}
	
	/**
	 * 将对象按键值对形式保存到request域中
	 * @param key
	 * @param value
	 */
	public static void save2Request(String key,Object value){
		getRequest().setAttribute(key, value);
	}
	
	/**
	 * 将对象按键值对的形式保存Session中
	 * @param key
	 * @param value
	 */
	public static void save2Session(String key,Object value){
		getServletRequestAttributes().setAttribute(key, value, ServletRequestAttributes.SCOPE_SESSION);
	}
	
	/**
	 * 保存用户到session中
	 * @param person
	 */
	public static void savePerson(Person person){
		save2Session("person", person);
	}
	
	/**
	 * 从session中获取用户
	 * @return
	 */
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

}
