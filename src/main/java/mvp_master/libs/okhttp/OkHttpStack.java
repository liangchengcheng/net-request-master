package mvp_master.libs.okhttp;

import android.os.Handler;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;
import mvp_master.libs.net.Net;
import mvp_master.libs.net.RequestParams;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:    2015年12月15日10:47:52
 * Description:    { OkHttpStack  也可以使用Volley}
 */
public class OkHttpStack<T> extends  AbsHttpStack<T>{

    private OkHttpClient mClient;
    private Handler mHandler;

    public OkHttpStack(){
        mClient=new OkHttpClient();
        mHandler=new Handler();
    }

    @Override
    public void get(String url, Net.Parser<T> parser, Net.Callback<T> callback, Object tag) {
        final Request request = new Request.Builder()
                .url(url).build();
        call(request, parser, callback, tag);
    }

    @Override
    public void post(String url, RequestParams params, Net.Parser<T> parser, Net.Callback<T> callback, Object tag) {
        MultipartBuilder builder = new MultipartBuilder()
                .type(MultipartBuilder.FORM);
        //添加普通的参数
        LinkedHashMap<String, String> map = params.get();
        for(Iterator<String> iterator=map.keySet().iterator();iterator.hasNext();) {
            String key = iterator.next();
            builder.addPart(Headers.of("Content-Disposition",
                            "form-data; name=\"" + key + "\""),
                    RequestBody.create(null, map.get(key)));
        }
        //添加文件参数
        LinkedHashMap<String, File> files = params.files();
        for(Iterator<String> iterator=files.keySet().iterator();iterator.hasNext();) {
            String key = iterator.next();
            File file = files.get(key);
            builder.addPart(Headers.of("Content-Disposition",
                            "form-data; name=\"" + key + "\";filename=\""+ file.getName() +"\""),
                    RequestBody.create(MediaType.parse("application/octet-stream"), file));
        }

        //组装Request
        Request request = new Request.Builder()
                .url(url).post(builder.build()).build();
        call(request, parser, callback, tag);
    }

    private void call(Request request, final Net.Parser<T> parser,
                      final Net.Callback<T> callback, final Object tag) {
        Request.Builder builder = request.newBuilder();
        if(tag != null) builder.tag(tag);
        //在这里添加头部文件
        LinkedHashMap<String, String> map = headers();
        if(map != null && !map.isEmpty()) {
            for(Iterator<String> iterator=map.keySet().iterator();iterator.hasNext();) {
                String key = iterator.next();
                builder.addHeader(key, map.get(key));
            }
            request = builder.build();
        }

        OkHttpClient client = mClient.clone();
        client.setConnectTimeout(20, TimeUnit.SECONDS);

        final Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, final IOException e) {
                final String msg = e.getMessage();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        onError(callback, msg);
                    }
                });
            }

            @Override
            public void onResponse(final Response response) throws IOException {
                final String resp = response.body().string();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        onNetResponse(parser, callback, resp);
                    }
                });
            }
        });
    }

    public LinkedHashMap<String, String> headers() {
        //在这里你可以添加头部文件
        return null;
    }

    @Override
    public void cancel(Object tag) {
        mClient.cancel(tag);
    }
}
