package com.project.mall.service.impl;

import com.project.mall.mapper.OmsCompanyAddressMapper;
import com.project.mall.model.OmsCompanyAddress;
import com.project.mall.model.OmsCompanyAddressExample;
import com.project.mall.service.OmsCompanyAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 鏀惰揣鍦板潃绠＄悊Service瀹炵幇绫?
 * Created by macro on 2018/10/18.
 */
@Service
public class OmsCompanyAddressServiceImpl implements OmsCompanyAddressService {
    @Autowired
    private OmsCompanyAddressMapper companyAddressMapper;
    @Override
    public List<OmsCompanyAddress> list() {
        return companyAddressMapper.selectByExample(new OmsCompanyAddressExample());
    }
}
