package com.project.mall.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * OSS娑撳﹣绱堕幋鎰閸氬海娈戦崶鐐剁殶閸欏倹鏆?
 * Created by macro on 2018/5/17.
 */
@Data
@EqualsAndHashCode
public class OssCallbackParam {
    private String callbackUrl;
    private String callbackBody;
    private String callbackBodyType;
}
