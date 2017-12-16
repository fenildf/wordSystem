package cn.ishow.web;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;

import cn.ishow.entity.Person;
import cn.ishow.entity.WordNote;
import cn.ishow.service.IWordNoteService;
import cn.ishow.utils.ServerResponse;
import cn.ishow.utils.WebUtils;
import cn.ishow.vo.WordNoteVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * <p>
 *  鍓嶇鎺у埗鍣�
 * </p>
 *
 * @author 浠ｈ壋鏍�
 * @since 2017-12-11
 */
@Controller
@RequestMapping("/wordNote")
public class WordNoteController {
	@Autowired
	private IWordNoteService wordNoteService;
	
	@RequestMapping("/listWordNote")
	@ResponseBody
	public Object listWordNote(WordNoteVo vo){
		Person person = WebUtils.getPerson();
		Integer id = person.getId();
		vo.setStudentId(id);
		return wordNoteService.listWordNote(vo).toBootStrapTable();
	}
	
	@RequestMapping("/saveNoteWord")
	@ResponseBody
	public Object saveNoteWord(Integer wordId,Integer answer){
		if(wordId==null||answer==null)
			return ServerResponse.success();
		Person person = WebUtils.getPerson();
		Integer id = person.getId();
		//判断是否存在
		Wrapper<WordNote> wrapper = new EntityWrapper<>();
		wrapper.where("word_id = {0} and person_id = {1}", wordId,id);
		WordNote wordNote = wordNoteService.selectOne(wrapper);
		if(wordNote!=null){//表示存在
			wordNote.setShowNumber(wordNote.getShowNumber()+1);
			if(answer==1){//答对了
				wordNote.setRightNumber(wordNote.getRightNumber()==null?1:wordNote.getRightNumber()+1);
			}
			wordNoteService.updateById(wordNote);
		}else{//表示不存在
			wordNote = new WordNote();
			wordNote.setWordId(wordId);
			wordNote.setPersonId(id);
			wordNote.setDeleteFlag(0);
			wordNote.setShowNumber(1);
			if(answer==1){
				wordNote.setRightNumber(1);
			}else{
				wordNote.setRightNumber(0);
			}
			wordNoteService.insert(wordNote);
		}
		return ServerResponse.success();
	}
	
	@RequestMapping("/deleteWordNote")
	@ResponseBody
	Object deleteWordNote(Integer id){
		boolean flag = wordNoteService.deleteById(id);
		if(flag){
			return ServerResponse.success();
		}
		return ServerResponse.fail();
	}
}
