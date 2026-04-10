package com.project.mall.service.impl;

import com.project.mall.mapper.OmsOrderSettingMapper;
import com.project.mall.model.OmsOrderSetting;
import com.project.mall.service.OmsOrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 璁㈠崟璁剧疆绠＄悊Service瀹炵幇绫?
 * Created by macro on 2018/10/16.
 */
@Service
public class OmsOrderSettingServiceImpl implements OmsOrderSettingService {
    @Autowired
    private OmsOrderSettingMapper orderSettingMapper;

    @Override
    public OmsOrderSetting getItem(Long id) {
        return orderSettingMapper.selectByPrimaryKey(id);
    }

    @Override
    public int update(Long id, OmsOrderSetting orderSetting) {
        orderSetting.setId(id);
        return orderSettingMapper.updateByPrimaryKey(orderSetting);
    }
}
