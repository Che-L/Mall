package com.project.mall.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.project.mall.common.api.CommonResult;
import com.project.mall.dto.BucketPolicyConfigDto;
import com.project.mall.dto.MinioUploadDto;
import io.minio.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * MinIO閻庣數顢婇挅鍕偓娑櫭崑宥囩不閿涘嫭鍊濩ontroller
 * Created by macro on 2019/12/25.
 */
@Controller
@RequestMapping("/minio")
public class MinioController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MinioController.class);
    @Value("${minio.endpoint}")
    private String ENDPOINT;
    @Value("${minio.bucketName}")
    private String BUCKET_NAME;
    @Value("${minio.accessKey}")
    private String ACCESS_KEY;
    @Value("${minio.secretKey}")
    private String SECRET_KEY;
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult upload(@RequestPart("file") MultipartFile file) {
        try {
            //闁告帗绋戠紓鎾寸▔閳ь剚绋夐悪绛皀IO闁汇劌鍤檃va閻庡箍鍨洪崺娑氱博?
            MinioClient minioClient =MinioClient.builder()
                    .endpoint(ENDPOINT)
                    .credentials(ACCESS_KEY,SECRET_KEY)
                    .build();
            boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(BUCKET_NAME).build());
            if (isExist) {
                LOGGER.info("閻庢稒锚閸嬪秴顩肩捄鍝勫殥缂備礁绻愰悺銊╁捶椤帞纾");
            } else {
                //闁告帗绋戠紓鎾垛偓娑櫭崑宥咁浖鐠洪缚瀚欓悹浣稿⒔閻ゅ棝宕ｉ鍥跺殺闁哄鍟村?
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(BUCKET_NAME).build());
                BucketPolicyConfigDto bucketPolicyConfigDto = createBucketPolicyConfigDto(BUCKET_NAME);
                SetBucketPolicyArgs setBucketPolicyArgs = SetBucketPolicyArgs.builder()
                        .bucket(BUCKET_NAME)
                        .config(JSONUtil.toJsonStr(bucketPolicyConfigDto))
                        .build();
                minioClient.setBucketPolicy(setBucketPolicyArgs);
            }
            String filename = file.getOriginalFilename();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            // 閻犱礁澧介悿鍡欌偓娑櫭崑宥団偓鐢殿攰閽栧嫰宕ュ鍥?
            String objectName = sdf.format(new Date()) + "/" + filename;
            // 濞达綀娉曢弫顦杣tObject濞戞挸锕ｇ槐鑸电▔閳ь剚绋夐鍛€ù鐘烘硾閸╁瞼鈧稒锚閸嬪秴顩奸張浣冨幀
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .bucket(BUCKET_NAME)
                    .object(objectName)
                    .contentType(file.getContentType())
                    .stream(file.getInputStream(), file.getSize(), ObjectWriteArgs.MIN_MULTIPART_SIZE).build();
            minioClient.putObject(putObjectArgs);
            LOGGER.info("闁哄倸娲ｅ▎銏＄▔婵犱胶鐐婇柟瀛樺姇婵?");
            MinioUploadDto minioUploadDto = new MinioUploadDto();
            minioUploadDto.setName(filename);
            minioUploadDto.setUrl(ENDPOINT + "/" + BUCKET_NAME + "/" + objectName);
            return CommonResult.success(minioUploadDto);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("濞戞挸锕ｇ槐鍫曞矗閹寸姵鏅搁梺鎸庣懆椤? {}闁", e.getMessage());
        }
        return CommonResult.failed();
    }

    /**
     * 闁告帗绋戠紓鎾垛偓娑櫭崑宥咁浖閸撲焦鐣遍悹浣告健濡墎绮甸弽顐ｆ闁挎稑鐭侀鏇犵磾椤旀槒绀嬮柛娆樹海椤曚即寮堕崘顔筋€?
     */
    private BucketPolicyConfigDto createBucketPolicyConfigDto(String bucketName) {
        BucketPolicyConfigDto.Statement statement = BucketPolicyConfigDto.Statement.builder()
                .Effect("Allow")
                .Principal("*")
                .Action("s3:GetObject")
                .Resource("arn:aws:s3:::"+bucketName+"/*.**").build();
        return BucketPolicyConfigDto.builder()
                .Version("2012-10-17")
                .Statement(CollUtil.toList(statement))
                .build();
    }
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult delete(@RequestParam("objectName") String objectName) {
        try {
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(ENDPOINT)
                    .credentials(ACCESS_KEY,SECRET_KEY)
                    .build();
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(BUCKET_NAME).object(objectName).build());
            return CommonResult.success(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CommonResult.failed();
    }
}
