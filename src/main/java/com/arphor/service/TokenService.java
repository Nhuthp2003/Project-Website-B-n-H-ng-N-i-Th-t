package com.arphor.service;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;

import org.springframework.stereotype.Service;

import com.arphor.entity.Token;

@Service
public class TokenService {

	private Map<String, Long> tokenMap = new ConcurrentHashMap<>();
    private static final long TOKEN_VALID_DURATION = 60 * 1000; // 1 minute

    public Token generateToken(String email) {
        String tokenValue = generateRandomToken();
        sendTokenByEmail(email, tokenValue);
        long currentTime = System.currentTimeMillis();
        tokenMap.put(tokenValue, currentTime);
        return new Token(email, tokenValue);
    }

    public boolean validateToken(String tokenValue) {
        if (tokenMap.containsKey(tokenValue)) {
            long tokenTime = tokenMap.get(tokenValue);
            long currentTime = System.currentTimeMillis();
            long tokenAge = currentTime - tokenTime;
            if (tokenAge <= TOKEN_VALID_DURATION) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isTokenExpired(String tokenValue) {
        if (tokenMap.containsKey(tokenValue)) {
            long tokenTime = tokenMap.get(tokenValue);
            long currentTime = System.currentTimeMillis();
            long tokenAge = currentTime - tokenTime;
            return tokenAge > TOKEN_VALID_DURATION;
        }
        return false;
    }

	private String generateRandomToken() {
		Random random = new Random();
		int token = 100000 + random.nextInt(900000);
		return String.valueOf(token);
	}

	private void sendTokenByEmail(String email, String token) {
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");

		String username = "nhatquang45013@gmail.com";
		String password = "kwii xsdb oknc rhef";

		Session session = Session.getInstance(properties, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
			message.setSubject("Your Token");
			message.setText("Your token is: " + token);

			Transport.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
