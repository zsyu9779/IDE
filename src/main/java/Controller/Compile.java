package Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import pojo.Data;

public class Compile {

    public static String compiler(String json) {
        Data d = JSON.parseObject(json,Data.class);

        return json;
    }


}
