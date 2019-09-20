/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package test.apache.skywalking.testcase.pulsar.controller;


import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/case")
@PropertySource("classpath:application.properties")
public class CaseController {

    private Logger logger = LogManager.getLogger(CaseController.class);

    @Value("${service.url:pulsar://127.0.0.1:6650}")
    private String serviceUrl;

    private String topicName;

    @PostConstruct
    private void setUp() {
        topicName = "test";
    }

    @RequestMapping("/pulsar-case")
    @ResponseBody
    public String pulsarCase() throws PulsarClientException {
        PulsarClient pulsarClient = PulsarClient.builder()
                .serviceUrl(serviceUrl)
                .build();

        Producer<byte[]> producer = pulsarClient.newProducer()
                .topic(topicName)
                .create();

        Consumer<byte[]> consumer = pulsarClient.newConsumer()
                .topic(topicName)
                .subscriptionName("test")
                .subscribe();

        producer.newMessage()
                .key("testKey")
                .value(Integer.toString(1).getBytes())
                .property("TEST", "TEST")
                .send();

        Message<byte[]> msg = consumer.receive(3, TimeUnit.SECONDS);

        producer.close();
        consumer.close();

        if (msg != null) {
            logger.info("properties: {}", msg.getProperty("TEST"));
            logger.info("messageId = {}, key = {}, value = {}", msg.getMessageId(), msg.getKey(), new String(msg.getValue()));
            return String.format("Success, consumer received message with key=%s and value=%s", msg.getKey(), new String(msg.getValue()));
        } else {
            return "Failed, consumer can't receive the message in 3 seconds";
        }
    }
}
