package com.service.authentication.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class EmailKafkaProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendEmailVerification(String email, String token, UUID userId) {
        Map<String, Object> message = new HashMap<>();
        message.put("to", email);
        message.put("type", "EMAIL_VERIFICATION");
        message.put("token", token);
        message.put("userId", userId.toString());

        kafkaTemplate.send("email.register.verify", message);
    }

    public void sendForgotPassword(String email, String resetToken) {
        Map<String, Object> message = new HashMap<>();
        message.put("to", email);
        message.put("type", "PASSWORD_RESET");
        message.put("token", resetToken);

        kafkaTemplate.send("email.forgot.password", message);
    }
}
