package cn.ishow.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author 浠ｈ壋鏍�
 * @since 2017-12-11
 */
@TableName("t_word_note")
public class WordNote extends Model<WordNote> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("person_id")
    private Integer personId;
    @TableField("word_id")
    private Integer wordId;
    @TableField("right_number")
    private Integer rightNumber;
    @TableField("show_number")
    private Integer showNumber;
    @TableField("right_rate")
    private Float rightRate;
    @TableField("delete_flag")
    private Integer deleteFlag;
    @TableField(exist = false)
    private String englishName;
    @TableField(exist = false)
    private String chinaName;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPersonId() {
        return personId;
    }


    public String getChinaName() {
        return chinaName;
    }

    public void setChinaName(String chinaName) {
        this.chinaName = chinaName;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public Integer getWordId() {
        return wordId;
    }

    public void setWordId(Integer wordId) {
        this.wordId = wordId;
    }

    public Integer getRightNumber() {
        return rightNumber;
    }

    public void setRightNumber(Integer rightNumber) {
        this.rightNumber = rightNumber;
    }

    public Integer getShowNumber() {
        return showNumber;
    }

    public void setShowNumber(Integer showNumber) {
        this.showNumber = showNumber;
    }

    public Float getRightRate() {
        return rightRate;
    }

    public void setRightRate(Float rightRate) {
        this.rightRate = rightRate;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    @Override
    public String toString() {
        return "WordNote [id=" + id + ", personId=" + personId + ", wordId=" + wordId + ", rightNumber=" + rightNumber
                + ", showNumber=" + showNumber + ", rightRate=" + rightRate + ", deleteFlag=" + deleteFlag
                + ", englishName=" + englishName + ", chinaName=" + chinaName + "]";
    }


}
