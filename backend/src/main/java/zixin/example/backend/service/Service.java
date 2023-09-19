package zixin.example.backend.service;

import zixin.example.backend.dao.Image;
import zixin.example.backend.dao.User;

public interface Service {
    public void saveUserAndImage (User user, Image image);
}
