package com.project.mall.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * OSS娑撳﹣绱堕弬鍥︽閻ㄥ嫬娲栫拫鍐波閺?
 * Created by macro on 2018/5/17.
 */
@Data
@EqualsAndHashCode
public class OssCallbackResult {
    private String filename;
    private String size;
    private String mimeType;
    private String width;
    private String height;
}
