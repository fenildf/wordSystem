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
@TableName("t_person")
public class Person extends Model<Person> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    //学号，老师就是职工号
    @TableField("login_name")
    private String loginName;
    private String password;
    private String name;
    private String gender;
    @TableField("remeber_count")
    private Long remeberCount;
    /**
     * 0表示学生 1表示老师
     */
    private Integer role;
    @TableField("delete_flag")
    private Integer deleteFlag;

    public static final int TEACHER_ROLE = 1;
    public static final int STUDENT_ROLE = 0;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Long getRemeberCount() {
        return remeberCount;
    }

    public void setRemeberCount(Long remeberCount) {
        this.remeberCount = remeberCount;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
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

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", loginName=" + loginName +
                ", password=" + password +
                ", name=" + name +
                ", gender=" + gender +
                ", remeberCount=" + remeberCount +
                ", role=" + role +
                ", deleteFlag=" + deleteFlag +
                "}";
    }
}
