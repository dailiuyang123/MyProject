package com.daily.web.controller;

import com.daily.mybatis.entity.JsonMessage;
import com.daily.util.WordGenerator;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 17600 on 2017/8/15.
 */

@Controller
@RequestMapping("/word")
public class WordController {

    @RequestMapping(value = "/getWord", method = RequestMethod.POST)
    public void getWord(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("utf-8");
        Map<String, Object> map = new HashMap<String, Object>();
        Enumeration<String> paramNames = req.getParameterNames();
        // 通过循环将表单参数放入键值对映射中
        while(paramNames.hasMoreElements()) {
            String key = paramNames.nextElement();
            String value = req.getParameter(key);
            map.put(key, value);
        }

        // 提示：在调用工具类生成Word文档之前应当检查所有字段是否完整
        // 否则Freemarker的模板殷勤在处理时可能会因为找不到值而报错 这里暂时忽略这个步骤了
        File file = null;
        InputStream fin = null;
        ServletOutputStream out = null;
        try {
            // 调用工具类WordGenerator的createDoc方法生成Word文档
            file = WordGenerator.createDoc(map, "resume");
            fin = new FileInputStream(file);

            resp.setCharacterEncoding("utf-8");
            resp.setContentType("application/msword");
            // 设置浏览器以下载的方式处理该文件默认名为resume.doc
            resp.addHeader("Content-Disposition", "attachment;filename=resume.doc");

            out = resp.getOutputStream();
            byte[] buffer = new byte[512];  // 缓冲区
            int bytesToRead = -1;
            // 通过循环将读入的Word文件的内容输出到浏览器中
            while((bytesToRead = fin.read(buffer)) != -1) {
                out.write(buffer, 0, bytesToRead);
            }
        } finally {
            if(fin != null) fin.close();
            if(out != null) out.close();
            if(file != null) file.delete(); // 删除临时文件
        }
    }

    /**
    *
    * 作者  json
    * 时间  2018/4/9 17:51
    * 描述  world 文档导出
    *
    **/
    @RequestMapping(value = "/exportWorld",method = RequestMethod.POST)
    public void exportWorld(HttpServletRequest request,HttpServletResponse response){
        try {
            WordGenerator wordGenerator=new WordGenerator(1);
            Map map=new HashMap();
            wordGenerator.exportWorld(map,request,response);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}


