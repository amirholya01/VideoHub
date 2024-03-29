package com.videohub.videohub.controller;

import com.videohub.videohub.domain.Rate;
import com.videohub.videohub.domain.Video;
import com.videohub.videohub.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("rate")
@CrossOrigin(origins = "*", maxAge = 3600)
public class RateController {

    @Autowired
    private RateService rateService;

    @GetMapping("{id}")
    public Rate findRateByVideoId(@PathVariable String id) {
        return rateService.findRateByVideoId(id);
    }

    @CrossOrigin
    @PostMapping
    private Video saveLike(@RequestBody Rate rate) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return rateService.save(username, rate);
    }

    @CrossOrigin
    @PostMapping("dislike")
    private Video saveDislike(@RequestBody Rate rate) {
        return rateService.saveDislike(rate);
    }

    @GetMapping("/reaction/{id}")
    public Rate findRateById(@PathVariable String id) {
        return rateService.findAllRate(id);
    }

}
