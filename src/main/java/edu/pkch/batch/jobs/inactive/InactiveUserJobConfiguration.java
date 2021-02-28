package edu.pkch.batch.jobs.inactive;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Configuration
@ConditionalOnProperty(name = "spring.batch.job.names", havingValue = InactiveUserJobConfiguration.JOB_NAME)
@RequiredArgsConstructor
public class InactiveUserJobConfiguration {
    public static final String JOB_NAME = "inactiveUserBatchJob";
    public static final String STEP_NAME = "inactiveUserBatchStep";
    public static final String READER_NAME = STEP_NAME + "-reader";
    public static final String PROCESSOR_NAME = STEP_NAME + "-processor";
    public static final String WRITER_NAME = STEP_NAME + "-writer";

    private int chunkSize;
    private int count;

    public static List<String> RAW_NUMBERS = IntStream.rangeClosed(1, 1000)
            .mapToObj(String::valueOf)
            .collect(Collectors.toList());

    @Value("${chunkSize:25}")
    public void setChunkSize(int chunkSize) {
        this.chunkSize = chunkSize;
    }

    @Bean(JOB_NAME)
    public Job inactiveUserBatchJob(JobBuilderFactory jobBuilderFactory,
                                    @Qualifier(STEP_NAME) Step inactiveUserBatchStep) {
        return jobBuilderFactory.get(JOB_NAME)
                .preventRestart()
                .incrementer(new RunIdIncrementer())
                .start(inactiveUserBatchStep)
                .build();
    }

    @Bean(STEP_NAME)
    public Step inactiveUserBatchStep(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get(STEP_NAME)
                .<String, Integer>chunk(chunkSize)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean(READER_NAME)
    public ItemReader<String> reader() {
        return () -> {
            count += 1;
            return RAW_NUMBERS.remove(0);
        };
    }

    @Bean(PROCESSOR_NAME)
    public ItemProcessor<String, Integer> processor() {
        return Integer::parseInt;
    }

    @Bean(WRITER_NAME)
    public ItemWriter<Integer> writer() {
        return System.out::println;
    }
}
