package com.videos.course.server.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.*;
import org.apache.commons.codec.binary.Base64;

import java.io.File;
import java.io.InputStream;

/**
 * @author 隔壁_老陈
 * 代码风骚、效率恐怖、编译器深深鄙视智商。
 * 千里之外定位问题、瞬间修复依旧风骚……
 * @create 2021-04-24 23:38
 * 复制官网上的
 */
public class VodUtiles {

    /**
     * 使用AK初始化VOD客户端
     * @param accessKeyId
     * @param accessKeySecret
     * @return
     * @throws ClientException
     */
    public static DefaultAcsClient initVodClient(String accessKeyId, String accessKeySecret) throws ClientException {
        // 根据点播接入服务所在的Region填写，例如：接入服务在上海，则填cn-shanghai；其他区域请参见点播中心和访问域名。
        String regionId = "cn-shanghai";
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        return client;
    }

    /**
     * 获取视频上传地址和凭证
     * @param vodClient
     * @return
     */
    public static CreateUploadVideoResponse createUploadVideo(DefaultAcsClient vodClient, String fileNmae) throws ClientException {
        CreateUploadVideoRequest request = new CreateUploadVideoRequest();
        //设置视频名称和标题名称
        request.setFileName(fileNmae);
        request.setTitle(fileNmae);
        //request.setDescription("this is desc");
        //request.setTags("tag1,tag2");
        //视频的封面
        //request.setCoverURL("http://vod.aliyun.com/test_cover_url.jpg");
        request.setCateId(1000296360L); //设置视频分类的组的id
        request.setTemplateGroupId("09af5415a715aec8eb2d619a0b87a118");//设置视频加密的模版id
        //request.setWorkflowId("");
        //request.setStorageLocation("");
        //request.setAppId("app-1000000");
        //设置请求超时时间
        request.setSysReadTimeout(1000);
        request.setSysConnectTimeout(1000);
        return vodClient.getAcsResponse(request);
    }

    /**
     * 使用上传凭证和地址初始化OSS客户端（注意需要先Base64解码并Json Decode再传入）。
     * @param uploadAuth
     * @param uploadAddress
     * @return
     */
    public static OSSClient initOssClient(JSONObject uploadAuth, JSONObject uploadAddress) {
        String endpoint = uploadAddress.getString("Endpoint");
        String accessKeyId = uploadAuth.getString("AccessKeyId");
        String accessKeySecret = uploadAuth.getString("AccessKeySecret");
        String securityToken = uploadAuth.getString("SecurityToken");
        return new OSSClient(endpoint, accessKeyId, accessKeySecret, securityToken);
    }

    /**
     * 简单的上传文件
     *    上传本地文件复制一个修改下里面的参数
     * @param ossClient
     * @param uploadAddress
     * @param localFile
     */
    public static void uploadLocalFile(OSSClient ossClient, JSONObject uploadAddress,  InputStream inputStream){
        String bucketName = uploadAddress.getString("Bucket");
        String objectName = uploadAddress.getString("FileName");

        ossClient.putObject(bucketName, objectName, inputStream);
    }
    /**
     * 上传本地文件
     * @param ossClient
     * @param uploadAddress
     * @param localFile
     */
    public static void uploadLocalFile(OSSClient ossClient, JSONObject uploadAddress, String localFile){
        String bucketName = uploadAddress.getString("Bucket");
        String objectName = uploadAddress.getString("FileName");
        File file = new File(localFile);
        ossClient.putObject(bucketName, objectName, file);
    }

    /**
     * 刷新上传凭证
     * @param vodClient
     * @return
     * @throws ClientException
     */
    public static RefreshUploadVideoResponse refreshUploadVideo(DefaultAcsClient vodClient) throws ClientException {
        RefreshUploadVideoRequest request = new RefreshUploadVideoRequest();
        request.setAcceptFormat(FormatType.JSON);
        request.setVideoId("VideoId");
        //设置请求超时时间
        request.setSysReadTimeout(1000);
        request.setSysConnectTimeout(1000);
        return vodClient.getAcsResponse(request);
    }

    /**
     *  在媒资管理-获取源文件信息（含源片下载地址）中
     * 获取源文件信息
     * @param client 发送请求客户端
     * @return GetMezzanineInfoResponse 获取源文件信息响应数据
     * @throws Exception
     */
    public static GetMezzanineInfoResponse getMezzanineInfo(DefaultAcsClient client, String videoId) throws Exception {
        GetMezzanineInfoRequest request = new GetMezzanineInfoRequest();
        request.setVideoId(videoId);
        //源片下载地址过期时间
        request.setAuthTimeout(3600L);
        return client.getAcsResponse(request);
    }
    /*获取播放凭证函数*/
    public static GetVideoPlayAuthResponse getVideoPlayAuth(DefaultAcsClient client,String videoId) throws Exception {
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        //request.setVideoId("视频ID");
        request.setVideoId(videoId);
        return client.getAcsResponse(request);
    }
        public static void main(String[] argv) {
            //您的AccessKeyId
            String accessKeyId = "LTAI5tMZoBYeYqqTgRRnKjfH";
            //您的AccessKeySecret
            String accessKeySecret = "cSLPZpsDbNVcGw0MFE9ke810uTh4IY";
            //需要上传到VOD的本地视频文件的完整路径，需要包含文件扩展名
            /**
             * 手动修改，需要上传的视频地址
             */
            String localFile = "C:\\Users\\admin\\Videos\\知识点4 主启动类源码探究.mp4";
            try {
                System.out.println(accessKeyId + "：" + accessKeySecret);
                // 初始化VOD客户端并获取上传地址和凭证
                DefaultAcsClient vodClient = initVodClient(accessKeyId, accessKeySecret);
                String fileName = "test.mp4";
                CreateUploadVideoResponse createUploadVideoResponse = createUploadVideo(vodClient,fileName);
                // 执行成功会返回VideoId、UploadAddress和UploadAuth
                String videoId = createUploadVideoResponse.getVideoId();
                /**
                 * 由于版本的原因，需要手动的修改Base64.decodeBase6，,JSONObject.cla
                 */
                JSONObject uploadAuth = JSONObject.parseObject(Base64.decodeBase64(createUploadVideoResponse.getUploadAuth()),JSONObject.class);
                JSONObject uploadAddress = JSONObject.parseObject(Base64.decodeBase64(createUploadVideoResponse.getUploadAddress()),JSONObject.class);
                // 使用UploadAuth和UploadAddress初始化OSS客户端
                OSSClient ossClient = initOssClient(uploadAuth, uploadAddress);
                // 上传文件，注意是同步上传会阻塞等待，耗时与文件大小和网络上行带宽有关
                uploadLocalFile(ossClient, uploadAddress, localFile);
                System.out.println("Put local file succeed, VideoId : " + videoId);

                /**
                 * 获取视频播放地址
                 */
                GetMezzanineInfoResponse response = new GetMezzanineInfoResponse();
                response = getMezzanineInfo(vodClient, videoId);
                System.out.println("获取视频信息, response : " + JSON.toJSONString(response));

            } catch (Exception e) {
                System.out.println("Put local file fail, ErrorMessage : " + e.getLocalizedMessage());
            }
        }

}
