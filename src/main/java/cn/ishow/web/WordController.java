package cn.ishow.web;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;

import cn.ishow.entity.Person;
import cn.ishow.entity.Word;
import cn.ishow.service.IWordService;
import cn.ishow.service.impl.WordServiceImpl;
import cn.ishow.utils.BeanUtils;
import cn.ishow.utils.ServerResponse;
import cn.ishow.utils.WordCache;
import cn.ishow.vo.WordVo;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
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
 *  
 * </p>
 *
 * @author yc
 * @since 2017-12-11
 */
@Controller
@RequestMapping("/word")
public class WordController {
	private Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private IWordService wordService;
	
	@RequestMapping("/listWordPage")
	@ResponseBody
	public Object listWordPage(WordVo vo){
		Page<Word> page = new Page<>(vo.getPageNum()==null?1:vo.getPageNum(),vo.getPageSize()==null?10:vo.getPageSize());
		Wrapper<Word> wrapper = new EntityWrapper<>();
		wrapper.where(" delete_flag = 0 ");
		if(!BeanUtils.strIsNUll(vo.getSearch())){
			wrapper.and(" (english_name like {0} or china_name like {1}) ",vo.getSearch()+"%",vo.getSearch()+"%");
		}
		
		if(vo.getType()!=null){
			wrapper.and(" type = {0}",vo.getType());
		}
		
		Page<Word> record = wordService.selectPage(page,wrapper);
		
		return ServerResponse.successWithData(record).toBootStrapTable();
	}
	
	@RequestMapping("/wordMain")
	public String wordMain(){
		return "/teacher/word_main";
	}
	
	
	@RequestMapping("/saveWord")
	@ResponseBody
	public Object saveWord(Word word){
		word.setDeleteFlag(0);
		boolean flag = false;
		if(word.getId()==null){
		  flag = wordService.insert(word);
		}else{
		  flag = wordService.updateById(word);
		}
		if(flag)
			return ServerResponse.success("添加成功");
		return ServerResponse.fail();
	}
	
	@RequestMapping("/deleteWord")
	@ResponseBody
	public Object deleteWord(Integer id){
		boolean flag = wordService.deleteById(id);
		if(flag)
			return ServerResponse.success();
		return ServerResponse.fail();
	}
	
	@RequestMapping("/findWordById")
	@ResponseBody
	public Object findWordById(Integer id){
		Word word = wordService.selectById(id);
		if(word==null)
			return ServerResponse.fail();
		return ServerResponse.successWithData(word);
	}
	
	@RequestMapping("/batchImportWord")
	@ResponseBody
	public Object batchImportWord(MultipartFile file,int wordType){
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
	        	List<Word> words = new LinkedList<>();
	        	//跳过前两行
                for(int k=2;k<sheet.getPhysicalNumberOfRows();k++ )
                {
                	Word word = null;
                    //读取单元格
                    Row row0 = sheet.getRow(k);
                    word = new Word();
                    //英文
                    Cell cell0 = row0.getCell(0);
                   int type =  cell0.getCellType();
                    word.setEnglishName(cell0.getStringCellValue());
                   
                    //中文解释
                    Cell cell1 = row0.getCell(1);
                    word.setChinaName(cell1.getStringCellValue().trim());
                    //补充
                    Cell cell2 = row0.getCell(2);
                    word.setDetail(cell2.getStringCellValue().trim());
                    word.setDeleteFlag(0);
                    word.setType(wordType);
                    words.add(word);
                }
                wordService.insertBatch(words);
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
	
	/**
	 * 背诵单词
	 * @return
	 */
	@RequestMapping("/randomWord")
	@ResponseBody
	public Object randomWord(){
		Word word = wordService.randomWord();
		Map<String,Object> map = new HashMap<>();
		map.put("wordId", word.getId());
		map.put("englishName", word.getEnglishName());
		map.put("rightChinaName", word.getChinaName());
		String[] results = WordCache.getInstance().randThree();
		map.put("falseChinaName1",results[0]);
		map.put("falseChinaName2", results[1]);
		map.put("falseChinaName3", results[2]);
		return ServerResponse.successWithData(map);
	}
}
