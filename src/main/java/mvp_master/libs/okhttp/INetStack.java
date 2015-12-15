package mvp_master.libs.okhttp;

import mvp_master.libs.net.Net;
import mvp_master.libs.net.RequestParams;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:    2015年12月15日10:01:03
 * Description:
 */
public interface INetStack<T> {


    void get(final String url, final Net.Parser<T> parser, final Net.Callback<T> callback, final Object tag);

    void post(final String url, final RequestParams params,
              final Net.Parser<T> parser,
              final Net.Callback<T> callback, final Object tag);


    void onNetResponse(final Net.Parser<T> parser,
                       final Net.Callback<T> callback,
                       final String response);

    void onError(final Net.Callback<T> callback, final String msg);

    void cancel(Object tag);
}
