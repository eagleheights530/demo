package zixin.example.backend.dao;

import java.sql.Timestamp;

public class Image {
    private String imageId;

    private String userId;

    private byte[] imageData;

    private Timestamp lastModified;

    public Image(String imageId, String userId, byte[] imageData, Timestamp lastModified) {
        this.imageId = imageId;
        this.userId = userId;
        this.imageData = imageData;
        this.lastModified = lastModified;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public void setLastModified(Timestamp lastModified) {
        this.lastModified = lastModified;
    }

    public String getImageId() {
        return imageId;
    }

    public String getUserId() {
        return userId;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public Timestamp getLastModified() {
        return lastModified;
    }

}
