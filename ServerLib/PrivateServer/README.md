#### 目录介绍
- 01.基础概念介绍
- 02.常见思路和做法
- 03.Api调用说明
- 04.测试case罗列
- 05.遇到的坑分析




### 01.基础概念介绍
#### 1.1 什么叫敏感信息
- 敏感设备信息获取是指只要调用系统API就会认为获取敏感信息，并不关心有没有获取到敏感信息以及调用系统API的目的。
  
  
#### 1.2 主要原则
- 没有权限不能调用系统API，如无READ_PHONE_STATE权限调用掉用getImei()、getDeviceId()等API
- 控制调用频次，即使有权限不能频繁调用系统API
- 控制传输频次，不能频繁传输敏感信息




### 02.常见思路和做法
#### 2.1 控频方案
- App自身系统隐私API调用，统一调用基础库，该库统一做内存级别缓存，不重复调用系统API。
- 传输控频，主要有2种方案：敏感信息统一传送一次，各业务单独对接，业务见相互依赖强；数据统一整包加密


#### 2.2 敏感信息有那些
- imei(IMEI)，android_id(Android唯一标识符)，provider_name(手机运营商)，operator_id(卡运营商id)，sn(sn设备号)等等



#### 2.3 具体代码思路
- 举一个简单例子【利用缓存，避免频繁调用api获取敏感信息】
    ``` java
    public static String getAndroidId(@NonNull Context context, String defaultValue) {
        if (null != Cache.ANDROID_ID) {
            return Cache.ANDROID_ID;
        }
        String androidId = SafePrivateHelper.getAndroidId();
        if (!TextUtils.isEmpty(androidId)) {
            Cache.ANDROID_ID = androidId;
        } else {
            androidId = defaultValue;
        }
        return androidId;
    }
    ```
- 然后看一下获取AndroidId具体方法做了什么？
    ``` java
    protected static String getAndroidId() {
        if(!UserPrivacyHolder.isInitUserPrivacy()){
            return "";
        }
        return AndroidIdOnceGetter.ANDROID_ID;
    }
    
    private static class AndroidIdOnceGetter {
        @NonNull
        private static final String ANDROID_ID = SafeGet();
    
        @SuppressLint("HardwareIds")
        @NonNull
        private static String SafeGet() {
            try {
                Context context = UserPrivacyHolder.getContext();
                return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            } catch (Throwable ignored) {
            }
            return "";
        }
    }
    ```




### 03.Api调用说明



### 04.测试case罗列



### 05.遇到的坑分析









































