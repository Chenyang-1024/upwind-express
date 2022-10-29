package com.upwind.service.impl;

import com.upwind.mapper.ManagerMapper;
import com.upwind.pojo.Manager;
import com.upwind.pojo.ManagerExample;
import com.upwind.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
// 作为系统管理员的话，其实没必要新增管理员，同理也没必要删除管理员
// 统一给一个管理员账号即可，如 admin
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    private ManagerMapper managerMapper;

    @Override
    // 新增管理员，返回 id
    public Integer insertManager(Manager manager) {
        int row = managerMapper.insert(manager);
        if (row > 0)
            return manager.getId();
        else
            return null;
    }

    @Override
    // 删除管理员账号
    public boolean deleteManagerById(Integer id) {
        return managerMapper.deleteByPrimaryKey(id) > 0;
    }

    @Override
    // 修改管理员账号密码
    public boolean updateManager(Manager manager) {
        return managerMapper.updateByPrimaryKeySelective(manager) > 0;
    }

    @Override
    // Controller 传给 Service 的密码，是已经过 MD5 加密过的！
    public Manager managerLogin(String account, String password) {
        ManagerExample managerExample = new ManagerExample();
        managerExample.createCriteria().andAccountEqualTo(account);
        List<Manager> managerList = managerMapper.selectByExample(managerExample);
        if (managerList.size() > 0) {
            Manager manager = managerList.get(0);
            if (password.equals(manager.getPassword()))
                return manager;
        }
        return null;
    }
}
