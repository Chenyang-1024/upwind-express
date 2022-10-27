package com.upwind.service.impl;

import com.upwind.mapper.ManagerMapper;
import com.upwind.pojo.Manager;
import com.upwind.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
