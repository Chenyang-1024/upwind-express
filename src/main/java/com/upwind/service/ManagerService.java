package com.upwind.service;

import com.upwind.pojo.Manager;

public interface ManagerService {

    /**
     * 新增管理员
     * @param manager
     * @return
     */
    Integer insertManager (Manager manager);

    /**
     * 注销管理员
     * @param id
     * @return
     */
    boolean deleteManagerById (Integer id);

    /**
     * 修改管理员账号密码
     * @param manager
     * @return
     */
    boolean updateManager (Manager manager);

    /**
     * 系统管理员登录
     * @param account
     * @param password
     * @return
     */
    Manager managerLogin (String account, String password);
}
