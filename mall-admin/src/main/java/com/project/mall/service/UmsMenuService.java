package com.project.mall.service;

import com.project.mall.dto.UmsMenuNode;
import com.project.mall.model.UmsMenu;

import java.util.List;

/**
 * 鍚庡彴鑿滃崟绠＄悊Service
 * Created by macro on 2020/2/2.
 */
public interface UmsMenuService {
    /**
     * 鍒涘缓鍚庡彴鑿滃崟
     */
    int create(UmsMenu umsMenu);

    /**
     * 淇敼鍚庡彴鑿滃崟
     */
    int update(Long id, UmsMenu umsMenu);

    /**
     * 鏍规嵁ID鑾峰彇鑿滃崟璇︽儏
     */
    UmsMenu getItem(Long id);

    /**
     * 鏍规嵁ID鍒犻櫎鑿滃崟
     */
    int delete(Long id);

    /**
     * 鍒嗛〉鏌ヨ鍚庡彴鑿滃崟
     */
    List<UmsMenu> list(Long parentId, Integer pageSize, Integer pageNum);

    /**
     * 鏍戝舰缁撴瀯杩斿洖鎵?鏈夎彍鍗曞垪琛?
     */
    List<UmsMenuNode> treeList();

    /**
     * 淇敼鑿滃崟鏄剧ず鐘舵??
     */
    int updateHidden(Long id, Integer hidden);
}
