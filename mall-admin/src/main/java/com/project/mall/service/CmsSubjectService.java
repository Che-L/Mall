package com.project.mall.service;

import com.project.mall.model.CmsSubject;

import java.util.List;

/**
 * 鍟嗗搧涓撻绠＄悊Service
 * Created by macro on 2018/6/1.
 */
public interface CmsSubjectService {
    /**
     * 鏌ヨ鎵?鏈変笓棰?
     */
    List<CmsSubject> listAll();

    /**
     * 鍒嗛〉鏌ヨ涓撻
     */
    List<CmsSubject> list(String keyword, Integer pageNum, Integer pageSize);
}
