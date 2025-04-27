package com.example.emailSender.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class emailService {

    @Autowired
    private JavaMailSender javaMailSender;

    // 🔹New account
    public void sendNewAccountEmail(String addressMail, String username) {
        try {
            String subject = "¡Bienvenido!";
            String body = "<h2>Hola " + username + ",</h2>"
                        + "<p>Nos alegra que te hayas registrado.</p>"
                        + "<p>Ahora puedes acceder y disfrutar de todas las funcionalidades que tenemos para ti.</p>"
                        + "<p><strong>¡Esperamos que disfrutes la experiencia!</strong></p>";
            emailSender(addressMail, subject, body, true); // true = es HTML
        } catch (MessagingException e) {
            System.out.println("Error al enviar correo de nueva cuenta: " + e.getMessage());
        }
    }

    // 🔹Forgot password
    public void sendForgotPasswordEmail(String addressMail, String username) {
        try {
            String subject = "Recuperación de contraseña";
            String resetLink = "https://example.com/reset-password?user=" + username; // 🔗 Link de ejemplo
            String body = "<h2>Hola " + username + ",</h2>"
                        + "<p>Recibimos una solicitud para restablecer tu contraseña.</p>"
                        + "<p>Para cambiar tu contraseña, haz clic en el siguiente enlace:</p>"
                        + "<p><a href='" + resetLink + "' style='display:inline-block;padding:10px 20px;background-color:#4CAF50;color:white;text-decoration:none;border-radius:5px;'>Restablecer Contraseña</a></p>"
                        + "<p>Si no solicitaste este cambio, puedes ignorar este mensaje.</p>"
                        + "<br><p>¡Gracias por confiar en nosotros!</p>";
            emailSender(addressMail, subject, body, true);
        } catch (MessagingException e) {
            System.out.println("Error al enviar correo de recuperación: " + e.getMessage());
        }
    }
    
     public String generateActivationCode() {
        return UUID.randomUUID().toString();
    }

    // 🔹Activation email
    public void sendActivationEmail(String addressMail, String activationCode) {
        try {
            String subject = "Activación de Cuenta";
            String activationLink = "http://example.com/activate?code=" + activationCode; // URL de activación
            String body = "<h2>Hola,</h2>"
                        + "<p>Gracias por registrarte.</p>"
                        + "<p>Para activar tu cuenta, haz clic en el siguiente enlace:</p>"
                        + "<p><a href='" + activationLink + "' style='display:inline-block;padding:10px 20px;background-color:#4CAF50;color:white;text-decoration:none;border-radius:5px;'>Activar Cuenta</a></p>"
                        + "<p>Si no te registraste, por favor ignora este mensaje.</p>"
                        + "<br><p>¡Esperamos que disfrutes la experiencia!</p>";
            emailSender(addressMail, subject, body, true); // Enviar correo HTML
        } catch (MessagingException e) {
            System.out.println("Error al enviar correo de activación: " + e.getMessage());
        }
    }

    // 🔹Password changed email
    public void sendPasswordChangedEmail(String addressMail, String username) {
        try {
            String subject = "Cambio de Contraseña Exitoso";
            String body = "<h2>Hola " + username + ",</h2>"
                        + "<p>Tu contraseña ha sido cambiada exitosamente.</p>"
                        + "<p>Si no realizaste este cambio, por favor contáctanos inmediatamente.</p>"
                        + "<br><p>¡Gracias por utilizar nuestros servicios!</p>";
            emailSender(addressMail, subject, body, true); // Enviar correo HTML
        } catch (MessagingException e) {
            System.out.println("Error al enviar correo de cambio de contraseña: " + e.getMessage());
        }
    }

    // 🔹Low stock notification
    public void sendLowStockNotification(String addressMail, String productName, int currentStock) {
        try {
            String subject = "Notificación de Bajo Stock: " + productName;
            String body = "<h2>Atención,</h2>"
                        + "<p>El producto <strong>" + productName + "</strong> tiene un stock bajo de <strong>" + currentStock + " unidades</strong>.</p>"
                        + "<p>Te recomendamos realizar un pedido para reabastecer el inventario.</p>"
                        + "<br><p>¡Gracias por mantener el inventario actualizado!</p>";
            emailSender(addressMail, subject, body, true); // Enviar correo HTML
        } catch (MessagingException e) {
            System.out.println("Error al enviar correo de bajo stock: " + e.getMessage());
        }
    }

    // 🔹Purchase notification
    public void sendPurchaseNotification(String email, String productList) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Notificación de Compra");
        message.setText(generatePurchaseMessage(productList));

        javaMailSender.send(message);
    }

    // Método para generar el mensaje de compra
    private String generatePurchaseMessage(String productList) {
        StringBuilder message = new StringBuilder();
        message.append("Estimado cliente,\n\n");
        message.append("Gracias por su compra. Aquí está la lista de productos adquiridos:\n\n");
        message.append(productList);  // La lista de productos que se pasa desde el controlador
        message.append("\n\n¡Gracias por su compra!");

        return message.toString();
    }


    public boolean emailSender(String addressMail, String subject, String bodyMail) throws MessagingException {
        return emailSender(addressMail, subject, bodyMail, false); // false = texto plano
    }

    // 🔹 Sobrecarga para enviar HTML si se necesita
    public boolean emailSender(String addressMail, String subject, String bodyMail, boolean isHtml) throws MessagingException {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(addressMail);
            helper.setSubject(subject);
            helper.setText(bodyMail, isHtml); // permite HTML si isHtml = true
            javaMailSender.send(message);
            System.out.println("Correo enviado a: " + addressMail);
            return true;
        } catch (MessagingException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
