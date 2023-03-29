package com.bank.account.query.infrastructure.consumers;

import com.bank.account.common.events.AccountClosed;
import com.bank.account.common.events.AccountOpened;
import com.bank.account.common.events.FundWithdrawn;
import com.bank.account.common.events.FundsDeposited;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;

public interface EventConsumer {
    void consume(@Payload AccountOpened event, Acknowledgment ack);
    void consume(@Payload AccountClosed event, Acknowledgment ack);
    void consume(@Payload FundWithdrawn event, Acknowledgment ack);
    void consume(@Payload FundsDeposited event, Acknowledgment ack);
}
