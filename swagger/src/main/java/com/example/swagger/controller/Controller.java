package com.example.swagger.controller;

import com.example.swagger.controller.model.movies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/movies")
public class Controller {

    Logger logger = LoggerFactory.getLogger(Controller.class);
    private static List<movies> data = new ArrayList<>();

    @GetMapping("/get")
    public List<movies> getMovies() {
        return data;
    }

    @PostMapping("/save")
    public List<movies> setMovies(@RequestBody movies movie) {
        data.add(movie);
        return data;
    }
}
