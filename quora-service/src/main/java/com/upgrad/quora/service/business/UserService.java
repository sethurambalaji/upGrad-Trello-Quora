package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.UserAuthEntity;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    PasswordCryptographyProvider passwordCryptographyProvider;

    //This service class method fetch username and call dao method to fetch username from the database
    @Transactional
    public UserEntity getUserByUsername(String username) {
        UserEntity userEntity = userDao.getUserByUsername(username);
        return userEntity;
    }

    //This service class method fetch emailId and call dao method to fetch emailId from the database
    @Transactional
    public UserEntity getUserByEmail(String email) {
        UserEntity userEntity = userDao.getUserByEmail(email);
        return userEntity;
    }

    //This service class method sign up specific user
    public UserEntity signupUser(UserEntity user) {
        String[] array = passwordCryptographyProvider.encrypt(user.getPassword());
        user.setPassword(array[1]);
        user.setSalt(array[0]);
        userDao.signupUser(user);
        return user;
    }

    //This service class method validates a particular password
    public boolean isPasswordCorrect(String password, UserEntity user) {
        String encryptedPassword = passwordCryptographyProvider.encrypt(password, user.getSalt());
        if (user.getPassword().equals(encryptedPassword)) {
            return true;
        } else {
            return false;
        }
    }

    //This service class method save specific users login information and generate authentication token for authorization
    @Transactional
    public UserAuthEntity saveLoginInfo(UserAuthEntity userAuthEntity, String password) {
        JwtTokenProvider tokenProvider = new JwtTokenProvider(password);
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime expiryTime = currentTime.plusHours(8);

        ZonedDateTime currentZonedTime = ZonedDateTime.now();
        ZonedDateTime expiryZonedTime = currentZonedTime.plusHours(8);

        String authToken = tokenProvider.generateToken(userAuthEntity.getUuid(), currentZonedTime, expiryZonedTime);

        userAuthEntity.setAccessToken(authToken);
        userAuthEntity.setExpiryTime(expiryTime);
        userAuthEntity.setLoginTime(currentTime);

        return userDao.saveLoginInfo(userAuthEntity);

    }

    //This service class method fetch specific user uuid
    public UserEntity getUserByUuid(String uuid) throws UserNotFoundException {
        UserEntity user = userDao.getUserByUuid(uuid);
        if (user == null) {
            throw new UserNotFoundException("USR-001", "User with entered uuid whose question details are to be seen does not exist");
        }

        return user;
    }
}