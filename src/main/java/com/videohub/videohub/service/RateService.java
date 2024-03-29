package com.videohub.videohub.service;

import com.videohub.videohub.domain.Rate;
import com.videohub.videohub.domain.User;
import com.videohub.videohub.domain.Video;
import com.videohub.videohub.repository.RateRepository;
import com.videohub.videohub.repository.UserRepository;
import com.videohub.videohub.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class RateService {

    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    private RateRepository rateRepository;
    @Autowired
    private UserRepository userRepository;

    public Rate findRateByVideoId(String id) {
        return null;
    }


    public Video save(String username, Rate rate) {
        Video video = new Video();
        Optional<Video> videoData = videoRepository.findById(rate.getVideoId());
        if (videoData.isPresent()) {
            video = videoData.get();
        }

        User user = new User();
        Optional<User> userData = userRepository.findByUsername(username);
        if (userData.isPresent()) {
            user = userData.get();
        }

        Rate userRate = new Rate();

        if (rate.getLike() > 0 || rate.getDisLike() > 0) {
            if ((userRate.getId() == rate.getId() || userRate.getId() == null) && userRate.getLike() == 0) {
                if (rate.getLike() > 0) {
                    userRate.setId(rate.getId());
                    userRate.setVideoId(rate.getVideoId());
                    int like = video.getRate().getLike() + 1;
                    userRate.setLike(1);
                    rate.setLike(like);
                    if (video.getRate().getDisLike() > 0) {
                        int disLike = video.getRate().getDisLike() - 1;
                        userRate.setLike(0);
                        rate.setDisLike(disLike);
                    }
                    video.setRate(rate);
                }
                user.getRates().add(userRate);
                rateRepository.save(rate);
            }
            return videoRepository.save(video);
        }
        return video;
    }


    public Video saveDislike(Rate rate) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Video video = new Video();
        Optional<Video> videoData = videoRepository.findById(rate.getVideoId());
        if (videoData.isPresent()) {
            video = videoData.get();
        }

        User user = new User();
        Optional<User> userData = userRepository.findByUsername(username);
        if (userData.isPresent()) {
            user = userData.get();
        }

        Set<Rate> rates = user.getRates();
        Rate userRate = rates.stream().filter(rate1 -> rate1.getId() == rate.getId()).findAny().orElse(null);
        if (userRate == null) {
            userRate = new Rate();
            userRate.setId(rate.getId());
            userRate.setVideoId(rate.getVideoId());
        }


        if (rate.getDisLike() > 0) {
            if (userRate.getDisLike() > 0) {

            } else if (userRate.getLike() > 0) {

            } else {
                userRate.setDisLike(1);
                int disLike = video.getRate().getDisLike() + 1;
                rate.setDisLike(disLike);
                user.getRates().add(rate);
                if(video.getRate().getLike() > 0){
                    int like = video.getRate().getLike() - 1;
                    userRate.setLike(0);
                    rate.setLike(like);
                }
                video.setRate(rate);
                user.getRates().add(userRate);
                rateRepository.save(rate);
                return videoRepository.save(video);
            }
        }

        return video;
    }



    public Rate findAllRate(String id) {
        return rateRepository.findById(id).get();
    }
}
