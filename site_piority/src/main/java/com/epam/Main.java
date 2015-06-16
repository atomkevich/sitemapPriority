package com.epam;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by alina on 15.6.15.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        ApplicationContext ctx =
                new AnnotationConfigApplicationContext(SpringMongoConfig.class);
        MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");
        Query searchUserQuery = new Query(Criteria.where("status").is(200));
        long count = mongoOperation.count(searchUserQuery, "urlStats");
        int threadCount = Math.round(count/100) + 1;
        ExecutorService executor = Executors.newFixedThreadPool((int)threadCount);
        for (int i = 1; i <= threadCount; i++) {
            Query limitQuery = searchUserQuery.skip((i-1)* 100).limit(100);
            List<UrlStats> validUrl = mongoOperation.find(limitQuery, UrlStats.class);
            WorkerThread workerThread = new WorkerThread(validUrl, mongoOperation);
            executor.execute(workerThread);
        }
    }
}
