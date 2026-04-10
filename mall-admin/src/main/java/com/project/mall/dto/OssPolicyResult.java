package com.project.mall.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 閼惧嘲褰嘜SS娑撳﹣绱堕弬鍥︽閹哄牊娼堟潻鏂挎礀缂佹挻鐏?
 * Created by macro on 2018/5/17.
 */
@Data
@EqualsAndHashCode
public class OssPolicyResult {
    private String accessKeyId;
    private String policy;
    private String signature;
    private String dir;
    private String host;
    private String callback;
}
