package com.project.mall.service.impl;

import cn.hutool.json.JSONUtil;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.project.mall.dto.OssCallbackParam;
import com.project.mall.dto.OssCallbackResult;
import com.project.mall.dto.OssPolicyResult;
import com.project.mall.service.OssService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Oss閻庣數顢婇挅鍕偓娑櫭崑宥囩不閿涘嫭鍊濻ervice閻庡湱鍋熼獮鍥╃尵?
 * Created by macro on 2018/5/17.
 */
@Service
public class OssServiceImpl implements OssService {

	private static final Logger LOGGER = LoggerFactory.getLogger(OssServiceImpl.class);
	@Value("${aliyun.oss.policy.expire}")
	private int ALIYUN_OSS_EXPIRE;
	@Value("${aliyun.oss.maxSize}")
	private int ALIYUN_OSS_MAX_SIZE;
	@Value("${aliyun.oss.callback}")
	private String ALIYUN_OSS_CALLBACK;
	@Value("${aliyun.oss.bucketName}")
	private String ALIYUN_OSS_BUCKET_NAME;
	@Value("${aliyun.oss.endpoint}")
	private String ALIYUN_OSS_ENDPOINT;
	@Value("${aliyun.oss.dir.prefix}")
	private String ALIYUN_OSS_DIR_PREFIX;

	@Autowired
	private OSSClient ossClient;

	/**
	 * 缂佹稒鍎抽幃鏇㈡偨閻旂鐏?
	 */
	@Override
	public OssPolicyResult policy() {
		OssPolicyResult result = new OssPolicyResult();
		// 閻庢稒锚閸嬪秹鎯勯鑲╃Э
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dir = ALIYUN_OSS_DIR_PREFIX+sdf.format(new Date());
		// 缂佹稒鍎抽幃鏇㈠嫉婢跺娅忛柡?
		long expireEndTime = System.currentTimeMillis() + ALIYUN_OSS_EXPIRE * 1000;
		Date expiration = new Date(expireEndTime);
		// 闁哄倸娲ｅ▎銏″緞瑜嶉惃?
		long maxSize = ALIYUN_OSS_MAX_SIZE * 1024 * 1024;
		// 闁搞儳鍋犻惃?
		OssCallbackParam callback = new OssCallbackParam();
		callback.setCallbackUrl(ALIYUN_OSS_CALLBACK);
		callback.setCallbackBody("filename=${object}&size=${size}&mimeType=${mimeType}&height=${imageInfo.height}&width=${imageInfo.width}");
		callback.setCallbackBodyType("application/x-www-form-urlencoded");
		// 闁圭粯鍔掑锕傛嚍閸屾粌浠?
		String action = "http://" + ALIYUN_OSS_BUCKET_NAME + "." + ALIYUN_OSS_ENDPOINT;
		try {
			PolicyConditions policyConds = new PolicyConditions();
			policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, maxSize);
			policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);
			String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);
			byte[] binaryData = postPolicy.getBytes("utf-8");
			String policy = BinaryUtil.toBase64String(binaryData);
			String signature = ossClient.calculatePostSignature(postPolicy);
			String callbackData = BinaryUtil.toBase64String(JSONUtil.parse(callback).toString().getBytes("utf-8"));
			// 閺夆晜鏌ㄥú鏍磼閹惧浜?
			result.setAccessKeyId(ossClient.getCredentialsProvider().getCredentials().getAccessKeyId());
			result.setPolicy(policy);
			result.setSignature(signature);
			result.setDir(dir);
			result.setCallback(callbackData);
			result.setHost(action);
		} catch (Exception e) {
			LOGGER.error("Failed to generate OSS upload policy", e);
		}
		return result;
	}

	@Override
	public OssCallbackResult callback(HttpServletRequest request) {
		OssCallbackResult result= new OssCallbackResult();
		String filename = request.getParameter("filename");
		filename = "http://".concat(ALIYUN_OSS_BUCKET_NAME).concat(".").concat(ALIYUN_OSS_ENDPOINT).concat("/").concat(filename);
		result.setFilename(filename);
		result.setSize(request.getParameter("size"));
		result.setMimeType(request.getParameter("mimeType"));
		result.setWidth(request.getParameter("width"));
		result.setHeight(request.getParameter("height"));
		return result;
	}

}
