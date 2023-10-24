package cdw.springtraining.gatekeeper.scheduler;

import cdw.springtraining.gatekeeper.dao.TokenRepository;
import cdw.springtraining.gatekeeper.entity.Token;
import cdw.springtraining.gatekeeper.service.JwtServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class TokenScheduler {

    @Autowired
    private JwtServiceImpl jwtService;

    @Autowired
    private TokenRepository tokenRepository;


    @Scheduled(fixedDelay = 3 * 60 * 60 * 1000)
    public void scheduler() throws InterruptedException {
        List<Token> tokenList = tokenRepository.findAll();
        List<Token> tokenToDelete=new ArrayList<>();
        for (Token tokens : tokenList) {
            long remainingTime = jwtService.extractRemainingTime(tokens.getTokenName());
            if (remainingTime <= 0) {
                tokenToDelete.add(tokens);
            }
        }

        List<Integer> deleteIds=tokenToDelete.stream()
                .map(Token::getTokenId)
                .collect(Collectors.toList());

        if(!deleteIds.isEmpty()){
            tokenRepository.deleteAllByIdInBatch(deleteIds);
        }

    }
}
