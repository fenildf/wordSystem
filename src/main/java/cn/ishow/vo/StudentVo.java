package cn.ishow.vo;

import java.io.Serializable;

public class StudentVo extends BaseVo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -4519811969545622551L;
    //�Ա�
    private String gender;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


}
