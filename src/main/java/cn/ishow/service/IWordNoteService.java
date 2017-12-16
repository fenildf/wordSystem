package cn.ishow.service;

import cn.ishow.entity.WordNote;
import cn.ishow.utils.ServerResponse;
import cn.ishow.vo.WordNoteVo;

import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  鏈嶅姟绫�
 * </p>
 *
 * @author 浠ｈ壋鏍�
 * @since 2017-12-11
 */
public interface IWordNoteService extends IService<WordNote> {
	ServerResponse listWordNote(WordNoteVo vo);
}
