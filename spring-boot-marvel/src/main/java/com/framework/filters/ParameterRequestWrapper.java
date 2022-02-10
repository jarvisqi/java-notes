package com.framework.filters;

import cn.hutool.json.JSONUtil;
import com.ctrip.framework.apollo.core.utils.StringUtils;
import com.framework.common.IOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;


import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@Slf4j
public class ParameterRequestWrapper extends HttpServletRequestWrapper {

    private Map<String, String[]> params = new HashMap<>();

    public ParameterRequestWrapper(HttpServletRequest request) {
        // 将request交给父类，以便于调用对应方法的时候，将其输出，其实父亲类的实现方式和第一种new的方式类似
        super(request);

        //将参数表，赋予给当前的Map以便于持有request中的参数
        Map<String, String[]> parameterMap = request.getParameterMap();
        log.info("转换前参数：{}", JSONUtil.toJsonStr(parameterMap));
        this.params.putAll(parameterMap);
        this.modifyParameterValues();
        log.info("转换后参数：{}", JSONUtil.toJsonStr(parameterMap));

    }

    /**
     * 重写getInputStream方法  post类型的请求参数必须通过流才能获取到值
     *
     * @return
     * @throws IOException
     */
    @Override
    public ServletInputStream getInputStream() throws IOException {
        //非json类型，直接返回
        if (!super.getHeader(HttpHeaders.CONTENT_TYPE).equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE)) {
            return super.getInputStream();
        }
        //为空直接返回
        String json = IOUtil.streamToString(super.getInputStream());
        if (StringUtils.isEmpty(json)) {
            return super.getInputStream();
        }
        log.info("转换前参数：{}", JSONUtil.toJsonStr(json));
        Map<String, Object> map = IOUtil.jsonToMap(json, true);
        log.info("转换后参数：{}", JSONUtil.toJsonStr(map));
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(JSONUtil.toJsonStr(map).getBytes("utf-8"));
        return new ParamsServletInputStream(byteArrayInputStream);
    }

    /**
     * 重写getParameter 参数从当前类中的map获取
     */
    @Override
    public String getParameter(String name) {
        String[] values = params.get(name);
        if (values == null || values.length == 0) {
            return null;
        }
        return values[0];
    }

    /**
     * 重写getParameterValues
     */
    @Override
    public String[] getParameterValues(String name) {
        return params.get(name);
    }


    /**
     * 将parameter的值去除空格后重写回去
     */
    private void modifyParameterValues() {
        Set<String> keys = params.keySet();
        Iterator<String> keyIt = keys.iterator();
        while (keyIt.hasNext()) {
            String key = keyIt.next();
            String[] values = params.get(key);
            values[0] = values[0].trim();
            params.put(key, values);
        }
    }


}
