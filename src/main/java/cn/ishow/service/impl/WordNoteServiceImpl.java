package cn.ishow.service.impl;

import cn.ishow.entity.WordNote;
import cn.ishow.mapper.WordNoteMapper;
import cn.ishow.service.IWordNoteService;
import cn.ishow.utils.ServerResponse;
import cn.ishow.vo.WordNoteVo;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 鏈嶅姟瀹炵幇绫�
 * </p>
 *
 * @author 浠ｈ壋鏍�
 * @since 2017-12-11
 */
@Service
public class WordNoteServiceImpl extends ServiceImpl<WordNoteMapper, WordNote> implements IWordNoteService {

    @Override
    public ServerResponse listWordNote(WordNoteVo vo) {
        Page<WordNote> page = new Page<>(vo.getPageNum() == null ? 1 : vo.getPageNum(), vo.getPageSize() == null ? 10 : vo.getPageSize());
        List<WordNote> records = baseMapper.listNote(page, vo);
        page.setRecords(records);
        return ServerResponse.successWithData(page);
    }

}
