package zixin.example.backend.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Base64;
import java.util.UUID;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;

import zixin.example.backend.dao.Image;
import zixin.example.backend.dao.User;
import zixin.example.backend.service.CustomVision;
import zixin.example.backend.service.Service;

@RestController
@CrossOrigin(origins = "${FRONTEND_HOST:*}") // Devops best practice, don't hardcode

public class ApiController {
    @Autowired
    Service serviceImpl;

    private final String CONNECTION_STRING = "DefaultEndpointsProtocol=https;AccountName=zixinchi;AccountKey=suuxxnadymD+Wk9B1yo+YCSymj2DrdgelGyp8U6Y6o3G9I69jPV8BGPTsJh7wIkcSPc6LoKAzcba+ASt+m3z8w==;EndpointSuffix=core.windows.net";
    @GetMapping("/greeting")
    public String greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return "Hello " + name;
    }

    @PostMapping("/images")
    public ResponseEntity<String> uploadImage(@RequestBody String data) throws IOException {
        String base64 = data.replace("data:image/png;base64,", "");
        byte[] rawBytes = Base64.getDecoder().decode(base64);
        String imageName = UUID.randomUUID() + ".png";
        saveImageToFile(rawBytes, imageName);
        saveToCloud(rawBytes, imageName);
        CustomVision.uploadImageWithTagZixin(rawBytes);
        return new ResponseEntity<>("Successfully uploaded image", HttpStatus.OK);
    }

    @PostMapping("/validate")
    public ResponseEntity<String> validate(@RequestBody String data) throws IOException {
        String base64 = data.replace("data:image/png;base64,", "");
        byte[] rawBytes = Base64.getDecoder().decode(base64);
        return CustomVision.validate(rawBytes);
    }

    @PostMapping("/form")
    public ResponseEntity<String> submit(@RequestBody String data){
        JSONObject jsonObject= new JSONObject(data);
        String name = jsonObject.getString("name");
        String email = jsonObject.getString("email");
        String base64 = jsonObject.getString("image").replace("data:image/png;base64,", "");
        byte[] rawBytes = Base64.getDecoder().decode(base64);

        UUID uuid = UUID.randomUUID();
        String userId = uuid.toString();

        User user = new User(userId, name, email, null);
        Image image = new Image(userId, userId, rawBytes,  null);

        serviceImpl.saveUserAndImage(user, image);

        ResponseEntity<String> result = new ResponseEntity<String>(HttpStatus.OK);

        return result;

    }   

    private void saveImageToFile(byte[] image, String imageName) throws IOException {
        File path = new File("./images/");
        if (!path.exists()) {
            path.mkdir();
        }
        Files.write(new File("./images/" + imageName).toPath(), image);
    }

    private void saveToCloud(byte[] image, String imageName) {
            // Create a BlobServiceClient object using a connection string
            BlobServiceClient client = new BlobServiceClientBuilder().connectionString(CONNECTION_STRING).buildClient();
    
            // Create a unique name for the container
            String containerName = "images";
    
            // Create the container and return a container client object
            BlobContainerClient blobContainerClient = client.createBlobContainerIfNotExists(containerName);
    
            // Get a reference to a blob
            BlobClient blobClient = blobContainerClient.getBlobClient(imageName);
    
            // Upload the blob
            // blobClient.uploadFromFile(localPath + fileName);
            InputStream targetStream = new ByteArrayInputStream(image);
            blobClient.upload(targetStream);
        }
    
}
