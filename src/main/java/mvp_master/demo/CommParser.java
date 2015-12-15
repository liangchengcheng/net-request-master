package mvp_master.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import mvp_master.libs.helper.Helper;
import mvp_master.libs.net.Net;
import mvp_master.libs.net.Result;

public class CommParser<T> implements Net.Parser<T> {
	
	private String mKey;
	
	public CommParser(String key) {
		mKey = key;
	}

	@Override
	public Result<T> parse(String response) {
		Result<T> result = new Result<T>();
		try {
			JSONObject baseObject = JSON.parseObject(response);
			if(!baseObject.getBooleanValue("success")) {
				result.setMsg(baseObject.getString("message"));
			}else {
				Class<T> klass = Helper.generateType(getClass());
				if(klass == null) throw new Exception();
				
				T t = baseObject.getObject(mKey, klass);
				result.setStatus(Result.SUCCESS);
				result.setResult(t);
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg(Net.ERR_PARSE_MSG);
		}
		
		result.setStatus(Result.ERROR);
 		return result;
	}
}
