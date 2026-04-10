package com.project.mall.service;

import com.project.mall.dto.UmsAdminParam;
import com.project.mall.dto.UpdateAdminPasswordParam;
import com.project.mall.model.UmsAdmin;
import com.project.mall.model.UmsResource;
import com.project.mall.model.UmsRole;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 鍚庡彴鐢ㄦ埛绠＄悊Service
 * Created by macro on 2018/4/26.
 */
public interface UmsAdminService {
    /**
     * 鏍规嵁鐢ㄦ埛鍚嶈幏鍙栧悗鍙扮鐞嗗憳
     */
    UmsAdmin getAdminByUsername(String username);

    /**
     * 娉ㄥ唽鍔熻兘
     */
    UmsAdmin register(UmsAdminParam umsAdminParam);

    /**
     * 鐧诲綍鍔熻兘
     * @param username 鐢ㄦ埛鍚?
     * @param password 瀵嗙爜
     * @return 鐢熸垚鐨凧WT鐨則oken
     */
    String login(String username,String password);

    /**
     * 鍒锋柊token鐨勫姛鑳?
     * @param oldToken 鏃х殑token
     */
    String refreshToken(String oldToken);

    /**
     * 鏍规嵁鐢ㄦ埛id鑾峰彇鐢ㄦ埛
     */
    UmsAdmin getItem(Long id);

    /**
     * 鏍规嵁鐢ㄦ埛鍚嶆垨鏄电О鍒嗛〉鏌ヨ鐢ㄦ埛
     */
    List<UmsAdmin> list(String keyword, Integer pageSize, Integer pageNum);

    /**
     * 淇敼鎸囧畾鐢ㄦ埛淇℃伅
     */
    int update(Long id, UmsAdmin admin);

    /**
     * 鍒犻櫎鎸囧畾鐢ㄦ埛
     */
    int delete(Long id);

    /**
     * 淇敼鐢ㄦ埛瑙掕壊鍏崇郴
     */
    @Transactional
    int updateRole(Long adminId, List<Long> roleIds);

    /**
     * 鑾峰彇鐢ㄦ埛瀵瑰簲瑙掕壊
     */
    List<UmsRole> getRoleList(Long adminId);

    /**
     * 鑾峰彇鎸囧畾鐢ㄦ埛鐨勫彲璁块棶璧勬簮
     */
    List<UmsResource> getResourceList(Long adminId);

    /**
     * 淇敼瀵嗙爜
     */
    int updatePassword(UpdateAdminPasswordParam updatePasswordParam);

    /**
     * 鑾峰彇鐢ㄦ埛淇℃伅
     */
    UserDetails loadUserByUsername(String username);

    /**
     * 鑾峰彇缂撳瓨鏈嶅姟
     */
    UmsAdminCacheService getCacheService();
}
