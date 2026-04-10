package com.project.mall.dto;

import com.project.mall.model.SmsFlashPromotionSession;
import lombok.Getter;
import lombok.Setter;

/**
 * 閸栧懎鎯堥崯鍡楁惂閺佷即鍣洪惃鍕簚濞嗏€蹭繆閹?
 * Created by macro on 2018/11/19.
 */
public class SmsFlashPromotionSessionDetail extends SmsFlashPromotionSession {
    @Setter
    @Getter
    private Long productCount;
}
