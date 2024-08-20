package com.example.springboot;

import com.example.springboot.entity.WikimediaData;
import com.example.springboot.repository.WikimediaDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaWikimediaConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaWikimediaConsumer.class);

    private WikimediaDataRepository dataRepository;

    public KafkaWikimediaConsumer(WikimediaDataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    //@kafkaListener is an message listener bean managed by spring
    @KafkaListener(topics = "wikimedia-recentchanges", groupId = "testGroup")
    public void consume(String eventMessage) {

        LOGGER.info(String.format("Event Message received -> %s", eventMessage));

        WikimediaData wikimediaData = new WikimediaData();
        wikimediaData.setWikiEventData(eventMessage);

        dataRepository.save(wikimediaData);

    }
}
