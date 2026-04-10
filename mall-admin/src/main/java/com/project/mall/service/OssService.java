package com.project.mall.service;

import com.project.mall.dto.OssCallbackResult;
import com.project.mall.dto.OssPolicyResult;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Oss瀵硅薄瀛樺偍绠＄悊Service
 * Created by macro on 2018/5/17.
 */
public interface OssService {
    /**
     * Oss涓婁紶绛栫暐鐢熸垚
     */
    OssPolicyResult policy();
    /**
     * Oss涓婁紶鎴愬姛鍥炶皟
     */
    OssCallbackResult callback(HttpServletRequest request);
}
