package edu.pkch.batch.components;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SlackNotifier {

    public void notify(String to) {
        log.info("send slack notification to {}", to);
    }
}
