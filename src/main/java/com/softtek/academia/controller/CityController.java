package com.softtek.academia.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.softtek.academia.entity.City;
import com.softtek.academia.entity.State;
import com.softtek.academia.service.CityService;
import com.softtek.academia.service.StateService;

@Controller
public class CityController {
	
private CityService cityService;
	
	@Autowired
	private StateService stateService;

	public CityController() {
	}

	@Autowired
	public CityController(CityService cityService) {
		this.cityService = cityService;
	}
	
	@RequestMapping(value = "/allcities", method = RequestMethod.POST)
	public ModelAndView displayAllCitys() {
		System.out.println("Cities ");
		ModelAndView mv = new ModelAndView();
		List<City> cityList = cityService.getAllCities();
		mv.addObject("cityList", cityList);
		mv.setViewName("allcities");
		return mv;
	}

	@RequestMapping(value = "/addcity", method = RequestMethod.GET)
	public ModelAndView displayNewCityForm() {
		ModelAndView mv = new ModelAndView("addCity");
		mv.addObject("headerMessage", "Add City");
		mv.addObject("city", new City());
		List<State> states = stateService.getAllStates();
		mv.addObject("states", states);
		return mv;
	}

	@RequestMapping(value = "/addCity", method = RequestMethod.POST)
	public ModelAndView saveNewCity(@ModelAttribute City city, BindingResult result) {
		ModelAndView mv = new ModelAndView("redirect:/home");

		if (result.hasErrors()) {
			return new ModelAndView("error");
		}
		boolean isAdded = cityService.saveCity(city);
		if (isAdded) {
			mv.addObject("message", "New City");
		} else {
			return new ModelAndView("error");
		}
		return mv;
	}

	@RequestMapping(value = "/editCity/{id}", method = RequestMethod.GET)
	public ModelAndView displayEditCityForm(@PathVariable Long id) {
		ModelAndView mv = new ModelAndView("/editCity");
		City city = cityService.getCityById(id);
		mv.addObject("headerMessage", "Edit City");
		mv.addObject("city", city);
		return mv;
	}

	@RequestMapping(value = "/editCity/{id}", method = RequestMethod.POST)
	public ModelAndView saveEditedCity(@ModelAttribute City city, BindingResult result) {
		ModelAndView mv = new ModelAndView();
		if (result.hasErrors()) {
			System.out.println(result.toString());
			return new ModelAndView("error");
		}
		boolean isSaved = cityService.saveCity(city);
		if (!isSaved) {
			return new ModelAndView("error");
		}
		return mv;
	}

	@RequestMapping(value = "/deletecity/{id}", method = RequestMethod.GET)
	public ModelAndView deleteCityById(@PathVariable Long id) {
		boolean isDeleted = cityService.deleteCityById(id);
		System.out.println("User deletion response: " + isDeleted);
		ModelAndView mv = new ModelAndView("redirect:/home");
		return mv;
	}


}
