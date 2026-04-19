package top.srcrs.notification;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 通知工具类
 * 支持多种消息推送方式
 *
 * @author srcrs
 */
public class NotificationKit {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationKit.class);

    private NotificationKit() {
    }

    /**
     * 统一推送消息入口
     * 会尝试所有已配置的通知方式
     *
     * @param title   消息标题
     * @param content 消息内容
     */
    public static void pushMessage(String title, String content) {
        pushDingTalk(title, content);
        pushPushPlus(title, content);
        pushWeixin(title, content);
        pushServerPush(title, content);
        pushFeishu(title, content);
        pushBark(title, content);
    }

    /**
     * 钉钉Webhook推送
     */
    public static void pushDingTalk(String title, String content) {
        String webhook = NotificationConfig.getDingTalkWebhook();
        if (NotificationConfig.isEmpty(webhook)) {
            return;
        }

        try {
            URL url = new URL(webhook);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setDoOutput(true);

            JSONObject message = new JSONObject();
            message.put("msgtype", "text");

            JSONObject text = new JSONObject();
            text.put("content", title + "\n" + content);
            message.put("text", text);

            try (OutputStream os = connection.getOutputStream()) {
                os.write(message.toJSONString().getBytes(StandardCharsets.UTF_8));
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                LOGGER.info("[钉钉]: 消息推送成功!");
            } else {
                LOGGER.warn("[钉钉]: 消息推送失败, 响应码: {}", responseCode);
            }
            connection.disconnect();
        } catch (Exception e) {
            LOGGER.error("[钉钉]: 消息推送失败! 原因: {}", e.getMessage());
        }
    }

    /**
     * PushPlus推送
     */
    public static void pushPushPlus(String title, String content) {
        String token = NotificationConfig.getPushPlusToken();
        if (NotificationConfig.isEmpty(token)) {
            return;
        }

        try {
            URL url = new URL("https://www.pushplus.plus/send");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setDoOutput(true);

            JSONObject message = new JSONObject();
            message.put("token", token);
            message.put("title", title);
            message.put("content", content);
            message.put("template", "html");

            try (OutputStream os = connection.getOutputStream()) {
                os.write(message.toJSONString().getBytes(StandardCharsets.UTF_8));
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                LOGGER.info("[PushPlus]: 消息推送成功!");
            } else {
                LOGGER.warn("[PushPlus]: 消息推送失败, 响应码: {}", responseCode);
            }
            connection.disconnect();
        } catch (Exception e) {
            LOGGER.error("[PushPlus]: 消息推送失败! 原因: {}", e.getMessage());
        }
    }

    /**
     * 企业微信Webhook推送
     */
    public static void pushWeixin(String title, String content) {
        String webhook = NotificationConfig.getWeixinWebhook();
        if (NotificationConfig.isEmpty(webhook)) {
            return;
        }

        try {
            URL url = new URL(webhook);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setDoOutput(true);

            JSONObject message = new JSONObject();
            message.put("msgtype", "text");

            JSONObject text = new JSONObject();
            text.put("content", title + "\n" + content);
            message.put("text", text);

            try (OutputStream os = connection.getOutputStream()) {
                os.write(message.toJSONString().getBytes(StandardCharsets.UTF_8));
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                LOGGER.info("[企业微信]: 消息推送成功!");
            } else {
                LOGGER.warn("[企业微信]: 消息推送失败, 响应码: {}", responseCode);
            }
            connection.disconnect();
        } catch (Exception e) {
            LOGGER.error("[企业微信]: 消息推送失败! 原因: {}", e.getMessage());
        }
    }

    /**
     * Server酱推送
     */
    public static void pushServerPush(String title, String content) {
        String key = NotificationConfig.getServerPushKey();
        if (NotificationConfig.isEmpty(key)) {
            return;
        }

        try {
            URL url = new URL("https://sctapi.ftqq.com/" + key + ".send");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            connection.setDoOutput(true);

            String params = "title=" + URLEncoder.encode(title, "UTF-8") +
                    "&desp=" + URLEncoder.encode(content, "UTF-8");

            try (OutputStream os = connection.getOutputStream()) {
                os.write(params.getBytes(StandardCharsets.UTF_8));
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                LOGGER.info("[Server酱]: 消息推送成功!");
            } else {
                LOGGER.warn("[Server酱]: 消息推送失败, 响应码: {}", responseCode);
            }
            connection.disconnect();
        } catch (Exception e) {
            LOGGER.error("[Server酱]: 消息推送失败! 原因: {}", e.getMessage());
        }
    }

    /**
     * 飞书Webhook推送
     */
    public static void pushFeishu(String title, String content) {
        String webhook = NotificationConfig.getFeishuWebhook();
        if (NotificationConfig.isEmpty(webhook)) {
            return;
        }

        try {
            URL url = new URL(webhook);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setDoOutput(true);

            JSONObject message = new JSONObject();
            message.put("msg_type", "interactive");

            JSONObject card = new JSONObject();

            JSONObject header = new JSONObject();
            JSONObject titleObj = new JSONObject();
            titleObj.put("content", title);
            titleObj.put("tag", "plain_text");
            header.put("title", titleObj);
            header.put("template", "blue");
            card.put("header", header);

            JSONObject element = new JSONObject();
            element.put("tag", "markdown");
            element.put("content", content);
            element.put("text_align", "left");
            card.put("elements", new JSONObject[]{element});

            message.put("card", card);

            try (OutputStream os = connection.getOutputStream()) {
                os.write(message.toJSONString().getBytes(StandardCharsets.UTF_8));
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                LOGGER.info("[飞书]: 消息推送成功!");
            } else {
                LOGGER.warn("[飞书]: 消息推送失败, 响应码: {}", responseCode);
            }
            connection.disconnect();
        } catch (Exception e) {
            LOGGER.error("[飞书]: 消息推送失败! 原因: {}", e.getMessage());
        }
    }

    /**
     * Bark推送
     */
    public static void pushBark(String title, String content) {
        String webhook = NotificationConfig.getBarkWebhook();
        if (NotificationConfig.isEmpty(webhook)) {
            return;
        }

        try {
            String baseUrl = webhook.endsWith("/") ? webhook : webhook + "/";
            String pushUrl = baseUrl + URLEncoder.encode(title, "UTF-8") + "/" + URLEncoder.encode(content, "UTF-8");

            URL url = new URL(pushUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                LOGGER.info("[Bark]: 消息推送成功!");
            } else {
                LOGGER.warn("[Bark]: 消息推送失败, 响应码: {}", responseCode);
            }
            connection.disconnect();
        } catch (Exception e) {
            LOGGER.error("[Bark]: 消息推送失败! 原因: {}", e.getMessage());
        }
    }
}
