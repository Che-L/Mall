package com.project.mall.dto;

import com.project.mall.model.OmsCompanyAddress;
import com.project.mall.model.OmsOrderReturnApply;
import lombok.Getter;
import lombok.Setter;

/**
 * 鐢宠淇℃伅灏佽
 * Created by macro on 2018/10/18.
 */
public class OmsOrderReturnApplyResult extends OmsOrderReturnApply {
    @Getter
    @Setter
    private OmsCompanyAddress companyAddress;
}
