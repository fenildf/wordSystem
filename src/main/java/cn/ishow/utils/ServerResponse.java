package cn.ishow.utils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;

public class ServerResponse implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7531455323973349723L;
	private int code;
	private String msg;
	private Object data;
	
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public ServerResponse() {
		super();
	}
	public ServerResponse(int code, String msg, Object data) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = data;
	}
	
	public static ServerResponse fail(String msg){
		return new ServerResponse(500, msg, null);
	}
	
	public static ServerResponse fail(){
		return fail("操作失败");
	}
	
	public static ServerResponse success(String msg){
		return new ServerResponse(200, msg, null);
	}
	
	public static ServerResponse success(){
		return new ServerResponse(200,"操作成功",null);
	}
	
	public static ServerResponse successWithData(Object data){
		return new ServerResponse(200, "操作成功", data);
	}
	@Override
	public String toString() {
		return "ServerResponse [code=" + code + ", msg=" + msg + ", data=" + data + "]";
	}

	
	public Map<String,Object> toBootStrapTable(){
		Page page =(Page)data;
		Map<String,Object> map = new HashMap<>();
		map.put("total", page.getTotal());
		map.put("page", page.getPages());
		map.put("rows", page.getRecords());
		return map;
	}
	
	
}
