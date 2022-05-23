package ru.mirea.paidClinicApplication.aspects.emailAspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.mirea.paidClinicApplication.entities.appUser.AppUser;
import ru.mirea.paidClinicApplication.entities.procedure.Procedure;
import ru.mirea.paidClinicApplication.services.mail.EmailService;

import javax.annotation.PostConstruct;

@Component
@org.aspectj.lang.annotation.Aspect
public class EmailAspect {

    private final ApplicationContext applicationContext;
    private EmailService emailService;

    @Autowired
    public EmailAspect(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void setEmailService() { this.emailService = applicationContext.getBean(EmailService.class); }

    @Before("sendMailReg()")
    public void emailSendReg(JoinPoint joinPoint) {
        AppUser appUser = (AppUser) joinPoint.getArgs()[0];
        emailService.sendNotification(
                "СПАСИБО ЗА РЕГИСТРАЦИЮ",
                appUser.getEmail()
        );
    }

    @Before("sendMailProc()")
    public void emailSendProc(JoinPoint joinPoint) {
        AppUser appUser = (AppUser) joinPoint.getArgs()[0];
        Procedure procedure = (Procedure) joinPoint.getArgs()[1];

        emailService.sendNotification(
                "СОЗДАНА НОВАЯ ЗАПИСЬ НА ПРОЦЕДУРУ: " + procedure.getDescription(),
                appUser.getEmail()
        );
    }

    @Pointcut("@annotation(ru.mirea.paidClinicApplication.annotations.mailReg.SendMailReg)")
    public void sendMailReg() {}

    @Pointcut("@annotation(ru.mirea.paidClinicApplication.annotations.mailProc.SendMailProc)")
    public void sendMailProc() {}
}
