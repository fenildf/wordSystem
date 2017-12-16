package cn.ishow.entity;

import com.baomidou.mybatisplus.enums.IdType;

import java.util.Date;

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
@TableName("t_word")
public class Word extends Model<Word> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("china_name")
    private String chinaName;
    @TableField("english_name")
    private String englishName;
    private String detail;
    @TableField("create_time")
    private Date createTime;
    @TableField("delete_flag")
    private Integer deleteFlag;

    /**
     * 0 四级 1 六级
     */
    private Integer type;

    public static final int FOUR_LEVEL = 0;
    public static final int SIX_LEVEL = 1;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChinaName() {
        return chinaName;
    }

    public void setChinaName(String chinaName) {
        this.chinaName = chinaName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Word [id=" + id + ", chinaName=" + chinaName + ", englishName=" + englishName + ", detail=" + detail
                + ", createTime=" + createTime + ", deleteFlag=" + deleteFlag + ", type=" + type + "]";
    }

}
