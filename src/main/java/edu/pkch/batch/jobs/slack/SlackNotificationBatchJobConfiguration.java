package edu.pkch.batch.jobs.slack;

import edu.pkch.batch.components.SlackNotifier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(name = "spring.batch.job.names", havingValue = SlackNotificationBatchJobConfiguration.JOB_NAME)
public class SlackNotificationBatchJobConfiguration {
    public static final String JOB_NAME = "slackNotificationBatchJob";
    public static final String STEP_NAME = "slackNotificationStep";
    public static final String TASKLET_NAME = STEP_NAME + "-tasklet";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final SlackNotifier slackNotifier;

    @Bean(JOB_NAME)
    public Job slackNotificationBatchJob(Step slackNotificationStep) {
        return jobBuilderFactory.get(JOB_NAME)
                .start(slackNotificationStep)
                .incrementer(new RunIdIncrementer())
                .build();
    }

    @Bean(STEP_NAME)
    @JobScope
    public Step slackNotificationStep(@Qualifier(TASKLET_NAME) Tasklet notifier) {
        return stepBuilderFactory.get(STEP_NAME)
                .tasklet(notifier)
                .build();
    }

    @Bean(TASKLET_NAME)
    @StepScope
    public Tasklet notifyTasklet(@Value("#{jobParameters[to]}") String to) {
        return (contribution, chunkContext) -> {
            try {
                slackNotifier.notify(to);
                return RepeatStatus.FINISHED;
            } catch (Exception e) {
                log.error("slack notifier error occured. to: {}, message: {}", to, e.getMessage(), e);
                throw e;
            }
        };
    }
}
