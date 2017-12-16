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
	 * ��ȡrequest����
	 * @return
	 */
	public static HttpServletRequest getRequest(){
		return getServletRequestAttributes().getRequest();
	}
	
	/**
	 * ��ȡresponse����
	 * @return
	 */
	public static HttpServletResponse getResponse(){
		return getServletRequestAttributes().getResponse();
	}
	
	/**
	 * �����󰴼�ֵ����ʽ���浽request����
	 * @param key
	 * @param value
	 */
	public static void save2Request(String key,Object value){
		getRequest().setAttribute(key, value);
	}
	
	/**
	 * �����󰴼�ֵ�Ե���ʽ����Session��
	 * @param key
	 * @param value
	 */
	public static void save2Session(String key,Object value){
		getServletRequestAttributes().setAttribute(key, value, ServletRequestAttributes.SCOPE_SESSION);
	}
	
	/**
	 * �����û���session��
	 * @param person
	 */
	public static void savePerson(Person person){
		save2Session("person", person);
	}
	
	/**
	 * ��session�л�ȡ�û�
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
