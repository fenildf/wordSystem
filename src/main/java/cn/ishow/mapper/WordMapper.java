package cn.ishow.mapper;

import cn.ishow.entity.Word;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
  *  Mapper 鎺ュ彛
 * </p>
 *
 * @author 浠ｈ壋鏍�
 * @since 2017-12-11
 */
public interface WordMapper extends BaseMapper<Word> {
       List<String> listAllChinaNames();
       Word randomWord(int param);
}