package zixin.example.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import zixin.example.backend.dao.DbRepositary;
import zixin.example.backend.dao.Image;
import zixin.example.backend.dao.User;

@org.springframework.stereotype.Service
public class ServiceImpl implements Service{
    @Autowired
    DbRepositary sqlDb;

    @Override
    @Transactional
    public void saveUserAndImage(User user, Image image){
        sqlDb.saveUser(user);
        sqlDb.saveImage(image);
    }
    
}
