package com.project.mall.controller;

import com.project.mall.common.api.CommonResult;
import com.project.mall.model.OmsCompanyAddress;
import com.project.mall.service.OmsCompanyAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 閺€鎯版彛閸︽澘娼冪粻锛勬倞Controller
 * Created by macro on 2018/10/18.
 */
@Controller
@RequestMapping("/companyAddress")
public class OmsCompanyAddressController {
    @Autowired
    private OmsCompanyAddressService companyAddressService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<OmsCompanyAddress>> list() {
        List<OmsCompanyAddress> companyAddressList = companyAddressService.list();
        return CommonResult.success(companyAddressList);
    }
}
