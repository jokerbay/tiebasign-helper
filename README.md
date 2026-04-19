<div align="center"> 
<h1 align="center">贴吧签到助手</h1>
<img src="https://img.shields.io/github/issues/srcrs/TiebaSignIn?color=green">
<img src="https://img.shields.io/github/stars/srcrs/TiebaSignIn?color=yellow">
<img src="https://img.shields.io/github/forks/srcrs/TiebaSignIn?color=orange">
<img src="https://img.shields.io/github/license/srcrs/TiebaSignIn?color=ff69b4">
<img src="https://img.shields.io/github/languages/code-size/srcrs/TiebaSignIn?color=blueviolet">
</div>

# 简介

用的是手机端的接口，签到经验更多，用户只需要填写`BDUSS`即可，每日自动帮你签到，最多支持`200`个贴吧签到。

# 功能

+ 贴吧签到(最多支持 200 个)

+ 支持多种消息推送方式（钉钉、企业微信、飞书、PushPlus、Server酱、Bark）

# 使用方法

## 1.fork本项目
### 必须检查的仓库设置

1. Settings -> Actions -> General -> Workflow permissions：选择 "Read and write permissions"（以允许 GITHUB_TOKEN push）。

2. 确保仓库没有被 Archived（Settings -> General -> Danger Zone: Archive repository）。

## 2.获取BDUSS

在网页中登录上贴吧，然后按下`F12`打开调试模式，在`cookie`中找到`BDUSS`，并复制其`Value`值。

![](./assets/获取BDUSS.gif)

## 3.将BDUSS添加到仓库的Secrets中

Name | Value
-|-
BDUSS | xxxxxxxxxxx

将上一步骤获取到的`BDUSS`粘贴到`Secrets`中

![](./assets/添加BDUSS.gif)

## 4.开启actions

默认`actions`是处于禁止的状态，需要手动开启。

![](./assets/开启actions.gif)

## 5.第一次运行actions

+ 自己提交一次`push`。

将`run.txt`中的`flag`由`0`改为`1`

```patch
- flag: 0
+ flag: 1
```

![](./assets/运行结果.gif)

## 成功了

每天早上`6:30`将会自动进行签到

---

# 消息推送配置

支持多种消息推送方式，可以同时配置多个，配置后签到结果会推送到所有已配置的渠道。

## 推送效果示例

```
百度贴吧自动签到

共 50 个贴吧
成功: 48 | 失败: 1 | 失效: 1

【签到成功】
贴吧1, 贴吧2, 贴吧3, 贴吧4, 贴吧5 ...等48个

【签到失败】
贴吧A

【失效贴吧】
贴吧X
```

## 配置方式

在 GitHub 仓库的 `Settings` -> `Secrets and variables` -> `Actions` 中添加以下配置：

### 必填配置

Name | Value | 说明
-|-|-
BDUSS | xxxxxxxxxxx | 百度贴吧Cookie（必填）

### 可选推送配置（至少配置一个）

Name | Value | 说明
-|-|-
PUSHPLUS_TOKEN | xxxxxxxxxxx | PushPlus Token，关注公众号获取
SERVERPUSHKEY | xxxxxxxxxxx | Server酱推送Key，从 https://sct.ftqq.com/ 获取
DINGDING_WEBHOOK | https://oapi.dingtalk.com/robot/send?access_token=xxx | 钉钉机器人Webhook地址
WEIXIN_WEBHOOK | https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=xxx | 企业微信机器人Webhook地址
FEISHU_WEBHOOK | https://open.feishu.cn/open-apis/bot/v2/hook/xxx | 飞书机器人Webhook地址
BARK_WEBHOOK | https://api.day.app/xxx | Bark推送地址

## 各推送方式获取方法

### PushPlus
1. 关注微信公众号「PushPlus推送加」
2. 在公众号菜单中获取 Token
3. 将 Token 填入 `PUSHPLUS_TOKEN`

### Server酱
1. 访问 https://sct.ftqq.com/
2. 使用微信扫码登录
3. 在「SendKey」页面获取 Key
4. 将 Key 填入 `SERVERPUSHKEY`

### 钉钉机器人
1. 在钉钉群组中添加自定义机器人
2. 安全设置选择「自定义关键词」，填写「签到」
3. 获取 Webhook 地址
4. 将地址填入 `DINGDING_WEBHOOK`

### 企业微信机器人
1. 在企业微信群组中添加机器人
2. 获取 Webhook 地址
3. 将地址填入 `WEIXIN_WEBHOOK`

### 飞书机器人
1. 在飞书群组中添加自定义机器人
2. 获取 Webhook 地址
3. 将地址填入 `FEISHU_WEBHOOK`

### Bark
1. 在 App Store 下载 Bark 应用
2. 打开应用获取推送地址
3. 将地址填入 `BARK_WEBHOOK`

---

## 注意事项

> ⚠️ **重要提示**: 原有的 `SCKEY` 变量已废弃，请改用 `PUSHPLUS_TOKEN` 或其他推送方式。
