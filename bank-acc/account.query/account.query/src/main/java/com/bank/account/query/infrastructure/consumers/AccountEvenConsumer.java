package com.bank.account.query.infrastructure.consumers;

import com.bank.account.common.events.AccountClosed;
import com.bank.account.common.events.AccountOpened;
import com.bank.account.common.events.FundWithdrawn;
import com.bank.account.common.events.FundsDeposited;
import com.bank.account.query.infrastructure.handlers.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;

public class AccountEvenConsumer implements EventConsumer{
    @Autowired
    private EventHandler eventHandler;

    @KafkaListener(topics = "AccountOpened", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(AccountOpened event, Acknowledgment ack) {
        eventHandler.on(event);
        ack.acknowledge();
    }

    @KafkaListener(topics = "AccountClosed", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(AccountClosed event, Acknowledgment ack) {
        eventHandler.on(event);
        ack.acknowledge();
    }

    @KafkaListener(topics = "FundWithdrawn", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(FundWithdrawn event, Acknowledgment ack) {
        eventHandler.on(event);
        ack.acknowledge();
    }

    @KafkaListener(topics = "FundsDeposited", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(FundsDeposited event, Acknowledgment ack) {
        eventHandler.on(event);
        ack.acknowledge();
    }
}
