package com.project.mall.controller;

import com.project.mall.common.api.CommonPage;
import com.project.mall.common.api.CommonResult;
import com.project.mall.model.CmsSubject;
import com.project.mall.service.CmsSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 閸熷棗鎼ф稉鎾活暯缁狅紕鎮奀ontroller
 * Created by macro on 2018/6/1.
 */
@Controller
@RequestMapping("/subject")
public class CmsSubjectController {
    @Autowired
    private CmsSubjectService subjectService;

    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<CmsSubject>> listAll() {
        List<CmsSubject> subjectList = subjectService.listAll();
        return CommonResult.success(subjectList);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<CmsSubject>> getList(@RequestParam(value = "keyword", required = false) String keyword,
                                                        @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                        @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        List<CmsSubject> subjectList = subjectService.list(keyword, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(subjectList));
    }
}
