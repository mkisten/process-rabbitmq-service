package ru.ertelecom.rabbitmqservice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class FilterId {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int filterId;

    // Связь с основной сущностью RabbitMQMessage
    @ManyToOne
    @JoinColumn(name = "rabbitmqmessage_id")
    private RabbitMQMessage rabbitMQMessage;
}