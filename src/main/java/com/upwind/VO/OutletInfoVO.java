package com.upwind.VO;

public class OutletInfoVO {

    private Integer id;
    private String name;
    private String phone;
    private String title;
    private String province;
    private String city;
    private String district;
    private String detail_addr;

    public OutletInfoVO() { }

    public OutletInfoVO(Integer id, String name, String phone, String title, String province, String city, String district, String detail_addr) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.title = title;
        this.province = province;
        this.city = city;
        this.district = district;
        this.detail_addr = detail_addr;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDetail_addr() {
        return detail_addr;
    }

    public void setDetail_addr(String detail_addr) {
        this.detail_addr = detail_addr;
    }

    @Override
    public String toString() {
        return "OutletInfoVO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", title='" + title + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", detail_addr='" + detail_addr + '\'' +
                '}';
    }
}
