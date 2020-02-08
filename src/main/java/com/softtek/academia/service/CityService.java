package com.softtek.academia.service;

import java.util.List;

import com.softtek.academia.entity.City;

public interface  CityService {
	
	public List<City> getAllCities();

	public City getCityById(Long id);

	public boolean saveCity(City user);

	public boolean deleteCityById(Long id);

}
