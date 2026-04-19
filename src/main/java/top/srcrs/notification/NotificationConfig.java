package top.srcrs.notification;

/**
 * 通知配置类
 * 从环境变量读取各种通知方式的配置
 *
 * @author srcrs
 */
public class NotificationConfig {

    private NotificationConfig() {
    }

    /**
     * 获取钉钉Webhook地址
     */
    public static String getDingTalkWebhook() {
        return System.getenv("DINGDING_WEBHOOK");
    }

    /**
     * 获取PushPlus Token
     */
    public static String getPushPlusToken() {
        return System.getenv("PUSHPLUS_TOKEN");
    }

    /**
     * 获取企业微信Webhook地址
     */
    public static String getWeixinWebhook() {
        return System.getenv("WEIXIN_WEBHOOK");
    }

    /**
     * 获取Server酱推送Key
     */
    public static String getServerPushKey() {
        return System.getenv("SERVERPUSHKEY");
    }

    /**
     * 获取飞书Webhook地址
     */
    public static String getFeishuWebhook() {
        return System.getenv("FEISHU_WEBHOOK");
    }

    /**
     * 获取Bark推送地址
     */
    public static String getBarkWebhook() {
        return System.getenv("BARK_WEBHOOK");
    }

    /**
     * 检查字符串是否为空
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
}
