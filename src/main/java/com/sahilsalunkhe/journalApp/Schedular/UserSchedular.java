package com.sahilsalunkhe.journalApp.Schedular;

import com.sahilsalunkhe.journalApp.entities.JournalEntry;
import com.sahilsalunkhe.journalApp.entities.User;
import com.sahilsalunkhe.journalApp.repositories.UserRepositories;
import com.sahilsalunkhe.journalApp.repositories.UserRepositoryImpl;
import com.sahilsalunkhe.journalApp.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class UserSchedular {

    @Autowired
    UserRepositoryImpl userRepository;

    @Autowired
    EmailService emailService;

    @Scheduled(cron = "0 25 22 * * ?",zone = "Asia/Kolkata")

    public  void fetchUserAndSendMail(){
        List<User> users=userRepository.getUserForSA();
        for (User user:users){
            List<JournalEntry> journalEntries=user.getJournalEntries();
            List<JournalEntry> last7days = journalEntries.stream()
                    .filter(Objects::nonNull)                 // 👈 NEW: skip null journals
                    .filter(journal -> journal.getDate() != null)
                    .filter(journal -> {
                        LocalDate date = journal.getDate().toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate();
                        return !date.isBefore(LocalDate.now().minusDays(7));
                    })
                    .collect(Collectors.toList());


            String msg=last7days.stream()
                    .map(journal->
                           "Date :" +journal.getDate()
                                   + "\nTitle: " + journal.getTitle()
                                   + "\nEntry: " + journal.getContent() + "\n")
                    .collect(Collectors.joining("\n---------------------------\n"));

            emailService.sendMail("sahilasalunkhe007@gmail.com","SentimentAnalysis",msg);


        }
    }
}
