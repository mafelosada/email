package com.example.emailSender.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class emailConfig {

    @Value("${spring.mail.host}")
    private String host;
    @Value("${spring.mail.username}")
    private String username;
    @Value("${spring.mail.password}")
    private String password;
    @Value("${spring.mail.port}")
    private String port;

    @Bean
    public JavaMailSender javaMailSender(){
        /*
         * Definimos e instanciamos un objeto de tipo JavaMailSenderImpl
         * Se realiza la configuración
         */
        JavaMailSenderImpl mailSender=new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setUsername(username);
        mailSender.setPassword(password);
        mailSender.setPort( Integer.parseInt(port));
        Properties properties=mailSender.getJavaMailProperties();
        properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
        return mailSender;

    }

}
