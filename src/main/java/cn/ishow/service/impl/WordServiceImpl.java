package cn.ishow.service.impl;

import cn.ishow.entity.Word;
import cn.ishow.mapper.WordMapper;
import cn.ishow.service.IWordService;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

/**
 * <p>
 * </p>
 *
 * @author 尹冲
 * @since 2017-12-11
 */
@Service("wordService")
public class WordServiceImpl extends ServiceImpl<WordMapper, Word> implements IWordService {

    @Override
    public List<String> listAllChinaNams() {
        return baseMapper.listAllChinaNames();
    }

    @Override
    public Word randomWord() {
        Wrapper<Word> wrapper = new EntityWrapper<>();
        wrapper.where("1 =1 ");
        int count = baseMapper.selectCount(wrapper);
        int index = new Random().nextInt(count);
        Word word = baseMapper.randomWord(index);
        return word;
    }

}
