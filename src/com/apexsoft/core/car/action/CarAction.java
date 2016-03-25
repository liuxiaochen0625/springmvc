package com.apexsoft.core.car.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("car")
public class CarAction {
	
	@RequestMapping(value="/demo")
	public @ResponseBody ModelAndView menuOpMng(){
		ModelAndView model = new ModelAndView("/car/demo");
		return model;
	}
	@RequestMapping(value="/test")
	public @ResponseBody ModelAndView test(){
		ModelAndView model = new ModelAndView("/car/test");
		return model;
	}

}
