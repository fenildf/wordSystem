package cn.ishow.mapper;

import cn.ishow.entity.WordNote;
import cn.ishow.vo.WordNoteVo;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;

/**
 * <p>
 * Mapper 鎺ュ彛
 * </p>
 *
 * @author 浠ｈ壋鏍�
 * @since 2017-12-11
 */
public interface WordNoteMapper extends BaseMapper<WordNote> {
    List<WordNote> listNote(Page<WordNote> page, WordNoteVo vo);
}