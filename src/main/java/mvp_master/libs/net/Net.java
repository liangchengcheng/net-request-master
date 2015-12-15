package mvp_master.libs.net;

import mvp_master.libs.helper.Helper;
import mvp_master.libs.helper.IBaseBean;
import mvp_master.libs.okhttp.INetStack;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:    2015年12月15日10:02:19
 * Description:
 */
public class Net {

    /**
     * 默认的错误提醒
     */
    public static final String DEF_ERR_MSG = "请求失败";

    /**
     * 数据解析失败
     */
    public static final String ERR_PARSE_MSG = "数据解析失败";

    private static INetStack sNetStack;

    public static void init(INetStack netStack) {
        sNetStack = netStack;
    }

    /**
     * get请求
     *
     * @param url      网址
     * @param parser   解析器
     * @param callback 回调
     */
    public static <T> void get(final String url, final Parser<T> parser, final Callback<T> callback, final Object tag) {
        try {
            sNetStack.get(url, parser, callback, tag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * post请求
     *
     * @param url      访问的url
     * @param params   post参数
     * @param parser   解析器
     * @param callback 回调
     */
    public static <T> void post(final String url, final RequestParams params,
                                final Parser<T> parser, final Callback<T> callback, final Object tag) {
        try {
            sNetStack.post(url, params, parser, callback, tag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * post请求
     *
     * @param url      访问的url
     * @param params   post参数
     * @param parser   解析器
     * @param callback 回调
     */
    public static <T> void post(final String url, final IBaseBean params,
                                final Parser<T> parser, final Callback<T> callback,
                                final Object tag) {
        try {
            sNetStack.post(url, Helper.bean2Params(params),
                    parser, callback, tag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void cancel(Object tag) {
        sNetStack.cancel(tag);
    }

    /**
     * 数据返回接口
     *
     * @param <T> 类型
     */
    public interface Callback<T> {
        void callback(Result<T> result);
    }

    /**
     * 数据解析
     *
     * @param <T> 类型
     */
    public interface Parser<T> {

        Result<T> parse(String response);
    }

    public static class NoParser implements Parser<String> {

        @Override
        public Result<String> parse(String response) {
            Result<String> res = new Result<>();
            res.setResult(response);
            return res;
        }
    }
}
