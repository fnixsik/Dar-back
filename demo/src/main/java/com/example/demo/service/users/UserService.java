package com.example.demo.service.users;

import com.example.demo.entity.users.User;
import com.example.demo.repository.roles.UserRepository;
import com.example.demo.service.Email.EmailService;
import com.example.demo.service.redis.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OtpService otpService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    private final SecureRandom secureRandom = new SecureRandom();

    // Переименовали метод для ясности логики
    public void initiatePasswordReset(String loginOrEmail) {
        User user = userRepository.findByUsernameOrEmail(loginOrEmail, loginOrEmail)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        // Генерируем код
        String code = String.valueOf(100000 + secureRandom.nextInt(900000));

        // 3. Сохраняем в Redis
        otpService.saveCode(user.getEmail(), code);

        // 2. Отправляем реальное письмо
        emailService.sendOtpCode(user.getEmail(), code);

        // TODO: Здесь будет вызов emailService.send(user.getEmail(), code);
        System.out.println("Код сброса для " + user.getEmail() + ": " + code);

        // Исправление: метод должен возвращать void, так как
        // мы отправляем код на почту, а не пользователю в ответном JSON
    }

    @Transactional
    public void confirmResetAndChangePassword(String email, String userInputCode, String newPassword) {
        // 1. Пытаемся достать код из Redis
        String validCode = otpService.getCode(email);

        // 2. Проверка
        if (validCode == null) {
            throw new RuntimeException("Срок действия кода истек или код не запрашивался.");
        }

        if (!validCode.equals(userInputCode)) {
            throw new RuntimeException("Неверный код подтверждения.");
        }

        // 3. Поиск пользователя (здесь можно искать по email напрямую,
        // так как мы его уже знаем из метода initiatePasswordReset)
        User user = userRepository.findByUsernameOrEmail(email, email)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        // 4. Хэшируем новый пароль
        user.setPassword(passwordEncoder.encode(newPassword));

        userRepository.save(user);

        // 5. Удаляем код из Redis
        otpService.deleteCode(email);
    }

    public void initiateUserconfirm(String email, String jsonRequest ){
        // Генерируем уникальный ключ (UUID)
        String token = UUID.randomUUID().toString();

        otpService.saveCode(token, jsonRequest);

        emailService.sendRegistrationConfirmLink(email, token);
    }

    public String completeRegistration(String token) {
        // Ищем в Redis по ключу с префиксом
        String jsonRequest = otpService.getCode(token);

        if (jsonRequest == null) {
            throw new RuntimeException("Ссылка устарела или неверна"); //
        }

        // Удаляем из Redis после успеха
        otpService.deleteCode(token);

        return jsonRequest;
    }
}