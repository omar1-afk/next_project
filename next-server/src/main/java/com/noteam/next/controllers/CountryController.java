package com.noteam.next.controllers;

import com.noteam.next.entities.Country;
import com.noteam.next.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/country")
public class CountryController {
    @Autowired
    CountryService countryService;
    @GetMapping("/All")
    ResponseEntity<List<Country>> getAllCountries(){
        List<Country> countryList = countryService.getAllCountries();
        if(countryList.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        else {
            return ResponseEntity.ok(countryList);
        }
    }
}
