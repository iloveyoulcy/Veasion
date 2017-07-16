package veasion.util.face;

import java.util.HashMap;

/**
 * FaceSet相关的操作 
 */
public class FaceSetOperate {

    private String apiKey = "";
    private String apiSecret = "";
    private String webUrl;

    /**
     *
     * @param apiKey
     * @param apiSecret
     * @param isInternationalVersion 是否是使用国际版
     *                               is use international version
     */
    public FaceSetOperate(String apiKey, String apiSecret, boolean isInternationalVersion){
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
        if(isInternationalVersion){
            webUrl = Key.WEB_BASE_INTERNATIONAL;
        }else{
            webUrl = Key.WEB_BASE;
        }
    }

    /**
     *  为一个已经创建的FaceSet添加人脸标识face_token。一个FaceSet最多存储1,000个face_token。
     * @param faceTokens 人脸标识face_token组成的字符串，可以是一个或者多个，用逗号分隔。最多不超过5个face_token
     * @param faceSetToken FaceSet的标识
     * @return
     * @throws Exception
     */
    public FaceResponse addFaceByFaceToken(String faceTokens, String faceSetToken) throws Exception {
        String url = webUrl + Key.SPLIT + Key.FACESET + Key.SPLIT + Key.ADDFACE;
        HashMap<String, String> map = new HashMap<>();
        map.put(Key.KEY_FOR_APIKEY, apiKey);
        map.put(Key.KEY_FOR_APISECRET, apiSecret);
        map.put(Key.KEY_FOR_FACE_TOKENS, faceTokens);
        map.put(Key.KEY_FOR_FACESET_TOKEN, faceSetToken);
        return HttpRequest.post(url, map, null);
    }

    /**
     *  为一个已经创建的FaceSet添加人脸标识face_token。一个FaceSet最多存储1,000个face_token。
     * @param FaceTokens 人脸标识face_token组成的字符串，可以是一个或者多个，用逗号分隔。最多不超过5个face_token
     * @param outerId 用户提供的FaceSet标识
     * @return
     * @throws Exception
     */
    public FaceResponse addFaceByOuterId(String FaceTokens, String outerId) throws Exception {
        String url = webUrl + Key.SPLIT + Key.FACESET + Key.SPLIT + Key.ADDFACE;
        HashMap<String, String> map = new HashMap<>();
        map.put(Key.KEY_FOR_APIKEY, apiKey);
        map.put(Key.KEY_FOR_APISECRET, apiSecret);
        map.put(Key.KEY_FOR_FACE_TOKENS, FaceTokens);
        map.put(Key.KEY_FOR_OUTER_ID, outerId);
        return HttpRequest.post(url, map, null);
    }

    /**
     *  创建一个人脸的集合FaceSet，用于存储人脸标识face_token。一个FaceSet能够存储1,000个face_token。
     * @param displayName 人脸集合的名字，256个字符，不能包括字符^@,&=*'"
     * @param outerId 账号下全局唯一的FaceSet自定义标识，可以用来管理FaceSet对象。最长255个字符，不能包括字符^@,&=*'"
     * @param tags FaceSet自定义标签组成的字符串，用来对FaceSet分组。最长255个字符，多个tag用逗号分隔，
     *             每个tag不能包括字符^@,&=*'"
     * @param FaceTokens 人脸标识face_token，可以是一个或者多个，用逗号分隔。最多不超过5个face_token
     * @param userData 自定义用户信息，不大于16KB，不能包括字符^@,&=*'"
     * @param ForceMerge 在传入outer_id的情况下，如果outer_id已经存在，是否将face_token加入已经存在的FaceSet中
     *                   0：不将face_tokens加入已存在的FaceSet中，直接返回FACESET_EXIST错误
     *                   1：将face_tokens加入已存在的FaceSet中
     *                   默认值为0
     * @return
     * @throws Exception
     */
    public FaceResponse createFaceSet(String displayName, String outerId, String tags, String FaceTokens, String userData, int ForceMerge) throws Exception {
        String url = webUrl + Key.SPLIT + Key.FACESET + Key.SPLIT + Key.CREATE;
        HashMap<String, String> map = new HashMap<>();
        map.put(Key.KEY_FOR_APIKEY, apiKey);
        map.put(Key.KEY_FOR_APISECRET, apiSecret);
        if(!HttpRequest.isEmpty(displayName)){
            map.put(Key.KEY_FOR_DISPLAY_NAME, displayName);
        }
        if(!HttpRequest.isEmpty(outerId)){
            map.put(Key.KEY_FOR_OUTER_ID, outerId);
        }
        if(!HttpRequest.isEmpty(tags)){
            map.put(Key.KEY_FOR_TAGS, tags);
        }
        if(!HttpRequest.isEmpty(FaceTokens)){
            map.put(Key.KEY_FOR_FACE_TOKENS, FaceTokens);
        }
        if(!HttpRequest.isEmpty(userData)){
            map.put(Key.KEY_FOR_USER_DATA, userData);
        }
        map.put(Key.KEY_FOR_FORCE_MERGE, String.valueOf(ForceMerge));
        return HttpRequest.post(url, map, null);
    }

    /**
     * 删除一个人脸集合。
     * @param faceSetToken FaceSet的标识
     * @param checkEmpty 删除时是否检查FaceSet中是否存在face_token，默认值为1
     *                   0：不检查
     *                   1：检查
     *                   如果设置为1，当FaceSet中存在face_token则不能删除
     * @return
     * @throws Exception
     */
    public FaceResponse deleteFaceSetByToken(String faceSetToken, int checkEmpty) throws Exception {
        String url = webUrl + Key.SPLIT + Key.FACESET + Key.SPLIT + Key.DELETE;
        HashMap<String, String> map = new HashMap<>();
        map.put(Key.KEY_FOR_APIKEY, apiKey);
        map.put(Key.KEY_FOR_APISECRET, apiSecret);
        map.put(Key.KEY_FOR_FACESET_TOKEN, faceSetToken);
        map.put(Key.KEY_FOR_CHECK_EMPTY, String.valueOf(checkEmpty));
        return HttpRequest.post(url, map, null);
    }

    /**
     * 删除一个人脸集合。
     * @param outerId 用户提供的FaceSet标识
     * @param checkEmpty 删除时是否检查FaceSet中是否存在face_token，默认值为1
     *                   0：不检查
     *                   1：检查
     *                   如果设置为1，当FaceSet中存在face_token则不能删除
     * @return 返回结果,Response实例
     * @throws Exception
     */
    public FaceResponse deleteFaceSetByOuterId(String outerId, int checkEmpty) throws Exception {
        String url = webUrl + Key.SPLIT + Key.FACESET + Key.SPLIT + Key.DELETE;
        HashMap<String, String> map = new HashMap<>();
        map.put(Key.KEY_FOR_APIKEY, apiKey);
        map.put(Key.KEY_FOR_APISECRET, apiSecret);
        map.put(Key.KEY_FOR_OUTER_ID, outerId);
        map.put(Key.KEY_FOR_CHECK_EMPTY, String.valueOf(checkEmpty));
        return HttpRequest.post(url, map, null);
    }

    /**
     *  获取一个FaceSet的所有信息
     * @param faceSetToken FaceSet的标识
     * @return
     * @throws Exception
     */
    public FaceResponse getDetailByFaceToken(String faceSetToken) throws Exception {
        String url = webUrl + Key.SPLIT + Key.FACESET + Key.SPLIT + Key.GET_DETAIL;
        HashMap<String, String> map = new HashMap<>();
        map.put(Key.KEY_FOR_APIKEY, apiKey);
        map.put(Key.KEY_FOR_APISECRET, apiSecret);
        map.put(Key.KEY_FOR_FACESET_TOKEN, faceSetToken);
        return HttpRequest.post(url, map, null);
    }

    /**
     *  获取一个FaceSet的所有信息
     * @param outerId 用户提供的FaceSet标识
     * @return
     * @throws Exception
     */
    public FaceResponse getDetailByOuterId(String outerId) throws Exception {
        String url = webUrl + Key.SPLIT + Key.FACESET + Key.SPLIT + Key.GET_DETAIL;
        HashMap<String, String> map = new HashMap<>();
        map.put(Key.KEY_FOR_APIKEY, apiKey);
        map.put(Key.KEY_FOR_APISECRET, apiSecret);
        map.put(Key.KEY_FOR_OUTER_ID, outerId);
        return HttpRequest.post(url, map, null);
    }

    /**
     * 移除一个FaceSet中的某些或者全部face_token
     * @param faceSetToken FaceSet的标识
     * @param faceTokens 需要移除的人脸标识字符串，可以是一个或者多个face_token组成，用逗号分隔。最多不能超过1,000个face_token
     *                   注：face_tokens字符串传入“RemoveAllFaceTokens”则会移除FaceSet内所有的face_token
     * @return
     * @throws Exception
     */
    public FaceResponse removeFaceFromFaceSetByFaceSetToken(String faceSetToken, String faceTokens) throws Exception {
        String url = webUrl + Key.SPLIT + Key.FACESET + Key.SPLIT + Key.REMOVE_FACE;
        HashMap<String, String> map = new HashMap<>();
        map.put(Key.KEY_FOR_APIKEY, apiKey);
        map.put(Key.KEY_FOR_APISECRET, apiSecret);
        map.put(Key.KEY_FOR_FACESET_TOKEN, faceSetToken);
        map.put(Key.KEY_FOR_FACE_TOKENS, faceTokens);
        return HttpRequest.post(url, map, null);
    }

    /**
     * 移除一个FaceSet中的某些或者全部face_token
     * @param outerId 用户自定义的FaceSet标识
     * @param faceTokens 需要移除的人脸标识字符串，可以是一个或者多个face_token组成，用逗号分隔。最多不能超过1,000个face_token
     *                   注：face_tokens字符串传入“RemoveAllFaceTokens”则会移除FaceSet内所有的face_token
     * @return
     * @throws Exception
     */
    public FaceResponse removeFaceFromFaceSetByOuterId(String outerId, String faceTokens) throws Exception {
        String url = webUrl + Key.SPLIT + Key.FACESET + Key.SPLIT + Key.REMOVE_FACE;
        HashMap<String, String> map = new HashMap<>();
        map.put(Key.KEY_FOR_APIKEY, apiKey);
        map.put(Key.KEY_FOR_APISECRET, apiSecret);
        map.put(Key.KEY_FOR_OUTER_ID, outerId);
        map.put(Key.KEY_FOR_FACE_TOKENS, faceTokens);
        return HttpRequest.post(url, map, null);
    }

    /**
     * 获取所有的FaceSet
     * @param tags 包含需要查询的FaceSet标签的字符串，用逗号分隔
     * @return
     * @throws Exception
     */
    public FaceResponse getFaceSets(String tags) throws Exception {
        String url = webUrl + Key.SPLIT + Key.FACESET + Key.SPLIT + Key.GET_FACESETS;
        HashMap<String, String> map = new HashMap<>();
        map.put(Key.KEY_FOR_APIKEY, apiKey);
        map.put(Key.KEY_FOR_APISECRET, apiSecret);
        map.put(Key.KEY_FOR_TAGS, tags);
        return HttpRequest.post(url, map, null);
    }

    /**
     * 更新一个人脸集合的属性
     * @param faceSetToken FaceSet的标识
     * @param newOuterId 在api_key下全局唯一的FaceSet自定义标识，可以用来管理FaceSet对象。最长255个字符，不能包括字符^@,&=*'"
     * @param displayName 人脸集合的名字，256个字符
     * @param userData 自定义用户信息，不大于16KB, 1KB=1024B
     * @param tags FaceSet自定义标签组成的字符串，用来对FaceSet分组。最长255个字符，多个tag用逗号分隔，每个tag不能包括字符^@,&=*'"
     * @return
     * @throws Exception
     */
    public FaceResponse updateFaceSetByFaceSetToken(String faceSetToken, String newOuterId, String displayName, String userData, String tags) throws Exception {
        String url = webUrl + Key.SPLIT + Key.FACESET + Key.SPLIT + Key.UPDATE;
        HashMap<String, String> map = new HashMap<>();
        map.put(Key.KEY_FOR_APIKEY, apiKey);
        map.put(Key.KEY_FOR_APISECRET, apiSecret);
        map.put(Key.KEY_FOR_FACESET_TOKEN, faceSetToken);
        map.put(Key.KEY_FOR_NEW_OUTER_ID, newOuterId);
        map.put(Key.KEY_FOR_DISPLAY_NAME, displayName);
        map.put(Key.KEY_FOR_USER_DATA, userData);
        map.put(Key.KEY_FOR_TAGS, tags);
        return HttpRequest.post(url, map, null);
    }

    /**
     * 更新一个人脸集合的属性
     * @param outerId 用户自定义的FaceSet标识
     * @param newOuterId 在api_key下全局唯一的FaceSet自定义标识，可以用来管理FaceSet对象。最长255个字符，不能包括字符^@,&=*'"
     * @param displayName 人脸集合的名字，256个字符
     * @param userData 自定义用户信息，不大于16KB, 1KB=1024B
     * @param tags FaceSet自定义标签组成的字符串，用来对FaceSet分组。最长255个字符，多个tag用逗号分隔，每个tag不能包括字符^@,&=*'"
     * @return
     * @throws Exception
     */
    public FaceResponse updateFaceSetByOuterId(String outerId, String newOuterId, String displayName, String userData, String tags) throws Exception {
        String url = webUrl + Key.SPLIT + Key.FACESET + Key.SPLIT + Key.UPDATE;
        HashMap<String, String> map = new HashMap<>();
        map.put(Key.KEY_FOR_APIKEY, apiKey);
        map.put(Key.KEY_FOR_APISECRET, apiSecret);
        map.put(Key.KEY_FOR_OUTER_ID, outerId);
        map.put(Key.KEY_FOR_NEW_OUTER_ID, newOuterId);
        map.put(Key.KEY_FOR_DISPLAY_NAME, displayName);
        map.put(Key.KEY_FOR_USER_DATA, userData);
        map.put(Key.KEY_FOR_TAGS, tags);
        return HttpRequest.post(url, map, null);
    }

}
