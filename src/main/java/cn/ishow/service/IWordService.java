package cn.ishow.service;

import cn.ishow.entity.Word;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 鏈嶅姟绫�
 * </p>
 *
 * @author 浠ｈ壋鏍�
 * @since 2017-12-11
 */
public interface IWordService extends IService<Word> {
    List<String> listAllChinaNams();

    Word randomWord();
}
