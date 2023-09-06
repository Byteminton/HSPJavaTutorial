package com.chapter26_manhan_tower.service;

import com.chapter26_manhan_tower.dao.DiningTableDAO;
import com.chapter26_manhan_tower.domain.DiningTable;

import java.util.List;

public class DiningTableService {
    private DiningTableDAO diningTableDAO = new DiningTableDAO();

    public List<DiningTable> tableState() {
        return diningTableDAO.queryReturnMultipleRows("select id, state from diningTable",
                DiningTable.class);
    }

    public DiningTable getDiningTable(int id) {
        return diningTableDAO.queryReturnSingleRow("select state from diningTable where id = ?",
                DiningTable.class, id);
    }

    public boolean book(int id, String name, String tel) {
        int update = diningTableDAO.update("update diningTable set state = '已预订', orderName = ?, orderTel = ? where id = ?", name, tel, id);
        return update > 0;
    }

    public boolean updateDiningTableState(int diningTableId, String state) {
        int update = diningTableDAO.update("update diningTable set state = ? where id = ?",
                state, diningTableId);
        return update > 0;
    }

    public boolean emptyDiningTable(int id, String state) {
        int update = diningTableDAO.update("update diningTable set state = ?, orderName = '', orderTel = '' where id = ?",
                state, id);
        return update > 0;
    }
}
