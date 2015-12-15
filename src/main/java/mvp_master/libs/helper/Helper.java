package mvp_master.libs.helper;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import mvp_master.libs.net.RequestParams;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:    2015年12月15日10:52:02
 * Description:
 */
public class Helper {

    public static <T> Class<T> generateType(Class<?> klass) {
        Type type = klass.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            ParameterizedType paramType = (ParameterizedType) type;
            Type[] actualTypes = paramType.getActualTypeArguments();
            if (actualTypes != null && actualTypes.length > 0) {
                return (Class<T>) actualTypes[0];
            }
        }

        return null;
    }

    public static RequestParams bean2Params(IBaseBean bean) {
        RequestParams params = new RequestParams();
        Class<?> klass = bean.getClass();
        Field[] fields = klass.getDeclaredFields();
        for (Field f : fields) {
            if (!f.isAnnotationPresent(Annotation.class)) continue;
            Annotation annotation = f.getAnnotation(Annotation.class);
            String key = annotation.key();
            f.setAccessible(true);
            try {
                params.add(key, f.get(bean));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return params;
    }
}
