package com.sge.clear.admin.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sge.clear.admin.service.StepService;

@Controller
@RequestMapping("/step")
/**
 * @Description 运行清算任务对应的Controller
 * @author yh 2014-05-14
 */
public class StepViewController {
	
	private static final Logger logger = LoggerFactory.getLogger("file.log");
	
	@Resource(name = "stepServiceHttp")
	private StepService stepService;
	
	@RequestMapping("runTask")
	public String runTask(@RequestParam("datepicker") String date,HttpServletRequest request) {
		logger.info("begin runTask:date={}",date);
		if(stepService.isTaskRuning()){
			request.setAttribute("errorMsg", "有清算任务执行中，请至‘清算结果查询’页面确认，并等待当前清算任务执行结束。");
			logger.info("有清算任务执行中，返回出错页面提示:请等待当前清算任务执行结束");
			return "common/error";
		}
		//启动清算任务
		int ret = stepService.runTask(date);
		logger.info("stepService.runTask:data={}",date);
		if(ret>=0){
			logger.info("启动清算任务成功,redirect:/admin/stepResult.jsp");
			return "redirect:/admin/stepResult.jsp";   		//启动清算任务成功，跳转至清算过程展示页面
		}
		else{
			logger.info("启动清算任务任务失败,返回出错页面");
			request.setAttribute("errorMsg", "出错了，请稍后重试或联系系统管理员");
			return "common/error"; //启动清算任务失败，跳转至出错页面
		}
	}
	
	 /**
	 * 异常页面控制
	 *
	 * @param runtimeException
	 * @return
	 */
	 @ExceptionHandler(Exception.class)
	 public String runtimeExceptionHandler(Exception exception,HttpServletRequest request) {
		 logger.error("Exception:{}",exception);
		 request.setAttribute("errorMsg", "出错了，请稍后重试或联系系统管理员");
		 return "common/error"; //启动清算任务失败，跳转至出错页面
	 }

}
