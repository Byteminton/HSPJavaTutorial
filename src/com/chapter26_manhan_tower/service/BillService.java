package com.chapter26_manhan_tower.service;

import com.chapter26_manhan_tower.dao.BillDAO;
import com.chapter26_manhan_tower.dao.MultiTableDAO;
import com.chapter26_manhan_tower.domain.Bill;
import com.chapter26_manhan_tower.domain.Menu;
import com.chapter26_manhan_tower.domain.MultiTableBean;

import java.util.List;
import java.util.UUID;

public class BillService {
    private BillDAO billDAO = new BillDAO();
    private MenuService menuService = new MenuService();
    private DiningTableService diningTableService = new DiningTableService();
    private MultiTableDAO multiTableDAO = new MultiTableDAO();
    public boolean addBill(int menuId, int nums, int diningTableId) {
        // 生成一个账单号UUID
        String billId = UUID.randomUUID().toString();
        Menu menu = menuService.getMenu(menuId);
        int update = billDAO.update("insert into bill values(null, ?, ?, ?, ?, ?, NOW(), '未结账')",
                billId, menuId, nums, menu.getPrice() * nums, diningTableId);
        if (update <= 0) {
            return false;
        }
        return diningTableService.updateDiningTableState(diningTableId, "就餐中");
    }

    public List<Bill> showBill() {
        return billDAO.queryReturnMultipleRows("select * from bill", Bill.class);
    }
    // 返回的账单带有菜品名
    public List<MultiTableBean> showBillPro() {
        return multiTableDAO.queryReturnMultipleRows("select bill.*, menu.name as menuName from bill, menu where bill.menuId = menu.id"
        , MultiTableBean.class);
    }

    public List<Bill> checkBill(int diningTableId) {
        return billDAO.queryReturnMultipleRows("select * from bill where diningTableId = ? and state = '未结账'",
                Bill.class, diningTableId);
    }

    public boolean payBill(int diningTableId, String payMode) {
        int update = billDAO.update("update bill set state = ? where diningTableId = ? and state = '未结账'",
                payMode, diningTableId);
        if (update <= 0) {
            return false;
        }
        return diningTableService.emptyDiningTable(diningTableId, "空");
    }
}
