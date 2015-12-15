package mvp_master.libs.okhttp;

import android.util.Log;
import mvp_master.libs.net.Net;
import mvp_master.libs.net.Result;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:    2015年12月15日10:47:52
 * Description:    {  AbsHttpStack }
 */
public abstract class AbsHttpStack<T> implements INetStack<T> {

    protected boolean debug;

    public void debug(boolean debug) {
        this.debug = debug;
    }

    public boolean isDebug() {
        return this.debug;
    }

    @Override
    public void onNetResponse(Net.Parser<T> parser, Net.Callback<T> callback, String response) {
        if (debug) Log.e("lcc", response);
        if (callback == null) return;
        if (parser == null) {
            Result<T> result = new Result<>();
            result.setStatus(Result.ERROR);
            result.setMsg(Net.ERR_PARSE_MSG);
            callback.callback(result);
            return;
        }
        Result<T> result = parser.parse(response);
        callback.callback(result);
    }

    @Override
    public void onError(Net.Callback<T> callback, String msg) {
        if (debug) Log.e("lcc", msg);
        if (callback == null) {
            return;
        }

        Result<T> result = new Result<>();
        result.setStatus(Result.ERROR);
        result.setMsg(msg);
        callback.callback(result);
    }
}
