package com.project.mall.dao;

import com.project.mall.dto.SmsCouponParam;
import org.apache.ibatis.annotations.Param;

/**
 * 浼樻儬鍒哥鐞嗚嚜瀹氫箟Dao
 * Created by macro on 2018/8/29.
 */
public interface SmsCouponDao {
    /**
     * 鑾峰彇浼樻儬鍒歌鎯呭寘鎷粦瀹氬叧绯?
     */
    SmsCouponParam getItem(@Param("id") Long id);
}
