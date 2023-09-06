package com.houserent.view;

import com.houserent.domain.House;
import com.houserent.service.HouseService;
import com.houserent.utils.Utility;

public class HouseView {
    private boolean loop = true;
    private char key;
    private HouseService houseService = new HouseService(10);

    public void mainMenu() {
        do {
            System.out.println("-----------------房屋出租系统-----------------");
            System.out.println("1 新 增 房 源");
            System.out.println("2 查 找 房 屋");
            System.out.println("3 删 除 房 屋");
            System.out.println("4 修 改 房 屋 信 息");
            System.out.println("5 房 屋 列 表");
            System.out.println("6 退 出");
            System.out.print("请选择(1-6): ");
            key = Utility.readChar();

            switch (key) {
                case '1': addHouse();
                case '2': findHouse();
                case '3': deleteHouse();
                case '4': changeHouse();
                case '5': details();
                case '6': exit();
            }

        } while (loop);

        System.out.println("你退出了程序~~");
    }

    public void addHouse() {
        String name;
        String phoneNumber;
        String address;
        int rent;
        String position;

        System.out.println("-----------------添加房屋-----------------");
        System.out.print("姓名:");
        name = Utility.readString(8);
        System.out.print("电话:");
        phoneNumber = Utility.readString(12);
        System.out.print("地址:");
        address = Utility.readString(16);
        System.out.print("月租:");
        rent = Utility.readInt();
        System.out.print("状态(未出租/已出租):");
        position = Utility.readString(3);
        House newHouse = new House(name, phoneNumber, address, rent, position);
        if (houseService.add(newHouse)) {
            System.out.println("-----------------添加房屋成功-----------------");
        } else {
            System.out.println("-----------------添加房屋失败-----------------");
        }
    }

    public void findHouse() {
        System.out.println("-----------------查找房屋-----------------");
        System.out.print("请输入你要查找的id:");
        int findId = Utility.readInt();
        House target = houseService.findById(findId);
        if (target != null) {
            System.out.println(target);
        } else {
            System.out.println("查找失败，不存在编号为" + findId + "的房屋");
        }
    }

    public void deleteHouse() {
        System.out.println("-----------------删除房屋-----------------");
        System.out.println("请选择待删除房屋编号(-1退出):");
        int delId = Utility.readInt();
        if (delId == -1) {
            return;
        }
        char choice = Utility.readConfirmSelection();
        if (choice == 'Y') {
            if (houseService.del(delId)) {
                System.out.println("-----------------删除房屋信息成功-----------------");
            } else {
                System.out.println("-----------------房屋编号不存在，删除失败-----------------");
            }
        }
    }

    public void changeHouse() {
        System.out.println("-----------------修改房屋-----------------");
        System.out.println("请选择待修改房屋编号(-1退出):");
        int updateId = Utility.readInt();
        if (updateId == -1) {
            return;
        }
        House target = houseService.findById(updateId);
        if (target == null) {
            System.out.println("-----------------该房屋不存在，修改失败-----------------");
        }
        System.out.print("姓名(" + target.getName() +"):");
        String name = Utility.readString(8, ""); //如果直接回车，默认返回""
        if (!"".equals(name)) {
            target.setName(name);
        }

        System.out.print("电话("+ target.getPhoneNumber() +"):");
        String phoneNumber = Utility.readString(12, "");
        if (!"".equals(phoneNumber)) {
            target.setPhoneNumber(phoneNumber);
        }

        System.out.print("地址("+ target.getAddress() +"):");
        String address = Utility.readString(18, "");
        if (!"".equals(address)) {
            target.setAddress(address);
        }

        System.out.print("租金("+ target.getRent() +"):");
        int rent = Utility.readInt(-1);
        if (rent != -1) {
            target.setRent(rent);
        }
        System.out.print("状态("+ target.getPosition() +"):");
        String position = Utility.readString(3, "");
        if (!"".equals(position)) {
            target.setPosition(position);
        }
        System.out.println("-----------------修改房屋信息成功-----------------");
    }
    public void details() {
        System.out.println("-----------------房屋列表-----------------");
        System.out.println("编号\t\t房主\t\t电话\t\t地址\t\t月租\t\t状态(未出租/已出租)");
        House[] houses = houseService.list();
        for (int i = 0; i < houses.length; i++) {
            if (houses[i] == null) {
                break;
            }
            System.out.println(houses[i]);
        }
        System.out.println("-----------------房屋列表完成-----------------");
    }

    public void exit() {
        char choice = Utility.readConfirmSelection();
        if (choice == 'Y') {
            loop = false;
        }
    }
}
