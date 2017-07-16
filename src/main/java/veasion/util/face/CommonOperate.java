package veasion.util.face;

import java.util.HashMap;

/**
 * 封装了detect，compare，search这三个接口<br>
 * 这个类里的所有方法都是网络请求，所以请在异步线程中调用
 */
public class CommonOperate {

    private String apiKey = "";
    private String apiSecret = "";
    private String webUrl;

    /**
     *
     * @param apiKey
     * @param apiSecret
     * @param isInternationalVersion 是否是使用国际版
     */
    public CommonOperate(String apiKey, String apiSecret, boolean isInternationalVersion){
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
        if(isInternationalVersion){
            webUrl = Key.WEB_BASE_INTERNATIONAL;
        }else{
            webUrl = Key.WEB_BASE;
        }
    }


    /**
     * 这是一个网络请求的方法，传入不同的参数和API接口，可以请求不同的API
     * @param url API接口
     * @param map 字符串参数
     * @param fileByte 文件参数
     * @return
     * @throws Exception
     */
    public static FaceResponse postTo(String url, HashMap<String, String> map, HashMap<String, byte[]> fileByte) throws Exception {
        return HttpRequest.post(url, map, fileByte);
    }


    /**
     * 调用者提供图片URL，进行人脸检测。
     * @param imageUrl 图片链接 image url
     * @param landmark 是否返回人脸的关键点，1：返回，0：不返回
     * @param attributes 检测人脸的属性 gender,age,smiling,glass,headpose,facequality,blur
     * @return
     * @throws Exception
     */
    public FaceResponse detectUrl(String imageUrl, int landmark, String attributes) throws Exception {
        String url = webUrl + Key.SPLIT + Key.DETECT;
        HashMap<String, String> map = new HashMap<>();
        map.put(Key.KEY_FOR_APIKEY, apiKey);
        map.put(Key.KEY_FOR_APISECRET, apiSecret);
        map.put(Key.KEY_FOR_IMAGE_URL, imageUrl);
        map.put(Key.KEY_FOR_RETURN_LANDMARK, String.valueOf(landmark));
        if(!HttpRequest.isEmpty(attributes)){
            map.put(Key.KEY_FOR_RETURN_ATTRIBUTES, attributes);
        }
        return HttpRequest.post(url, map, null);
    }

    /**
     * 调用者提供图片文件，进行人脸检测。
     * @param fileByte 二进制数组 image binary array
     * @param landmark 是否返回人脸的关键点，1：返回，0：不返回
     * @param attributes 检测人脸的属性 gender,age,smiling,glass,headpose,facequality,blur
     * @return
     * @throws Exception
     */
    public FaceResponse detectByte(byte[] fileByte, int landmark, String attributes) throws Exception {
        String url = webUrl + Key.SPLIT + Key.DETECT;
        HashMap<String, String> map = new HashMap<>();
        HashMap<String, byte[]> fileMap = new HashMap<>();
        map.put(Key.KEY_FOR_APIKEY, apiKey);
        map.put(Key.KEY_FOR_APISECRET, apiSecret);
        fileMap.put(Key.KEY_FOR_IMAGE_FILE, fileByte);
        if(landmark != 0){
            map.put(Key.KEY_FOR_RETURN_LANDMARK, String.valueOf(landmark));
        }
        if(!HttpRequest.isEmpty(attributes)){
            map.put(Key.KEY_FOR_RETURN_ATTRIBUTES, attributes);
        }
        return HttpRequest.post(url, map, fileMap);
    }

    /**
     * 调用者提供图片文件，进行人脸检测。
     * @param base64 Base64数据 image data for base64
     * @param landmark 是否返回人脸的关键点，1：返回，0：不返回
     * @param attributes 检测人脸的属性 gender,age,smiling,glass,headpose,facequality,blur
     * @return
     * @throws Exception
     */
    public FaceResponse detectBase64(String base64, int landmark, String attributes) throws Exception {
        String url = webUrl + Key.SPLIT + Key.DETECT;
        HashMap<String, String> map = new HashMap<>();
        map.put(Key.KEY_FOR_APIKEY, apiKey);
        map.put(Key.KEY_FOR_APISECRET, apiSecret);
        map.put(Key.KEY_FOR_IMAGE_BASE64, base64);
        if(landmark != 0){
            map.put(Key.KEY_FOR_RETURN_LANDMARK, String.valueOf(landmark));
        }
        if(!HttpRequest.isEmpty(attributes)){
            map.put(Key.KEY_FOR_RETURN_ATTRIBUTES, attributes);
        }
        return HttpRequest.post(url, map, null);
    }

    /**
     * 将两个人脸进行比对，来判断是否为同一个人。
     * compare two faces
     * @param faceToken1 第一个人脸标识face_token
     * @param image_url1 第一个人脸的url
     * @param image_url1 第一个人脸的图片url 三个参数只需要传一个就行了
     * @param fileByte1  第一个人脸的图片文件 三个参数只需要传一个就行了
     * @param base64_1   第一个人脸的图片base64 三个参数只需要传一个就行了
     * @param faceToken2 第二个人脸标识face_token
     * @param image_url2 第二个人脸的url 三个参数只需要传一个就行了
     * @param fileByte2  第二个人脸的图片文件 三个参数只需要传一个就行了
     * @param base64_2   第二个人脸的图片base64 三个参数只需要传一个就行了
     * @return
     * @throws Exception
     */
    public FaceResponse compare(String faceToken1, String image_url1, byte[] fileByte1, String base64_1,
                          String faceToken2, String image_url2, byte[] fileByte2, String base64_2) throws Exception {
        String url = webUrl + Key.SPLIT + Key.COMPARE;
        HashMap<String, String> map = new HashMap<>();
        HashMap<String, byte[]> fileByte = new HashMap<>();
        map.put(Key.KEY_FOR_APIKEY, apiKey);
        map.put(Key.KEY_FOR_APISECRET, apiSecret);
        if(!HttpRequest.isEmpty(faceToken1)){
            map.put(Key.KEY_FOR_FACE_TOKEN1, faceToken1);
        }
        if(!HttpRequest.isEmpty(faceToken2)){
            map.put(Key.KEY_FOR_FACE_TOKEN2, faceToken2);
        }
        if(!HttpRequest.isEmpty(image_url1)){
            map.put(Key.KEY_FOR_IMAGE_URL1, image_url1);
        }
        if(!HttpRequest.isEmpty(image_url2)){
            map.put(Key.KEY_FOR_IMAGE_URL2, image_url2);
        }
        if(fileByte1 != null){
            fileByte.put(Key.KEY_FOR_IMAGE_FILE1, fileByte1);
        }
        if(fileByte2 != null){
            fileByte.put(Key.KEY_FOR_IMAGE_FILE2, fileByte2);
        }
        if(!HttpRequest.isEmpty(base64_1)){
            map.put(Key.KEY_FOR_IMAGE_BASE64_1, base64_1);
        }
        if(!HttpRequest.isEmpty(base64_2)){
            map.put(Key.KEY_FOR_IMAGE_BASE64_2, base64_2);
        }
        return HttpRequest.post(url, map, fileByte);
    }

    /**
     *  在Faceset中找出与目标人脸最相似的一张或多张人脸。<br>
     *  faceToken,image_url,buff四个参数只要传入一个就可以了，其他可以传空（null）
     * @param faceToken 与Faceset中人脸比对的face_token
     * @param image_url 需要比对的人脸的网络图片URL
     * @param buff 需要比对的人脸的图片的二进制数组
     * @param faceSetToken Faceset的标识
     * @param returnResultCount 返回比对置信度最高的n个结果，范围[1,5]。默认值为1
     * @return
     * @throws Exception
     */
    public FaceResponse searchByFaceSetToken(String faceToken, String image_url, byte[] buff, String faceSetToken, int returnResultCount) throws Exception {
        String url = webUrl + Key.SPLIT + Key.SEARCH;
        HashMap<String, String> map = new HashMap<>();
        HashMap<String, byte[]> fileMap = new HashMap<>();
        map.put(Key.KEY_FOR_APIKEY, apiKey);
        map.put(Key.KEY_FOR_APISECRET, apiSecret);
        map.put(Key.KEY_FOR_FACESET_TOKEN, faceSetToken);
        map.put(Key.KEY_FOR_RETURN_RESULT_COUNT, String.valueOf(returnResultCount));
        if(faceToken != null){
            map.put(Key.KEY_FOR_FACE_TOKEN, faceToken);
        }
        if(image_url != null){
            map.put(Key.KEY_FOR_IMAGE_URL, image_url);
        }
        if(buff != null){
            fileMap.put(Key.KEY_FOR_IMAGE_FILE, buff);
        }
        return HttpRequest.post(url, map, fileMap);
    }

    /**
     *  在Faceset中找出与目标人脸最相似的一张或多张人脸。<br>
     *  faceToken,image_url,image_file,buff四个参数只要传入一个就可以了，其他可以传空（null）
     * @param faceToken 与Faceset中人脸比对的face_token
     * @param image_url 需要比对的人脸的网络图片URL
     * @param buff 需要比对的人脸的图片的二进制数组
     * @param outerId Faceset的标识
     * @param returnResultCount 返回比对置信度最高的n个结果，范围[1,5]。默认值为1
     * @return
     * @throws Exception
     */
    public FaceResponse searchByOuterId(String faceToken, String image_url, byte[] buff, String outerId, int returnResultCount) throws Exception {
        String url = webUrl + Key.SPLIT + Key.SEARCH;
        HashMap<String, String> map = new HashMap<>();
        HashMap<String, byte[]> fileMap = new HashMap<>();
        map.put(Key.KEY_FOR_APIKEY, apiKey);
        map.put(Key.KEY_FOR_APISECRET, apiSecret);
        map.put(Key.KEY_FOR_OUTER_ID, outerId);
        map.put(Key.KEY_FOR_RETURN_RESULT_COUNT, String.valueOf(returnResultCount));
        if(faceToken != null){
            map.put(Key.KEY_FOR_FACE_TOKEN, faceToken);
        }
        if(image_url != null){
            map.put(Key.KEY_FOR_IMAGE_URL, image_url);
        }
        if(buff != null){
            fileMap.put(Key.KEY_FOR_IMAGE_FILE, buff);
        }
        return HttpRequest.post(url, map, fileMap);
    }

}
