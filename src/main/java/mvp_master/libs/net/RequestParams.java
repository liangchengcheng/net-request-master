package mvp_master.libs.net;

import java.io.File;
import java.util.LinkedHashMap;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:    2015年12月15日10:33:12
 * Description:   请求参数
 */
public class RequestParams {

    private LinkedHashMap<String, String> mParams;
    private LinkedHashMap<String, File> mFileParams;

    public RequestParams() {
        mParams = new LinkedHashMap<>();
        mFileParams = new LinkedHashMap<>();
    }

    /**
     * 添加参数
     *
     * @param key   参数名称
     * @param value 参数值
     * @return 参数
     */
    public RequestParams add(String key, Object value) {
        //是文件参数添加文件参数
        if (value instanceof File) mFileParams.put(key, (File) value);
            //添加普通参数
        else mParams.put(key, value.toString());
        return this;
    }

    /**
     * 获取普通参数
     */
    public LinkedHashMap<String, String> get() {
        return mParams;
    }

    /**
     * 获取带文件的参数
     */
    public LinkedHashMap<String, File> files() {
        return mFileParams;
    }
}
