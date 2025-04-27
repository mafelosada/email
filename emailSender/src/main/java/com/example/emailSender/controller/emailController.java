package com.example.emailSender.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.emailSender.services.emailService;

@CrossOrigin(origins = "http://127.0.0.1:5500") 
@RestController
public class emailController {

    @Autowired
    private emailService emailService;

    @GetMapping("/newAccount/{email}/{username}")
    public String sendNewAccount(@PathVariable String email, @PathVariable String username) {
        emailService.sendNewAccountEmail(email, username);
        return "Correo de nueva cuenta enviado a " + email;
    }

    @GetMapping("/forgotPassword/{email}/{username}")
    public String sendForgotPassword(@PathVariable String email, @PathVariable String username) {
        emailService.sendForgotPasswordEmail(email, username);
        return "Correo de recuperación enviado a " + email;
    }

    @GetMapping("/sendActivationEmail/{email}/{username}")
    public String sendActivationEmail(@PathVariable String email, @PathVariable String username) {
        String activationCode = emailService.generateActivationCode();
        emailService.sendActivationEmail(email, activationCode);
        return "Correo de activación enviado a " + email;
    }
    @GetMapping("/passwordChanged/{email}/{username}")
    public String sendPasswordChangedEmail(@PathVariable String email, @PathVariable String username) {
        emailService.sendPasswordChangedEmail(email, username);
        return "Correo de notificación de cambio de contraseña enviado a " + email;
    }

    @GetMapping("/lowStock/{email}/{productName}/{currentStock}")
    public String sendLowStockEmail(@PathVariable String email, @PathVariable String productName, @PathVariable int currentStock) {
        emailService.sendLowStockNotification(email, productName, currentStock);
        return "Correo de notificación de bajo stock enviado a " + email;
    }

    @GetMapping("/purchaseNotification/{email}/{productList}")
    public String sendPurchaseNotification(@PathVariable String email, @PathVariable String productList) {
        emailService.sendPurchaseNotification(email, productList);
        return "Correo de notificación de compra enviado a " + email;
    }

   


}
