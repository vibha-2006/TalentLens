import okhttp3.*;
import java.io.IOException;

public class TestGeminiAPI {
    public static void main(String[] args) {
        String apiKey = "AIzaSyD0x32Td48OmutIEbmw0N5S9lsbgs3wBmk";

        OkHttpClient client = new OkHttpClient();

        // Test 1: List models
        System.out.println("=== Testing List Models API ===");
        String listModelsUrl = "https://generativelanguage.googleapis.com/v1/models?key=" + apiKey;

        Request listRequest = new Request.Builder()
            .url(listModelsUrl)
            .get()
            .build();

        try (Response response = client.newCall(listRequest).execute()) {
            System.out.println("Status: " + response.code());
            System.out.println("Response: " + response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Test 2: Try a simple generation request
        System.out.println("\n=== Testing Generate Content with gemini-pro ===");
        String generateUrl = "https://generativelanguage.googleapis.com/v1/models/gemini-pro:generateContent?key=" + apiKey;

        String jsonBody = """
            {
              "contents": [{
                "parts":[{
                  "text": "Hello, how are you?"
                }]
              }]
            }
            """;

        RequestBody body = RequestBody.create(
            jsonBody,
            MediaType.parse("application/json")
        );

        Request genRequest = new Request.Builder()
            .url(generateUrl)
            .post(body)
            .build();

        try (Response response = client.newCall(genRequest).execute()) {
            System.out.println("Status: " + response.code());
            System.out.println("Response: " + response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

