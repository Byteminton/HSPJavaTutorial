package com.chapter26_manhan_tower.view;

import com.chapter26_manhan_tower.domain.*;
import com.chapter26_manhan_tower.service.BillService;
import com.chapter26_manhan_tower.service.DiningTableService;
import com.chapter26_manhan_tower.service.EmployeeService;
import com.chapter26_manhan_tower.service.MenuService;
import com.chapter26_manhan_tower.utils.Utility;

import java.util.List;

public class MHTView {
    private boolean loop = true;
    private String key;
    private EmployeeService employeeService = new EmployeeService();
    private DiningTableService diningTableService = new DiningTableService();
    private MenuService menuService = new MenuService();
    private BillService billService = new BillService();

    public void displayTableState() {
        System.out.println("\n餐桌编号\t\t餐桌状态");
        List<DiningTable> diningTables = diningTableService.tableState();
        for (DiningTable diningTable : diningTables) {
            System.out.println(diningTable);
        }
        System.out.println("==========显示完毕==========");
    }

    public void bookTable() {
        System.out.println("==========预订餐桌==========");
        System.out.print("请选择要预订餐桌的编号(-1退出):");
        int id = Utility.readInt();
        if (id == -1) {
            System.out.println("==========退出预订==========");
            return;
        }
        DiningTable diningTable = diningTableService.getDiningTable(id);
        if (diningTable == null) {
            System.out.println("该编号的餐桌不存在");
            System.out.println("==========预订失败==========");
            return;
        }
        if (!"空".equals(diningTable.getState())) {
            System.out.println("餐桌已被预订");
            System.out.println("==========预订失败==========");
            return;
        }
        char choice = Utility.readConfirmSelection();
        if (choice == 'Y') {
            System.out.print("预订人名字:");
            String name = Utility.readString(50);
            System.out.print("预订人电话:");
            String tel = Utility.readString(50);
            if (diningTableService.book(id, name, tel)) {
                System.out.println("==========预订成功==========");
            } else {
                System.out.println("==========预订失败==========");
            }
        } else {
            System.out.println("==========退出预订==========");
        }
    }

    public void displayMenu() {
        System.out.println("\n菜品编号\t\t菜品名\t\t类别\t\t价格");
        List<Menu> menus = menuService.showMenu();
        for (Menu menu : menus) {
            System.out.println(menu);
        }
        System.out.println("==========显示完毕==========");
    }

    public void order() {
        System.out.println("==========开始点餐==========");
        while (true) {
            System.out.print("请选择座位号(-1退出):");
            int tableId = Utility.readInt();
            if (tableId == -1) {
                System.out.println("==========退出预订==========");
                return;
            }
            DiningTable diningTable = diningTableService.getDiningTable(tableId);
            if (diningTable == null) {
                System.out.println("该编号的餐桌不存在");
                continue;
            }
            while (true) {
                System.out.print("请选择菜品编号(-1退出):");
                int menuId = Utility.readInt();
                if (menuId == -1) {
                    System.out.println("==========点餐结束==========");
                    return;
                }
                Menu menu = menuService.getMenu(menuId);
                if (menu == null) {
                    System.out.println("该编号的菜品不存在,请重新添加");
                    continue;
                }
                System.out.print("请选择菜品数量(-1退出):");
                int nums = Utility.readInt();
                if (nums <= 0) {
                    System.out.println("==========错误输入，重新点餐==========");
                    continue;
                }
                char choice = Utility.readConfirmSelection();
                if (choice == 'Y') {
                    if (billService.addBill(menuId, nums, tableId)) {
                        System.out.println("==========点餐成功==========");
                    } else {
                        System.out.println("==========点餐失败==========");
                    }
                } else {
                    System.out.println("==========放弃添加==========");
                }
            }
        }
    }

    public void displayBill() {
        System.out.println("\n编号\t\t菜品号\t\t数量\t\t金额\t\t桌号\t\t日期\t\t\t\t\t\t\t状态");
        List<Bill> bills = billService.showBill();
        for (Bill bill : bills) {
            System.out.println(bill);
        }
        System.out.println("==========显示完毕==========");
    }
    public void displayMultiTableBill() {
        System.out.println("\n编号\t\t菜品号\t\t数量\t\t金额\t\t桌号\t\t日期\t\t\t\t\t\t\t状态\t\t菜品名");
        List<MultiTableBean> multiTableBills = billService.showBillPro();
        for (MultiTableBean multiTableBill : multiTableBills) {
            System.out.println(multiTableBill);
        }
        System.out.println("==========显示完毕==========");
    }

    public void pay() {
        System.out.println("==========结账服务==========");
        System.out.print("请选择要结账的餐桌编号(-1退出):");
        int diningTableId = Utility.readInt();
        if (diningTableId == -1) {
            return;
        }
        DiningTable diningTable = diningTableService.getDiningTable(diningTableId);
        if (diningTable == null) {
            System.out.println("该编号的餐桌不存在");
            return;
        }
        List<Bill> bills = billService.checkBill(diningTableId);
        if (bills.size() == 0) {
            System.out.println("该餐桌不存在未结账的账单");
            return;
        }
        Double money = 0.0;
        for (Bill bill : bills) {
            money += bill.getMoney();
        }
        System.out.print("结账方式(现金/支付宝/微信)(回车退出):");
        String mode = Utility.readString(20, ""); // 如果直接回车，返回的是默认值""
        if ("".equals(mode)) {
            return;
        }
        char confirm = Utility.readConfirmSelection();
        if (confirm == 'Y') {
            if(billService.payBill(diningTableId, mode)) {
                System.out.println("收款¥" + money);
                System.out.println("==========结账成功==========");
            } else {
                System.out.println("==========结账失败==========");
            }
        } else {
            System.out.println("==========取消结账==========");
        }


    }
    public void mainMenu() {
        while (loop) {
            System.out.println("==========满汉楼==========");
            System.out.println("\t 1 登陆满汉楼");
            System.out.println("\t 2 退出系统");
            System.out.print("请输入你的选择:");
            key = Utility.readString(1);
            switch (key) {
                case "1":
                    System.out.print("请输入员工号:");
                    String empId = Utility.readString(50);
                    System.out.print("请输入密码:");
                    String pwd = Utility.readString(50);
                    Employee employee = employeeService.getEmployee(empId, pwd);
                    if (employee != null) {
                        System.out.println("==========登陆成功[" + employee.getName() + "]==========\n");
                        while (loop) {
                            System.out.println("\n==========满汉楼二级菜单==========");
                            System.out.println("\t 1 显示餐桌状态");
                            System.out.println("\t 2 预订餐桌");
                            System.out.println("\t 3 显示所有菜品");
                            System.out.println("\t 4 点餐");
                            System.out.println("\t 5 查看账单");
                            System.out.println("\t 6 结账");
                            System.out.println("\t 7 退出");
                            System.out.print("请输入你的选择:");
                            key = Utility.readString(1);
                            switch (key) {
                                case "1":
                                    displayTableState();
                                    break;
                                case "2":
                                    bookTable();
                                    break;
                                case "3":
                                    displayMenu();
                                    break;
                                case "4":
                                    order();
                                    break;
                                case "5":
                                    displayBill();
                                    displayMultiTableBill();
                                    break;
                                case "6":
                                    pay();
                                    break;
                                case "7":
                                    loop = false;
                                    break;
                                default:
                                    System.out.println("输入有误，请重新输入");
                            }
                        }
                    } else{
                        System.out.println("用户名或密码不正确");
                        System.out.println("==========登陆失败==========");
                    }
                    break;
                case "2":
                    loop = false;
                    break;
                default:
                    System.out.println("输入有误，请重新输入");
            }
        }
        System.out.println("成功退出～");
    }

    public static void main(String[] args) {
        new MHTView().mainMenu();
    }
}
