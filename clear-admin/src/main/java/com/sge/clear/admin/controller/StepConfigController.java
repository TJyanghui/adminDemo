package com.sge.clear.admin.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.sge.clear.admin.model.ClearStepDO;
import com.sge.clear.admin.service.StepConfigService;

@Controller
@RequestMapping("/stepConfig")
/**
 * @Description 增改清算配置对应的Controller
 * @author yh 2014-05-14
 */
public class StepConfigController{
	
	private static final Logger logger = LoggerFactory.getLogger("file.log");
	
	@Resource(name = "stepConfigService")
	private StepConfigService stepConfigService;
	
	@RequestMapping(value="/insert",method=RequestMethod.GET)
	public String insertStepConfig(@ModelAttribute("clearStepDO") ClearStepDO clearStepDO) {
		return "config/stepConfigInsert";
	}
	
	@RequestMapping(value="/insert",method=RequestMethod.POST)
	public String insertStepConfig(@Validated ClearStepDO clearStepDO,BindingResult br,HttpServletRequest request) {
		logger.info("insertStepConfig:{}", clearStepDO);
		if(br.hasErrors()){
			return "config/stepConfigInsert";
		}
		
		if(clearStepDO.getIp().isEmpty()){
			clearStepDO.setIp("localhost");
		}
		
		clearStepDO.setParam("");
		clearStepDO.setIsEnable(1);

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication() .getPrincipal();
		clearStepDO.setUserModified(userDetails.getUsername()); //当前登录用户
			
		int ret = stepConfigService.insertStep(clearStepDO); //调用service接口插入
		if(ret>=0){
			logger.info("redirect:/config/stepConfig.jsp");
			return "redirect:/config/stepConfig.jsp"; //插入成功，返回配置显示页面
		}
		else{
			logger.info("插入DB失败，返回出错页面 ");
			request.setAttribute("errorMsg", "插入清算场次信息失败，请确认场次号是否与当前配置中的场次重复");
			return "common/error"; //启动清算任务失败，跳转至出错页面
		}
	}
	
	@RequestMapping(value="/{step}/edit",method=RequestMethod.GET)
	public String editStepConfig(@PathVariable int step, Model model){
		logger.info("editStepConfig: step={}",step);
		ClearStepDO stepDO = stepConfigService.getStepById(step);
		if(null == stepDO){
			logger.info("未查询到id对应的清算场次配置,step={}",step);
			model.addAttribute("errorMsg", "未查询到id对应的清算场次配置，请确认后重试");
			return "common/error"; //启动清算任务失败，跳转至出错页面	
		}
		logger.info("查询到id对应的清算场次配置:input_step={},get_step={},get_name={}", step, stepDO.getStep(), stepDO.getName());
		model.addAttribute(stepDO);
		
		return "config/stepConfigEdit";
	}
	
	@RequestMapping(value="/{step}/edit",method=RequestMethod.POST)
	public String editStepConfig(@Validated ClearStepDO clearStepDO,BindingResult br,HttpServletRequest request) {
		logger.info("insertStepConfig:{}", clearStepDO);
		if(br.hasErrors()){
			return "config/stepConfigEdit";
		}
		
		if(clearStepDO.getIp().isEmpty()){
			clearStepDO.setIp("localhost");
		}
		
		clearStepDO.setParam("");
		clearStepDO.setIsEnable(1);

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication() .getPrincipal();
		clearStepDO.setUserModified(userDetails.getUsername()); //当前登录用户
			
		int ret = stepConfigService.updateStepByStep(clearStepDO); //调用service接口插入
		if(ret>=0){
			logger.info("redirect:/config/stepConfig.jsp");
			return "redirect:/config/stepConfig.jsp"; //插入成功，返回配置显示页面
		}
		else{
			logger.info("插入DB失败，返回出错页面 ");
			request.setAttribute("errorMsg", "插入清算场次信息失败，请确认场次号是否与当前配置中的场次重复");
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
		 logger.error(exception.getLocalizedMessage());
		 if(exception instanceof DuplicateKeyException)
			 request.setAttribute("errorMsg", "场次号冲突，请确认场次号是否与当前配置中的场次重复");
		 else
			 request.setAttribute("errorMsg", "出错了，请稍后重试或联系系统管理员");
		 return "common/error"; //启动清算任务失败，跳转至出错页面
	 }

	
}
