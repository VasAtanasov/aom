package com.github.vaatech.aom.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@PropertySource("classpath:git.properties")
@ConfigurationProperties(prefix = "git")
public class GitPropertySource {
    private String branch;
    private Commit commit;
    private Build build;

    @Data
    public static class Commit {
        private Id id;
        private User user;
        private Message message;
        private String time;

        @Data
        public static class Id {
            private String full;
            private String describe;
            private String abbrev;
        }
    }

    @Data
    public static class Build {
        private User user;
        private String time;
    }

    @Data
    public static class User {
        private String email;
        private String name;
    }

    @Data
    public static class Message {
        private String full;
        private String shortMessage;

        public void setShort(String shortMessage) {
            this.shortMessage = shortMessage;
        }

        public String getShort() {
            return shortMessage;
        }
    }
}
