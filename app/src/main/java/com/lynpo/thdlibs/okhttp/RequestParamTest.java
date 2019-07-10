package com.lynpo.thdlibs.okhttp;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import androidx.collection.SparseArrayCompat;
import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Create by fujw on 2018/10/25.
 * *
 * RequestParamTest
 */
public class RequestParamTest {

    public static void main(String[] args) {
        Request request = new Request.Builder()
                .url("http://www.hihamlet.com")
                .get()
                .addHeader("content-type", "application/json")
                .build();

        HttpUrl url = request.url().newBuilder()
                .addQueryParameter("id", "1")
                .addQueryParameter("id", "2")
                .addQueryParameter("id", "3")
                .addQueryParameter("ida", "4")
                .addQueryParameter("name", "lynpo")
                .build();
        request = request.newBuilder().url(url).build();

        String mc_sign = getMcSign(request, System.currentTimeMillis() + "");
        String mc_sign_sha1 = getSha1(mc_sign);
        System.out.println("mc_sign:" + mc_sign);
        System.out.println("mc_sign_sha1:" + mc_sign_sha1);
    }

    /**
     * 获取签名参数 mc_sign
     * 签名方式，mc_sign 的计算方式是：
     * 将除了 mc_sign 之外的 queryParams 中所有参数加上 appSignKey（版本密钥） 后，
     * 按 key 的正序排序后，以 key1=value1&key2=value2 结构拼接，使用SHA1加密
     * @param request the request
     * @param timeStamp timeStamp
     * @return calculated mc_sign
     */
    private static String getMcSign(Request request, String timeStamp) {
        Map<String, String> kValForMcSign = new HashMap<>();
        SparseArrayCompat<String> paramIndexKeyArray = null;
        // 拼接 appSignKey
        kValForMcSign.put("appSignKey", "fdJ940-1}P]f[eQg");
        // 拼接公共参数
        kValForMcSign.put("timestamp", timeStamp);
        // 获取并拼接请求参数 queryParams
        HttpUrl url = request.url().newBuilder().build();
        int qs = url.querySize();
        for (int i = 0; i < qs; i++) {
            String name = url.queryParameterName(i);
            String value = url.queryParameterValue(i);
            String nVPair = name + "=" + value;
            if (!kValForMcSign.containsKey(name)) {
                kValForMcSign.put(name, value);
            } else {
                if (paramIndexKeyArray == null) {
                    paramIndexKeyArray = new SparseArrayCompat<>();
                }
                paramIndexKeyArray.put(i, nVPair);
            }
        }

        Set<String> keys = kValForMcSign.keySet();
        Object[] queryKeys = keys.toArray();
        // 参数按 key 排序
        Arrays.sort(queryKeys, (Comparator) (o1, o2) -> (o1 instanceof String && o2 instanceof String) ? ((String) o1).compareTo(((String) o2)) : 0);

        StringBuilder sb = new StringBuilder();
        boolean first = true;
        int size = 0;
        boolean hasDuplicateKey = paramIndexKeyArray != null && ((size = paramIndexKeyArray.size()) > 0);
        for (Object key : queryKeys) {
            if (!(key instanceof String)) {
                continue;
            }
            if (!first) {
                sb.append("&");
            }
            first = false;
            sb.append(key).append("=").append(kValForMcSign.get(key));
            if (hasDuplicateKey) {
                for (int i = 0; i < size; i++) {
                    String nVPair = paramIndexKeyArray.get(paramIndexKeyArray.keyAt(i));
                    if (nVPair != null && nVPair.startsWith(key + "=")) {
                        sb.append("&").append(nVPair);
                    }
                }
            }
        }

//        return getSha1(sb.toString());
        return sb.toString();
    }

    public static String getSha1(String input) {
        try {
            MessageDigest digest = java.security.MessageDigest.getInstance("SHA-1");
            digest.update(input.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            // 字节数组转换为十六进制数
            for (byte aMessageDigest : messageDigest) {
                String shaHex = Integer.toHexString(aMessageDigest & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException ignored) {

        }
        return "";
    }
}
