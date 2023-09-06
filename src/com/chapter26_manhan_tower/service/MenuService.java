package com.chapter26_manhan_tower.service;

import com.chapter26_manhan_tower.dao.MenuDAO;
import com.chapter26_manhan_tower.domain.Menu;

import java.util.List;

public class MenuService {
    private MenuDAO menuDAO = new MenuDAO();

    public List<Menu> showMenu() {
        return menuDAO.queryReturnMultipleRows("select * from menu", Menu.class);
    }

    public Menu getMenu(int id) {
        return menuDAO.queryReturnSingleRow("select * from menu where id = ?",
                Menu.class, id);
    }
}
