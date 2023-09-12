package zixin.example.backend.dao;

public interface DbRepositary {
    int saveUser(User user);

    int saveImage(Image image);

    User getUser(String userId);

    Image getImage(String userId);

}
