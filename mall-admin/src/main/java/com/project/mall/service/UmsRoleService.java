package com.project.mall.service;

import com.project.mall.model.UmsMenu;
import com.project.mall.model.UmsResource;
import com.project.mall.model.UmsRole;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 鍚庡彴瑙掕壊绠＄悊Service
 * Created by macro on 2018/9/30.
 */
public interface UmsRoleService {
    /**
     * 娣诲姞瑙掕壊
     */
    int create(UmsRole role);

    /**
     * 淇敼瑙掕壊淇℃伅
     */
    int update(Long id, UmsRole role);

    /**
     * 鎵归噺鍒犻櫎瑙掕壊
     */
    int delete(List<Long> ids);

    /**
     * 鑾峰彇鎵?鏈夎鑹插垪琛?
     */
    List<UmsRole> list();

    /**
     * 鍒嗛〉鑾峰彇瑙掕壊鍒楄〃
     */
    List<UmsRole> list(String keyword, Integer pageSize, Integer pageNum);

    /**
     * 鏍规嵁绠＄悊鍛業D鑾峰彇瀵瑰簲鑿滃崟
     */
    List<UmsMenu> getMenuList(Long adminId);

    /**
     * 鑾峰彇瑙掕壊鐩稿叧鑿滃崟
     */
    List<UmsMenu> listMenu(Long roleId);

    /**
     * 鑾峰彇瑙掕壊鐩稿叧璧勬簮
     */
    List<UmsResource> listResource(Long roleId);

    /**
     * 缁欒鑹插垎閰嶈彍鍗?
     */
    @Transactional
    int allocMenu(Long roleId, List<Long> menuIds);

    /**
     * 缁欒鑹插垎閰嶈祫婧?
     */
    @Transactional
    int allocResource(Long roleId, List<Long> resourceIds);
}
