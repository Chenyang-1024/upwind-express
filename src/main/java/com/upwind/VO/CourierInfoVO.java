package com.upwind.VO;

public class CourierInfoVO {

    private Integer id;
    private String name;
    private String phone;
    private String job_no;
    private String gender;
    private String identity_num;
    private String outlet_title;

    public CourierInfoVO() { }

    public CourierInfoVO(Integer id, String name, String phone, String job_no, String gender, String identity_num, String outlet_title) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.job_no = job_no;
        this.gender = gender;
        this.identity_num = identity_num;
        this.outlet_title = outlet_title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getJob_no() {
        return job_no;
    }

    public void setJob_no(String job_no) {
        this.job_no = job_no;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIdentity_num() {
        return identity_num;
    }

    public void setIdentity_num(String identity_num) {
        this.identity_num = identity_num;
    }

    public String getOutlet_title() {
        return outlet_title;
    }

    public void setOutlet_title(String outlet_title) {
        this.outlet_title = outlet_title;
    }

    @Override
    public String toString() {
        return "CourierInfoVO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", job_no='" + job_no + '\'' +
                ", gender='" + gender + '\'' +
                ", identity_num='" + identity_num + '\'' +
                ", outlet_title='" + outlet_title + '\'' +
                '}';
    }
}
