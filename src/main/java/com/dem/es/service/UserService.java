package com.dem.es.service;

import com.dem.es.entity.User;
import org.springframework.stereotype.Service;

public interface UserService {

    User getById(Long id);
}
