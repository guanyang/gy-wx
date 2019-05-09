package org.gy.framework.util.weixin.api.media;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.IOUtils;

public class MediaUpload {

    private static final Logger LOGGER           = LoggerFactory.getLogger(MediaUpload.class);

    public static final String  UPLOAD_MEDIA_PATTERN = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token={0}&type={1}";

    private MediaUpload() {

    }

    /**
     * 上传素材
     * 
     * @param accessToken
     * @param file 上传的文件
     */
    public static String uploadMedia(String accessToken,
                                     File file) {

        InputStream is = null;
        DataInputStream in = null;
        OutputStream out = null;
        try {
            // 拼装请求地址
            String mediaUrl = MessageFormat.format(UPLOAD_MEDIA_PATTERN, accessToken, "image");

            URL url = new URL(mediaUrl);
            long filelength = file.length();
            String fileName = file.getName();
            /**
             * 你们需要在这里根据文件后缀suffix将type的值设置成对应的mime类型的值
             */
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setUseCaches(false); // post方式不能使用缓存
            // 设置请求头信息
            con.setRequestProperty("Connection", "Keep-Alive");
            con.setRequestProperty("Charset", "UTF-8");

            // 设置边界,这里的boundary是http协议里面的分割符，不懂的可惜百度(http 协议 boundary)，这里boundary 可以是任意的值(111,2222)都行
            String boundary = "----------" + System.currentTimeMillis();
            con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            // 请求正文信息
            // 第一部分：

            StringBuilder sb = new StringBuilder();

            sb.append("--"); // 必须多两道线
            sb.append(boundary);
            sb.append("\r\n");
            // 这里是media参数相关的信息，这里是否能分开下我没有试，感兴趣的可以试试
            sb.append("Content-Disposition: form-data;name=\"media\";filename=\"" + fileName + "\";filelength=\"" + filelength + "\" \r\n");
            sb.append("Content-Type:application/octet-stream\r\n\r\n");
            byte[] head = sb.toString().getBytes("utf-8");
            // 获得输出流
            out = new DataOutputStream(con.getOutputStream());
            // 输出表头
            out.write(head);
            // 文件正文部分
            // 把文件已流文件的方式 推入到url中
            is = new FileInputStream(file);
            in = new DataInputStream(is);
            int bytes;
            byte[] bufferOut = new byte[1024];
            while ((bytes = in.read(bufferOut)) != -1) {
                out.write(bufferOut, 0, bytes);
            }
            // 结尾部分，这里结尾表示整体的参数的结尾，结尾要用"--"作为结束，这些都是http协议的规定
            byte[] foot = ("\r\n--" + boundary + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
            out.write(foot);
            out.flush();
            StringBuilder buffer = new StringBuilder();
            BufferedReader reader;
            // 定义BufferedReader输入流来读取URL的响应
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String result = buffer.toString();
            JSONObject jsonObject = JSONObject.parseObject(result);
            return jsonObject.getString("media_id");
        } catch (Exception e) {
            LOGGER.error("上传素材异常：" + e.getMessage(), e);
        } finally {
            IOUtils.close(out);
            IOUtils.close(in);
            IOUtils.close(is);
        }
        return null;
    }

}
