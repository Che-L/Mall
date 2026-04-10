package com.project.mall.service;

import com.project.mall.dto.SmsCouponParam;
import com.project.mall.model.SmsCoupon;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 浼樻儬鍒哥鐞哠ervice
 * Created by macro on 2018/8/28.
 */
public interface SmsCouponService {
    /**
     * 娣诲姞浼樻儬鍒?
     */
    @Transactional
    int create(SmsCouponParam couponParam);

    /**
     * 鏍规嵁浼樻儬鍒竔d鍒犻櫎浼樻儬鍒?
     */
    @Transactional
    int delete(Long id);

    /**
     * 鏍规嵁浼樻儬鍒竔d鏇存柊浼樻儬鍒镐俊鎭?
     */
    @Transactional
    int update(Long id, SmsCouponParam couponParam);

    /**
     * 鍒嗛〉鑾峰彇浼樻儬鍒稿垪琛?
     */
    List<SmsCoupon> list(String name, Integer type, Integer pageSize, Integer pageNum);

    /**
     * 鑾峰彇浼樻儬鍒歌鎯?
     * @param id 浼樻儬鍒歌〃id
     */
    SmsCouponParam getItem(Long id);
}
