package com.campus_macket.campus_market_backend.Server.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.campus_macket.campus_market_backend.DO.RawDataDO;
import com.campus_macket.campus_market_backend.DO.WechatUserDO;
import com.campus_macket.campus_market_backend.DTO.WechatLoginRequestDTO;
import com.campus_macket.campus_market_backend.Server.WechatService;
import com.campus_macket.campus_market_backend.Utils.HttpClientUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class WechatServiceImpl implements WechatService {
    private static final String REQUEST_URL = "https://api.weixin.qq.com/sns/jscode2session";
    private static final String code = "authorization_code";

    @Override
    public Map<String, Object> getUserInfoMap(WechatLoginRequestDTO loginRequest) throws Exception {
        Map<String, Object> userInfoMap = new HashMap<>();
        // logger报错的话，删掉就好，或者替换为自己的日志对象
        logger.info("Start get SessionKey，loginRequest的数据为：" + JSONObject.toJSONString(loginRequest));
        JSONObject sessionKeyOpenId = getSessionKeyOrOpenId(loginRequest.getCode());

        // 获取openId && sessionKey
        String openId = sessionKeyOpenId.getString("openid");

        String sessionKey = sessionKeyOpenId.getString("session_key");
        WechatUserDO insertOrUpdateDO = buildWechatUserDO(loginRequest, sessionKey, openId);

        // 根据code保存openId和sessionKey
        JSONObject sessionObj = new JSONObject();
        sessionObj.put("openId", openId);
        sessionObj.put("sessionKey", sessionKey);

//        // 这里的set方法，自行导入自己项目的Redis，key自行替换，这里10表示10天
//        stringJedisClientTem.set(WechatRedisPrefixConstant.USER_OPPEN_ID_AND_SESSION_KEY_PREFIX + loginRequest.getCode(),
//                sessionObj.toJSONString(), 10, TimeUnit.DAYS);

        // 根据openid查询用户，这里的查询service自己写，就不贴出来了
        WechatUserDO user = wechatUserService.getByOpenId(openId);
        if (user == null) {
            // 用户不存在，insert用户，这里加了个分布式锁，防止insert重复用户，看自己的业务，决定要不要这段代码
            if (setLock(WechatRedisPrefixConstant.INSERT_USER_DISTRIBUTED_LOCK_PREFIX + openId, "1", 10)) {
                // 用户入库，service自己写
                insertOrUpdateDO.setToken(getToken());
                wechatUserService.save(insertOrUpdateDO);
                userInfoMap.put("token", insertOrUpdateDO.getToken());
            }
        } else {
            userInfoMap.put("token", wechatUser.getToken());
            // 已存在，做已存在的处理，如更新用户的头像，昵称等，根据openID更新，这里代码自己写
            wechatUserService.updateByOpenId(insertOrUpdateDO);
        }

        return userInfoMap;
    }

    // 这里的JSONObject是阿里的fastjson，自行maven导入
    private JSONObject getSessionKeyOrOpenId(String code) throws Exception {
        Map<String, String> requestUrlParam = new HashMap<>();
        // 小程序appId，自己补充
        requestUrlParam.put("appid", APPID);
        // 小程序secret，自己补充
        requestUrlParam.put("secret", SECRET);
        // 小程序端返回的code
        requestUrlParam.put("js_code", code);
        // 默认参数
        requestUrlParam.put("grant_type", GRANT_TYPE);

        // 发送post请求读取调用微信接口获取openid用户唯一标识
        String result = HttpClientUtils.doPost(REQUEST_URL, requestUrlParam);
        return JSON.parseObject(result);
    }

    private WechatUserDO buildWechatUserAuthInfoDO(WechatLoginRequestDTO loginRequest, String sessionKey, String openId){
        WechatUserDO wechatUserDO = new WechatUserDO();
        wechatUserDO.setOpenId(openId);

        if (loginRequest.getRawData() != null) {
            RawDataDO rawDataDO = JSON.parseObject(loginRequest.getRawData(), RawDataDO.class);
            wechatUserDO.setNickname(rawDataDO.getNickName());
            wechatUserDO.setAvatarUrl(rawDataDO.getAvatarUrl());
            wechatUserDO.setGender(rawDataDO.getGender());
            wechatUserDO.setCity(rawDataDO.getCity());
            wechatUserDO.setCountry(rawDataDO.getCountry());
            wechatUserDO.setProvince(rawDataDO.getProvince());
        }

        // 解密加密信息，获取unionID
        if (loginRequest.getEncryptedData() != null){
            JSONObject encryptedData = getEncryptedData(loginRequest.getEncryptedData(), sessionKey, loginRequest.getIv());
            if (encryptedData != null){
                String unionId = encryptedData.getString("unionId");
                wechatUserDO.setUnionId(unionId);
            }
        }

        return wechatUserDO;
    }

    private JSONObject getEncryptedData(String encryptedData, String sessionkey, String iv) {
        // 被加密的数据
        byte[] dataByte = Base64.decode(encryptedData);
        // 加密秘钥
        byte[] keyByte = Base64.decode(sessionkey);
        // 偏移量
        byte[] ivByte = Base64.decode(iv);
        try {
            // 如果密钥不足16位，那么就补足.这个if中的内容很重要
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + 1;
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, "UTF-8");
                return JSONObject.parseObject(result);
            }
        } catch (Exception e) {
            logger.error("解密加密信息报错", e.getMessage());
        }
        return null;
    }

    private boolean setLock(String key, String value, long expire) throws Exception {
        boolean result = stringJedisClientTem.setNx(key, value, expire, TimeUnit.SECONDS);
        return result;
    }

    private String getToken() throws Exception {
        // 这里自定义token生成策略，可以用UUID+sale进行MD5
        return "";
    }
}
