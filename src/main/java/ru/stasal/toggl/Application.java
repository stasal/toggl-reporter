package ru.stasal.toggl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.stasal.toggl.api.DetailsReport;
import ru.stasal.toggl.api.TaskDetails;
import ru.stasal.toggl.report.WeeklyReport;
import ru.stasal.toggl.report.fill.DetailsReportFiller;
import ru.stasal.toggl.report.fill.ReportFiller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Stanislav Alekminskiy (al.stanislav@gmail.com), 06.02.2018
 */
@SpringBootApplication
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    private static final String TOGGL_API_URI = "https://toggl.com/reports/api/v2";
    private static final String TOGGL_SUMMARY_REPORT_URL = TOGGL_API_URI + "/summary";
    private static final String TOGGL_DETAILS_REPORT_URL = TOGGL_API_URI + "/details";
    private static final String API_TOKEN_AUTH_PASS = "api_token";

    public static void main(String args[]) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        objectMapper.registerModule(javaTimeModule);
        return objectMapper;
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder, @Value("${token}") String token) {
        return builder.basicAuthorization(token, API_TOKEN_AUTH_PASS).build();
    }

    @Bean(name = "togglUri")
    public UriComponentsBuilder togglUri(@Value("${workspace}") String workspaceId) {
        return UriComponentsBuilder.fromUriString(TOGGL_DETAILS_REPORT_URL)
                .queryParam("user_agent", "https://github.com/stasal/toggl-reporter")
                .queryParam("workspace_id", workspaceId);
    }

    @Bean
    public CommandLineRunner run(RestTemplate restTemplate, @Qualifier("togglUri") UriComponentsBuilder togglUriBuilder) {
        return args -> {
            List<TaskDetails> tasks = new ArrayList<>();
            int page = 0;
            ResponseEntity<DetailsReport> response;
            do {
                togglUriBuilder.replaceQueryParam("page", page++);
                response = restTemplate.getForEntity(
                        togglUriBuilder.build().toUri(),
                        DetailsReport.class
                );
                DetailsReport details = response.getBody();
                tasks.addAll(details.getTasks());
                // Toggl API constraint. No more than one request per second for token+IP address pair
                Thread.sleep(1000);
            } while (tasks.size() < response.getBody().getTotalCount());

            ReportFiller filler = new DetailsReportFiller(tasks);
            WeeklyReport report = new WeeklyReport();
            filler.fillReport(report);
//            export(report);
        };
    }
}
