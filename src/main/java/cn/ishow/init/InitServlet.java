package cn.ishow.init;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

import cn.ishow.service.IWordNoteService;
import cn.ishow.service.IWordService;
import cn.ishow.utils.WordCache;

/**
 * ���ظ��ŵ���
 * @author 86322
 *
 */
public class InitServlet extends HttpServlet {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 5217836893340756218L;

	@Override
	public void init() throws ServletException {
	  logger.info("��ʼ���������ݿ�ʼ.....................");
	  IWordService wordService = SpringContextHolder.getBean("wordService", IWordService.class);
	  List<String> results = wordService.listAllChinaNams();
	  logger.info("��ʼ�����ʽ�������"+results.size()+"���ݱ����ص��ڴ���");
	  WordCache.getInstance().putAll(results);
	}
	
	

}
 