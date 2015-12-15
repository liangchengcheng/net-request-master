package mvp_master.demo;

import mvp_master.libs.helper.Annotation;
import mvp_master.libs.helper.IBaseBean;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:    2015年12月15日15:19:54
 * Description:
 */
public class User implements IBaseBean {

    @Annotation(key = "name")
    private String name;
    @Annotation(key = "age")
    private int age;
    @Annotation(key = "city")
    private String city;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "name=" + name + ",age=" + age + ",city=" + city;
    }
}
