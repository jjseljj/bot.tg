package bot.tg.bot.tg;
/*
import org.apache.commons.codec.digest.DigestUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class AuthLinkGenerator {

    private static final String BOT_TOKEN = "5688493107:AAHb2EJs-oNMCxYurZfPE2xmWwW5fi6G1Cs";
    private static final String BOT_USERNAME = "XenodochialBotovskiBot";
    private String secretKey;
    private static final String AUTH_URL = "https://example.com/auth";
    private static final ObjectMapper mapper = new ObjectMapper();

    public AuthLinkGenerator(String secretKey) {
        this.secretKey = secretKey;
    }
    public static String generateAuthLink() {
        String state = generateState();
        String encodedBotUsername = URLEncoder.encode(BOT_USERNAME, StandardCharsets.UTF_8);
        String encodedAuthUrl = URLEncoder.encode(AUTH_URL, StandardCharsets.UTF_8);
        String encodedState = URLEncoder.encode(state, StandardCharsets.UTF_8);

        String query = String.format("bot_username=%s&auth_url=%s&state=%s", encodedBotUsername, encodedAuthUrl, encodedState);
        String hash = generateHash(query, secretKey);
        String encodedHash = URLEncoder.encode(hash, StandardCharsets.UTF_8);

        return String.format("https://telegram.org/auth?%s&hash=%s", query, encodedHash);
    }
    private static String generateHash(String query, String secretKey) {
        String text = query + secretKey;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] sha1hash = md.digest(text.getBytes());
            return bytesToHex(sha1hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte aByte : bytes) {
            String hex = Integer.toHexString(0xff & aByte);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }


    private static String generateState() {
        try {
            Map<String, String> stateData = new HashMap<>();
            stateData.put("bot_token", BOT_TOKEN);
            String stateJson = mapper.writeValueAsString(stateData);
            String encodedStateJson = Base64.getUrlEncoder().encodeToString(stateJson.getBytes(StandardCharsets.UTF_8));
            return encodedStateJson;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String parseAuthResponse(String payload) {
        try {
            String decodedPayload = new String(Base64.getUrlDecoder().decode(payload), StandardCharsets.UTF_8);
            Map<String, String> payloadMap = mapper.readValue(decodedPayload, Map.class);
            String token = payloadMap.get("token");
            return token;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

 */