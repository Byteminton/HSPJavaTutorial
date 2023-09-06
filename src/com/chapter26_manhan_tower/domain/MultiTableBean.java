package com.chapter26_manhan_tower.domain;

import java.sql.Timestamp;

/**
 * bill 表中没有菜品名，想要联合查询bill 和 menu 表，实现能够显示菜名的功能
 */
public class MultiTableBean {
    private Integer id;
    private String billId;
    private Integer menuId;
    private Integer nums;
    private Double money;
    private Integer diningTableId;
    private Timestamp billDate;
    private String state;
    private String menuName; // 比bill表多加的一列, 必须跟sql语句查询结果表格中的列名一致
    // 假如随后游补充了新的属性，但多表查询不想显示某些属性，只需要正常写sql语句即可，程序不会报错，只是没有用到的属性会赋默认值
    // 有参构造器也是可以不写的，因为底层反射的时候是使用无参构造器进行赋值的
    // 也可以设置多个MultiTableBean共同继承一个父类，防止这个类属性越来越多

    public MultiTableBean() {
    }

    public MultiTableBean(Integer id, String billId, Integer menuId, Integer nums, Double money, Integer diningTableId, Timestamp billDate, String state, String menuName) {
        this.id = id;
        this.billId = billId;
        this.menuId = menuId;
        this.nums = nums;
        this.money = money;
        this.diningTableId = diningTableId;
        this.billDate = billDate;
        this.state = state;
        this.menuName = menuName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public Integer getNums() {
        return nums;
    }

    public void setNums(Integer nums) {
        this.nums = nums;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Integer getDiningTableId() {
        return diningTableId;
    }

    public void setDiningTableId(Integer diningTableId) {
        this.diningTableId = diningTableId;
    }

    public Timestamp getBillDate() {
        return billDate;
    }

    public void setBillDate(Timestamp billDate) {
        this.billDate = billDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return id + "\t\t" + menuId + "\t\t\t" + nums + "\t\t" + money + "\t" +
                diningTableId + "\t\t" + billDate + "\t\t" + state + "\t\t" + menuName;
    }
}
