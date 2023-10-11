package com.github.vaatech.aom.rest.controller;

import com.github.vaatech.aom.config.properties.GitPropertySource;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/revision")
public class GitRevisionController {

    @Data
    @JsonInclude
    public static class GitRepositoryState {

        private String branch; // =${git.branch}
        private String describe; // =${git.commit.id.describe}
        private String commitId; // =${git.commit.id}
        private String buildUserName; // =${git.build.user.name}
        private String buildUserEmail; // =${git.build.user.email}
        private String buildTime; // =${git.build.time}
        private String commitUserName; // =${git.commit.user.name}
        private String commitUserEmail; // =${git.commit.user.email}
        private String commitMessageFull; // =${git.commit.message.full}
        private String commitMessageShort; // =${git.commit.message.short}
        private String commitTime; // =${git.commit.time}

        public GitRepositoryState(GitPropertySource propertySource) {
            this.branch = propertySource.getBranch();
            this.describe = propertySource.getCommit().getId().getDescribe();
            this.commitId = propertySource.getCommit().getId().getFull();
            this.commitUserName = propertySource.getCommit().getUser().getName();
            this.commitUserEmail = propertySource.getCommit().getUser().getEmail();
            this.commitTime = propertySource.getCommit().getTime();
            this.commitMessageShort = propertySource.getCommit().getMessage().getShort();
            this.commitMessageFull = propertySource.getCommit().getMessage().getFull();
            this.buildUserName = propertySource.getBuild().getUser().getName();
            this.buildUserEmail = propertySource.getBuild().getUser().getEmail();
            this.buildTime = propertySource.getBuild().getTime();
        }
    }

    public GitRevisionController(GitPropertySource propertySource) {
        this.propertySource = propertySource;
    }

    private final GitPropertySource propertySource;

    @GetMapping
    public GitRepositoryState checkGitRevision() {
        return new GitRepositoryState(propertySource);
    }
}
