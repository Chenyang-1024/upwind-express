package com.upwind.service.impl;

import com.upwind.mapper.ConsumerMapper;
import com.upwind.mapper.CourierMapper;
import com.upwind.mapper.OutletMapper;
import com.upwind.pojo.Courier;
import com.upwind.pojo.CourierExample;
import com.upwind.pojo.Outlet;
import com.upwind.pojo.OutletExample;
import com.upwind.service.OutletService;
import com.upwind.utils.CommonPrefixLengthUtil;
import com.upwind.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OutletServiceImpl implements OutletService {

    @Autowired
    private OutletMapper outletMapper;
    @Autowired
    private CourierMapper courierMapper;

    @Override
    // Controller 在封装之前需要先查询一下，是否已经有该手机号的账号，手机号是需要唯一的
    public Integer insertOutlet(Outlet outlet) {
        int row = outletMapper.insert(outlet);
        if (row > 0)
            return outlet.getId();
        else
            return null;
    }

    @Override
    public boolean deleteOutletById(Integer id) {
        CourierExample courierExample = new CourierExample();
        courierExample.createCriteria().andOutlet_idEqualTo(id);
        // 网点还有下属快递员时，不允许删除网点
        if (courierMapper.selectByExample(courierExample).size() > 0)
            return false;
        return outletMapper.deleteByPrimaryKey(id) > 0;
    }

    @Override
    // Controller 传给 Service 的密码，是已经过 MD5 加密过的！
    public Outlet outletLogin(String phone, String password) {
        OutletExample outletExample = new OutletExample();
        outletExample.createCriteria().andPhoneEqualTo(phone);
        List<Outlet> outletList = outletMapper.selectByExample(outletExample);
        if (outletList.size() > 0) {
            // 因为手机号唯一，所以这里不用再检查，直接取 list 第一个元素即可
            Outlet outlet = outletList.get(0);
            if (password.equals(outlet.getPassword()))
                return outlet;
        }
        return null;
    }

    @Override
    public Outlet getOutletById(Integer id) {
        return outletMapper.selectByPrimaryKey(id);
    }

    @Override
    // 网点名称不要求唯一的情况下，需要在查询结果显示其地址信息以区分不同网点
    // 以下实现方式为网点名称不要求唯一，因此查询将返回网点列表
    public List<Outlet> getOutletByTitle(String title) {
        OutletExample outletExample = new OutletExample();
        outletExample.createCriteria().andTitleEqualTo(title);
        return outletMapper.selectByExample(outletExample);
    }

    @Override
    public List<Outlet> getClostestOutlet(String province, String city, String district, String detail_addr) {
        // 把范围限定在同一个市中
        OutletExample outletExample = new OutletExample();
        outletExample.createCriteria().andProvinceEqualTo(province).andCityEqualTo(city);
        List<Outlet> records = outletMapper.selectByExample(outletExample);
        if (records.size() == 0)
            return null;
        Map<Outlet, Integer> distMap = new HashMap<>();
        List<Outlet> closestOutlets = new ArrayList<>();
        for (Outlet outlet : records) {
            Integer dist = CommonPrefixLengthUtil.getStrCommonPrefixLength(district + detail_addr, outlet.getDistrict() + outlet.getDetail_addr());
            distMap.put(outlet, dist);
        }
        // 应该是将 Map 转化为 List ，以进行排序
        List<Map.Entry<Outlet, Integer>> list = new ArrayList<>(distMap.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Outlet, Integer>>() {
            @Override
            public int compare(Map.Entry<Outlet, Integer> o1, Map.Entry<Outlet, Integer> o2) {
                // 返回 正数 表示需要交换 o1 和 o2 的位置
                // 因为要使得网点按照距离 从近到远 排序，所以用后者匹配长度 - 前者匹配长度
                return o2.getValue() - o1.getValue();
            }
        });
        for (Map.Entry<Outlet, Integer> mapping : list)
            closestOutlets.add(mapping.getKey());
        return closestOutlets;
    }

    @Override
    public boolean updateOutlet(Outlet outlet) {
        Outlet old = outletMapper.selectByPrimaryKey(outlet.getId());
        // 网点转让情况，需要修改新负责人账号密码为默认初始化密码
        if (outlet.getPhone() != null && !outlet.getPhone().equals(old.getPhone()))
            outlet.setPassword(MD5Util.getMD5("123456"));
        return outletMapper.updateByPrimaryKeySelective(outlet) > 0;
    }

    @Override
    public List<Outlet> getAllOutlet() {
        return outletMapper.selectByExample(null);
    }
}
