package zixin.example.backend.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SqlDb implements DbRepositary {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int saveUser(User user) {

        return jdbcTemplate.update(
                "insert into Users (userid, fullName, email) values(?,?,?)",
                user.getUserId(), user.getFullName(), user.getEmail());

    }

    @Override
    public int saveImage(Image image) {

        return jdbcTemplate.update(
                "insert into Images (userid, imageId, imageData) values(?,?,?)",
                image.getUserId(), image.getImageId(), image.getImageData());
    }

    @Override
    public User getUser(String userId) {
        String sql = "SELECT * FROM Users WHERE userId = ?";
        User target = jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
                new User(
                        rs.getString("userId"),
                        rs.getString("fullName"),
                        rs.getString("email"),
                        rs.getTimestamp("lastModified")
                ), new Object[] { userId });
        return target;
    }

    @Override
    public Image getImage(String userId) {
        String sql = "SELECT * FROM Images WHERE userId = ?";
        Image target = jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
                new Image(
                        rs.getString("imageId"),
                        rs.getString("userId"),
                        rs.getBytes("imageData"),
                        rs.getTimestamp("lastModified")
                ),new Object[]{userId});
        return target;

    }

}
