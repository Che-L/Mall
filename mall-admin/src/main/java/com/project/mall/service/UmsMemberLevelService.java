package com.project.mall.service;

import com.project.mall.model.UmsMemberLevel;

import java.util.List;

/**
 * 浼氬憳绛夌骇绠＄悊Service
 * Created by macro on 2018/4/26.
 */
public interface UmsMemberLevelService {
    /**
     * 鑾峰彇鎵?鏈変細鍛樼瓑绾?
     * @param defaultStatus 鏄惁涓洪粯璁や細鍛?
     */
    List<UmsMemberLevel> list(Integer defaultStatus);
}
