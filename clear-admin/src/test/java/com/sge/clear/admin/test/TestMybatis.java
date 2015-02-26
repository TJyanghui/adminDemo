package com.sge.clear.admin.test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sge.clear.admin.model.ClearStepDO;
import com.sge.clear.admin.service.StepService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring.xml", "classpath:spring/spring-mybatis.xml" })
public class TestMybatis {
	
	@Resource(name = "stepServiceHttp")
	private StepService stepService;
	

	@Test
	public void testInsert() {
		List<ClearStepDO> result = stepService.getStepStatus();
		return ;
	
	}
	
	
	@Test
	public void test2() throws IOException, InterruptedException {
		String command = "python G:\\python\\test.py";
		Runtime run = Runtime.getRuntime();//返回与当前 Java 应用程序相关的运行时对象  
        Process p = run.exec(command);// 启动另一个进程来执行命令  
        BufferedInputStream in = new BufferedInputStream(p.getInputStream());  
        BufferedReader inBr = new BufferedReader(new InputStreamReader(in));  
        String lineStr;  
        while ((lineStr = inBr.readLine()) != null)  
            //获得命令执行后在控制台的输出信息  
            System.out.println(lineStr);// 打印输出信息  
        //检查命令是否执行失败。  
        if (p.waitFor() != 0) {  
            if (p.exitValue() == 1){//p.exitValue()==0表示正常结束，1：非正常结束  
            	int ret = p.exitValue();
                System.err.println(ret); 
            }
        }  
        inBr.close();  
        in.close(); 
	}
	
	@Test
	public void test3() throws UnsupportedEncodingException  {
		String s = "\ub2\ue2\uca\ud4";
		System.out.println(s);
		String t = new String(s.getBytes("gb2312"),"utf-8");
		System.out.println(t);
	}

}
