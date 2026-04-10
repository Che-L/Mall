package com.project.mall.dao;

import com.project.mall.model.UmsAdminRoleRelation;
import com.project.mall.model.UmsResource;
import com.project.mall.model.UmsRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 鍚庡彴鐢ㄦ埛涓庤鑹插叧绯荤鐞嗚嚜瀹氫箟Dao
 * Created by macro on 2018/10/8.
 */
public interface UmsAdminRoleRelationDao {
    /**
     * 鎵归噺鎻掑叆鐢ㄦ埛瑙掕壊鍏崇郴
     */
    int insertList(@Param("list") List<UmsAdminRoleRelation> adminRoleRelationList);

    /**
     * 鑾峰彇鐢ㄤ簬鎵?鏈夎鑹?
     */
    List<UmsRole> getRoleList(@Param("adminId") Long adminId);

    /**
     * 鑾峰彇鐢ㄦ埛鎵?鏈夊彲璁块棶璧勬簮
     */
    List<UmsResource> getResourceList(@Param("adminId") Long adminId);

    /**
     * 鑾峰彇璧勬簮鐩稿叧鐢ㄦ埛ID鍒楄〃
     */
    List<Long> getAdminIdList(@Param("resourceId") Long resourceId);
}
