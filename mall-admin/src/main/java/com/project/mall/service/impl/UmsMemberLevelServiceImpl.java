package com.project.mall.service.impl;

import com.project.mall.mapper.UmsMemberLevelMapper;
import com.project.mall.model.UmsMemberLevel;
import com.project.mall.model.UmsMemberLevelExample;
import com.project.mall.service.UmsMemberLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 浼氬憳绛夌骇绠＄悊Service瀹炵幇绫?
 * Created by macro on 2018/4/26.
 */
@Service
public class UmsMemberLevelServiceImpl implements UmsMemberLevelService{
    @Autowired
    private UmsMemberLevelMapper memberLevelMapper;
    @Override
    public List<UmsMemberLevel> list(Integer defaultStatus) {
        UmsMemberLevelExample example = new UmsMemberLevelExample();
        example.createCriteria().andDefaultStatusEqualTo(defaultStatus);
        return memberLevelMapper.selectByExample(example);
    }
}
