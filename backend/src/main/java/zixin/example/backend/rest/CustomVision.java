package zixin.example.backend.rest;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class CustomVision {
    final static String PROJECT_ID = "564c7c07-4e85-4b10-9b0a-4e16cc899437";
    final static String TRAINING_ENDPOINT = "https://northcentralus.api.cognitive.microsoft.com/";
    final static String PREDICTION_ENDPOINT = "https://northcentralus.api.cognitive.microsoft.com/customvision/v3.0/Prediction/564c7c07-4e85-4b10-9b0a-4e16cc899437/classify/iterations/Iteration2/image";
    final static String API_KEY = "cbcbb505dac1439c83930bfbff8b8562";

    static RestTemplate restTemplate = new RestTemplate();

    // Maps each tag to its Custom Vision tag ID
    final static public Map<String, String> tags = new HashMap<>() {{
        put("Zixin", "cd52dd33-9e63-4c2f-90af-8b2c6b8fe16b");
        put("Others", "f10b21df-7b00-471b-aea2-c179d575e919");
    }};

    /** Creates a new tag to categorize data in Custom Vision */
    public static void createTag(String tagName) throws JSONException {
        // Add params to URI
        Map<String, String> params = new HashMap<>();
        params.put("endpoint", TRAINING_ENDPOINT);
        params.put("projectId", PROJECT_ID);
        params.put("name", tagName);

        String url = "{endpoint}/customvision/v3.3/Training/projects/{projectId}/tags?name={name}";
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
        URI uri = builder.buildAndExpand(params).toUri();

        // Set request headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Training-key", API_KEY);

        // Make request and print result of success
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.postForEntity(uri, request, String.class);
        JSONObject jsonObject = new JSONObject(response.getBody());

        System.out.println(jsonObject.getString("id") + ": " + tagName);
    }

    /** Uploads a new image to the training set and tags it accordingly */
    private static void uploadImage(String tagId, byte[] fileData) throws JSONException {
        // Add params to URI
        Map<String, String> params = new HashMap<>();
        params.put("endpoint", TRAINING_ENDPOINT);
        params.put("projectId", PROJECT_ID);
        params.put("tagIds", tagId);

        String url = "{endpoint}/customvision/v3.3/training/projects/{projectId}/images?tagIds={tagIds}";
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
        URI uri = builder.buildAndExpand(params).toUri();

        // Set request headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.set("Training-key", API_KEY);

        // Make request and print result of success
        HttpEntity<byte[]> request = new HttpEntity<>(fileData, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(uri, request, String.class);

        System.out.println(response.getBody());
    }

    public static void uploadImageWithTagZixin(byte[] fileData) throws JSONException{
        String tagId = tags.get("Zixin");
        uploadImage(tagId, fileData);
    }
    /** Sends a prediction request to Custom Vision endpoint and returns the result of validation */
    public static ResponseEntity<String> validate(byte[] data) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/octet-stream");
        headers.add("Prediction-Key", API_KEY);

        HttpEntity<byte[]> entity = new HttpEntity<>(data, headers);
        ResponseEntity<String> result = restTemplate.postForEntity(PREDICTION_ENDPOINT, entity, String.class);
        return result;
    }

    public static void main(String args[]) throws JSONException {
        //createTag("Zixin");
        //createTag("Others");
    }
}
