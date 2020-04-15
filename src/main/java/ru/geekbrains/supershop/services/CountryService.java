package ru.geekbrains.supershop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.geekbrains.supershop.persistence.entities.Country;
import ru.geekbrains.supershop.persistence.repositories.CountryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryService {
    private final CountryRepository countryRepository;

    public List<Country> findAll(){
        return countryRepository.findAll();
    }
}
