package cn.ishow.web;


import cn.ishow.entity.Word;
import cn.ishow.service.IWordService;
import cn.ishow.utils.*;
import cn.ishow.vo.WordVo;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * <p>
 * <p>
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
    public Object listWordPage(WordVo vo) {
        Page<Word> page = new Page<>(vo.getPageNum() == null ? 1 : vo.getPageNum(), vo.getPageSize() == null ? 10 : vo.getPageSize());
        Wrapper<Word> wrapper = new EntityWrapper<>();
        wrapper.where(" delete_flag = 0 ");
        if (!BeanUtils.strIsNUll(vo.getSearch())) {
            wrapper.and(" (english_name like {0} or china_name like {1}) ", vo.getSearch() + "%", vo.getSearch() + "%");
        }

        if (vo.getType() != null) {
            wrapper.and(" type = {0}", vo.getType());
        }

        Page<Word> record = wordService.selectPage(page, wrapper);

        return ServerResponse.successWithData(record).toBootStrapTable();
    }

    @RequestMapping("/wordMain")
    public String wordMain() {
        return "/teacher/word_main";
    }


    @RequestMapping("/saveWord")
    @ResponseBody
    public Object saveWord(Word word) {
        word.setDeleteFlag(0);
        boolean flag = false;
        if (word.getId() == null) {
            flag = wordService.insert(word);
        } else {
            flag = wordService.updateById(word);
        }
        if (flag)
            return ServerResponse.success("添加成功");
        return ServerResponse.fail();
    }

    @RequestMapping("/deleteWord")
    @ResponseBody
    public Object deleteWord(Integer id) {
        boolean flag = wordService.deleteById(id);
        if (flag)
            return ServerResponse.success();
        return ServerResponse.fail();
    }

    @RequestMapping("/findWordById")
    @ResponseBody
    public Object findWordById(Integer id) {
        Word word = wordService.selectById(id);
        if (word == null)
            return ServerResponse.fail();
        return ServerResponse.successWithData(word);
    }

    @RequestMapping("/batchImportWord")
    @ResponseBody
    public Object batchImportWord(MultipartFile file, int wordType) {
        String fileName = file.getOriginalFilename();
        logger.info(">>>>>>>>>>>fileName:" + fileName);
        if (!fileName.matches("^.+\\.(?i)((xls)|(xlsx))$")) {
            return ServerResponse.fail("请选择excel文件");
        }
        try {
            List<Word> words = ExcelUtils.importData(Word.class, file.getOriginalFilename(), file.getInputStream(), new ArrayList<>(Arrays.asList("englishName", "chinaName", "detail")));
            for (Word word : words) {
                word.setDeleteFlag(0);
                word.setType(wordType);
            }
            wordService.insertBatch(words);
            return ServerResponse.success("批量导入成功");
        } catch (Exception e) {
            logger.error(e);
            return ServerResponse.fail("批量导入失败");
        }
    }

    /**
     * 背诵单词
     *
     * @return
     */
    @RequestMapping("/randomWord")
    @ResponseBody
    public Object randomWord() {
        Word word = wordService.randomWord();
        Map<String, Object> map = new HashMap<>();
        map.put("wordId", word.getId());
        map.put("englishName", word.getEnglishName());
        map.put("rightChinaName", word.getChinaName());
        String[] results = WordCache.getInstance().randThree();
        map.put("falseChinaName1", results[0]);
        map.put("falseChinaName2", results[1]);
        map.put("falseChinaName3", results[2]);
        return ServerResponse.successWithData(map);
    }


    @RequestMapping("/exportExcel")
    @ResponseBody
    public void exportExcel() {
        Wrapper<Word> wrapper = new EntityWrapper<>();
        wrapper.where("1=1");
        List<Word> words = wordService.selectList(wrapper);
        logger.info("单词的数量:" + words.size());
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("英文", "englishName");
        map.put("中文", "chinaName");
        map.put("补充", "detail");
        WebUtils.exportExcel(map, words, "单词表");
    }
}
