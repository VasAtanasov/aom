package com.github.vaatech.aom.test;

import org.hibernate.SessionFactory;
import org.hibernate.stat.CollectionStatistics;
import org.hibernate.stat.EntityStatistics;
import org.hibernate.stat.QueryStatistics;
import org.hibernate.stat.Statistics;

import jakarta.persistence.EntityManager;

import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.ToLongFunction;
import java.util.stream.Stream;

import static java.lang.String.format;
import static java.util.Comparator.reverseOrder;
import static java.util.stream.Collectors.joining;

public final class HibernateStatisticsHelper {

    private record Tuple<T1, T2>(T1 _1, T2 _2) implements Comparable<Tuple<T1, T2>> {

        @Override
        public int compareTo(Tuple<T1, T2> that) {
            return Tuple.compareTo(this, that);
        }

        @SuppressWarnings("unchecked")
        private static <U1 extends Comparable<? super U1>, U2 extends Comparable<? super U2>>
        int compareTo(Tuple<?, ?> o1, Tuple<?, ?> o2) {
            final Tuple<U1, U2> t1 = (Tuple<U1, U2>) o1;
            final Tuple<U1, U2> t2 = (Tuple<U1, U2>) o2;

            final int check1 = t1._1.compareTo(t2._1);
            if (check1 != 0) {
                return check1;
            }
            return t1._2.compareTo(t2._2);
        }

        static <T1, T2> Tuple<T1, T2> of(T1 _1, T2 _2) {
            return new Tuple<>(_1, _2);
        }
    }

    private static final Comparator<Tuple<String, Long>> STRING_LONG_PAIR_COMPARATOR =
            Comparator.<Tuple<String, Long>>comparingLong(Tuple::_2).reversed().thenComparing(Tuple::_1);

    public static Statistics getStatistics(final EntityManager entityManager) {
        return entityManager.getEntityManagerFactory().unwrap(SessionFactory.class).getStatistics();
    }

    public static void assertCurrentQueryCount(final Statistics stats, final int expectedQueryCount) {
        final long totalQueryCount = getTotalQueryCount(stats);

        if (totalQueryCount != expectedQueryCount) {
            throw new HibernateStatisticsAssertionException(
                    format(
                            "Expected exactly %d queries, but got: %d\n  %s\n",
                            expectedQueryCount, totalQueryCount, toString(stats)));
        }
    }

    public static void assertCurrentQueryCountAtMost(
            final Statistics statistics, final int expectedMaxQueryCount) {
        final long totalQueryCount = getTotalQueryCount(statistics);

        if (totalQueryCount > expectedMaxQueryCount) {
            throw new HibernateStatisticsAssertionException(
                    format(
                            "Expected at most %d queries, but got: %d\n  %s\n",
                            expectedMaxQueryCount, totalQueryCount, toString(statistics)));
        }
    }

    public static void assertQueryCount(
            final Statistics statistics, final int expectedQueryCount, final Runnable task) {
        statistics.clear();
        task.run();
        assertCurrentQueryCount(statistics, expectedQueryCount);
    }

    public static void assertMaxQueryCount(
            final Statistics statistics, final int expectedMaxQueryCount, final Runnable task) {
        statistics.clear();
        task.run();
        assertCurrentQueryCountAtMost(statistics, expectedMaxQueryCount);
    }

    private static long getTotalQueryCount(final Statistics stats) {
        return Math.max(stats.getQueryExecutionCount(), stats.getPrepareStatementCount());
    }

    public static String toString(final Statistics stats) {
        final StringBuilder msgBuf =
                new StringBuilder("Prepared statements: ").append(stats.getPrepareStatementCount());

        appendEntityStatisticIfPresent(msgBuf, "loads", stats, EntityStatistics::getLoadCount);
        appendEntityStatisticIfPresent(msgBuf, "fetches", stats, EntityStatistics::getFetchCount);
        appendEntityStatisticIfPresent(msgBuf, "inserts", stats, EntityStatistics::getInsertCount);
        appendEntityStatisticIfPresent(msgBuf, "updates", stats, EntityStatistics::getUpdateCount);
        appendEntityStatisticIfPresent(msgBuf, "deletes", stats, EntityStatistics::getDeleteCount);

        appendCollectionStatisticIfPresent(msgBuf, "loads", stats, CollectionStatistics::getLoadCount);
        appendCollectionStatisticIfPresent(
                msgBuf, "fetches", stats, CollectionStatistics::getFetchCount);
        appendCollectionStatisticIfPresent(
                msgBuf, "recreates", stats, CollectionStatistics::getRecreateCount);
        appendCollectionStatisticIfPresent(
                msgBuf, "updates", stats, CollectionStatistics::getUpdateCount);
        appendCollectionStatisticIfPresent(
                msgBuf, "removes", stats, CollectionStatistics::getRemoveCount);

        appendQueryStatisticsIfPresent(msgBuf, stats);

        return msgBuf.toString();
    }

    private static void appendEntityStatisticIfPresent(
            final StringBuilder buf,
            final String statisticNamePlural,
            final Statistics stats,
            final ToLongFunction<EntityStatistics> extractor) {

        final Tuple<Long, String> statisticCount = getEntityStatisticLine(stats, extractor);

        if (statisticCount._1 > 0) {
            buf.append("\n  Entity ")
                    .append(statisticNamePlural)
                    .append(" (")
                    .append(statisticCount._1)
                    .append("): ")
                    .append(statisticCount._2);
        }
    }

    private static Tuple<Long, String> getEntityStatisticLine(
            final Statistics statistics, final ToLongFunction<EntityStatistics> extractor) {

        return aggregate(
                Arrays.stream(statistics.getEntityNames())
                        .map(
                                entityName -> {
                                    final EntityStatistics entityStats = statistics.getEntityStatistics(entityName);
                                    final String className = entityName.substring(entityName.lastIndexOf('.') + 1);

                                    return Tuple.of(className, extractor.applyAsLong(entityStats));
                                }));
    }

    private static void appendCollectionStatisticIfPresent(
            final StringBuilder buf,
            final String statisticNamePlural,
            final Statistics stats,
            final ToLongFunction<CollectionStatistics> extractor) {

        final Tuple<Long, String> statisticCount = getCollectionStatisticLine(stats, extractor);

        if (statisticCount._1 > 0) {
            buf.append("\n  Collection ")
                    .append(statisticNamePlural)
                    .append(" (")
                    .append(statisticCount._1)
                    .append("): ")
                    .append(statisticCount._2);
        }
    }

    private static Tuple<Long, String> getCollectionStatisticLine(
            final Statistics statistics, final ToLongFunction<CollectionStatistics> extractor) {

        return aggregate(
                Arrays.stream(statistics.getCollectionRoleNames())
                        .map(
                                collectionRoleName -> {
                                    final CollectionStatistics collStats =
                                            statistics.getCollectionStatistics(collectionRoleName);
                                    final String[] words = collectionRoleName.split("\\.");

                                    final String shortName =
                                            words.length >= 2
                                                    ? words[words.length - 2] + "." + words[words.length - 1]
                                                    : collectionRoleName;

                                    return Tuple.of(shortName, extractor.applyAsLong(collStats));
                                }));
    }

    private static void appendQueryStatisticsIfPresent(
            final StringBuilder buf, final Statistics stats) {
        final String queryLines =
                Arrays.stream(stats.getQueries())
                        .map(
                                query -> {
                                    final QueryStatistics queryStats = stats.getQueryStatistics(query);
                                    return Tuple.of(queryStats.getExecutionCount(), query);
                                })
                        .sorted(reverseOrder())
                        .map(pair -> String.format("%s%s: %s", " ".repeat(3), pair._1.toString(), pair._2))
                        .collect(joining("\n  "));

        if (!queryLines.isEmpty()) {
            buf.append("\n  Queries (ordered by execution count): ")
                    .append(stats.getQueryExecutionCount())
                    .append("\n  ")
                    .append(queryLines);
        }
    }

    private static Tuple<Long, String> aggregate(final Stream<Tuple<String, Long>> tuples) {
        final AtomicLong total = new AtomicLong(0L);

        final String statisticLine =
                tuples
                        .filter(pair -> pair._2 > 0)
                        .sorted(STRING_LONG_PAIR_COMPARATOR)
                        .map(
                                pair -> {
                                    total.getAndAdd(pair._2);
                                    return format("%s=%d", pair._1, pair._2);
                                })
                        .collect(joining(", "));

        return Tuple.of(total.get(), statisticLine);
    }

    private HibernateStatisticsHelper() {
        throw new AssertionError();
    }
}
