package com.github.vaatech.aom.core.repository;

import com.github.vaatech.aom.BaseApplicationTest;
import com.github.vaatech.aom.test.HibernateStatisticsAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class MakerRepositoryTest extends BaseApplicationTest {

    @Autowired
    MakerRepository makerRepository;

    @Test
    @HibernateStatisticsAssertions(queryCount = 0)
    void findAll() {
        int a= 5;

    }
}