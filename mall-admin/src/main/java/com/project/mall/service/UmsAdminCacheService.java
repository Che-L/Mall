package com.project.mall.service;

import com.project.mall.model.UmsAdmin;
import com.project.mall.model.UmsResource;

import java.util.List;

/**
 * 鍚庡彴鐢ㄦ埛缂撳瓨鎿嶄綔Service
 * Created by macro on 2020/3/13.
 */
public interface UmsAdminCacheService {
    /**
     * 鍒犻櫎鍚庡彴鐢ㄦ埛缂撳瓨
     */
    void delAdmin(Long adminId);

    /**
     * 鍒犻櫎鍚庡彴鐢ㄦ埛璧勬簮鍒楄〃缂撳瓨
     */
    void delResourceList(Long adminId);

    /**
     * 褰撹鑹茬浉鍏宠祫婧愪俊鎭敼鍙樻椂鍒犻櫎鐩稿叧鍚庡彴鐢ㄦ埛缂撳瓨
     */
    void delResourceListByRole(Long roleId);

    /**
     * 褰撹鑹茬浉鍏宠祫婧愪俊鎭敼鍙樻椂鍒犻櫎鐩稿叧鍚庡彴鐢ㄦ埛缂撳瓨
     */
    void delResourceListByRoleIds(List<Long> roleIds);

    /**
     * 褰撹祫婧愪俊鎭敼鍙樻椂锛屽垹闄よ祫婧愰」鐩悗鍙扮敤鎴风紦瀛?
     */
    void delResourceListByResource(Long resourceId);

    /**
     * 鑾峰彇缂撳瓨鍚庡彴鐢ㄦ埛淇℃伅
     */
    UmsAdmin getAdmin(String username);

    /**
     * 璁剧疆缂撳瓨鍚庡彴鐢ㄦ埛淇℃伅
     */
    void setAdmin(UmsAdmin admin);

    /**
     * 鑾峰彇缂撳瓨鍚庡彴鐢ㄦ埛璧勬簮鍒楄〃
     */
    List<UmsResource> getResourceList(Long adminId);

    /**
     * 璁剧疆缂撳瓨鍚庡彴鐢ㄦ埛璧勬簮鍒楄〃
     */
    void setResourceList(Long adminId, List<UmsResource> resourceList);
}
