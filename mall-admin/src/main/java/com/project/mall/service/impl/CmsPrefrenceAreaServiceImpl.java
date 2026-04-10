package com.project.mall.service.impl;

import com.project.mall.mapper.CmsPrefrenceAreaMapper;
import com.project.mall.model.CmsPrefrenceArea;
import com.project.mall.model.CmsPrefrenceAreaExample;
import com.project.mall.service.CmsPrefrenceAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 鍟嗗搧浼橀?夌鐞哠ervice瀹炵幇绫?
 * Created by macro on 2018/6/1.
 */
@Service
public class CmsPrefrenceAreaServiceImpl implements CmsPrefrenceAreaService {
    @Autowired
    private CmsPrefrenceAreaMapper prefrenceAreaMapper;

    @Override
    public List<CmsPrefrenceArea> listAll() {
        return prefrenceAreaMapper.selectByExample(new CmsPrefrenceAreaExample());
    }
}
