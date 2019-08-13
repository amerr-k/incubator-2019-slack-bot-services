package com.welcome.bot.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.welcome.bot.domain.Invite;
import com.welcome.bot.domain.User;
import com.welcome.bot.domain.UserSettings;
import com.welcome.bot.exception.ResourceNotFoundException;
import com.welcome.bot.repository.InviteRepository;
import com.welcome.bot.repository.UserRepository;
@Service
public class InviteService {
	@Autowired
    private JavaMailSender javaMailSender;
	 @Autowired
	    private UserRepository userRepository;
	 @Autowired
	    private InviteRepository inviteRepository;
public boolean sendInvite(String email) {
	 try {
	        SimpleMailMessage msg = new SimpleMailMessage();
	        msg.setTo(email);
	        msg.setSubject("You've been addedd to NSoft Welcome Bot application");
	        msg.setText("Hello"+email+"\n You've been added to Nsoft Welcome Bot application. You can login to application here: http://nsoft.com/welcome-bot/login \n Please login with this email and google password.");
	        
	        javaMailSender.send(msg);
	        return true;
}
 catch (Exception e) {
    return false;

}
}
@Scheduled(fixedDelay = 900000)
public void scheduleFixedDelayTask() {
	List<User> users=userRepository.findAll();
    for(User user:users) {
    	if(!(user.getInvite().getSent())){
    		if(sendInvite(user.getEmail())) {
    			Invite invite2=inviteRepository.findById(user.getInvite().getId()).orElseThrow(() -> new ResourceNotFoundException("Invite", "id", user.getInvite().getId()));
    	    	invite2.setSent(true);
    	    	Invite result=inviteRepository.save(invite2);
    		}
    	}
    }
}
}