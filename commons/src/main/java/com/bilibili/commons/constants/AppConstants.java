package com.bilibili.commons.constants;

/**
 * @author Silvery
 * @since 2023/8/17 15:12
 */
public class AppConstants {

    /**
     * 网关url
     */
    public static final String REQUEST_URL = "127.0.0.1";

    /**
     * 网关端口
     */
    public static final String REQUEST_PORT = "8080";

    /**
     * 认证请求头
     */
    public static final String AUTH_HEADER = "Authorization";

    /**
     * token从哪位开始
     */
    public static final Integer TOKEN_SITE = 7;

    /**
     * 更新，删除失败的结果
     */
    public static final Integer FALSE_CODE = 0;

    /*
    * 已登出的token
    */
    public static final String JWT_BLACK_LIST = "jwt:blacklist:";

    /*
     * 邮件标识
     */
    public static final String REGISTER_EMAIL = "register_email:";

    /*
     * 邮件限制标识
     */
    public static final String VERIFY_LIMIT = "verify:limit:";

    /*
     * 邮件有效时间
     */
    public static final Integer EMAIL_TIME = 5;

    /*
     * 请求限制时间
     */
    public static final Integer FLOW_TIME = 60;

    /**
     * 图片文件夹
     */
    public static final String PICTURE_BUCK = "picture";


    /**
     * 视频文件夹
     */
    public static final String VIDEO_BUCK = "video";

    /**
     * 文件名位置
     */
    public static final Integer FILE_POSITION = 0;

    /**
     * 父标签
     */
    public static final Integer PARENT_TAG = -1;

    /**
     * 统一名字开头
     */
    public static final String NAME_START = "bi_";

    /**
     * 未通过审核状态
     */
    public static final String NOT_PASS_VIDEO = "未审核";

    /**
     * 通过审核状态
     */
    public static final String PASS_VIDEO = "已审核";

    /**
     * 是不是轮播图
     */
    public static final Integer IS_BANNER = 1;

    /**
     * 是不是建议
     */
    public static final Integer IS_ADVICE = 1;

    /**
     * 用户缓存
     */
    public static final String ACCOUNT_CACHE = "account:cache";
    /**
     * 弹幕
     */
    public static final String DANMU_CACHE = "danmu:cache";
    /**
     * 文件缓存
     */
    public static final String FILE_CACHE = "file:cache";
    /**
     * 标签缓存
     */
    public static final String TAG_CACHE = "tag:cache";
    /**
     * 待办缓存
     */
    public static final String TODO_CACHE = "todo:cache";
    /**
     * 视频缓存
     */
    public static final String VIDEO_CACHE = "video:cache";

}
