package com.noteam.next.services;

import com.noteam.next.entities.Country;
import com.noteam.next.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryService {
    @Autowired
    CountryRepository countryRepository;
    public List<Country> getAllCountries(){
        return countryRepository.findAll(Sort.by("name"));
    }
}
