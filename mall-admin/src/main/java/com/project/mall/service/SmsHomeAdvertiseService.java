package com.project.mall.service;

import com.project.mall.model.SmsHomeAdvertise;

import java.util.List;

/**
 * 棣栭〉骞垮憡绠＄悊Service
 * Created by macro on 2018/11/7.
 */
public interface SmsHomeAdvertiseService {
    /**
     * 娣诲姞骞垮憡
     */
    int create(SmsHomeAdvertise advertise);

    /**
     * 鎵归噺鍒犻櫎骞垮憡
     */
    int delete(List<Long> ids);

    /**
     * 淇敼涓娿?佷笅绾跨姸鎬?
     */
    int updateStatus(Long id, Integer status);

    /**
     * 鑾峰彇骞垮憡璇︽儏
     */
    SmsHomeAdvertise getItem(Long id);

    /**
     * 鏇存柊骞垮憡
     */
    int update(Long id, SmsHomeAdvertise advertise);

    /**
     * 鍒嗛〉鏌ヨ骞垮憡
     */
    List<SmsHomeAdvertise> list(String name, Integer type, String endTime, Integer pageSize, Integer pageNum);
}
