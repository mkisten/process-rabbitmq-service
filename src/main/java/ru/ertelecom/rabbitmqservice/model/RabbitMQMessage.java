package ru.ertelecom.rabbitmqservice.model;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.List;



@Data
@Entity
public class RabbitMQMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private Action action;

    @OneToOne(cascade = CascadeType.ALL)
    private Incident incident;

    @OneToMany(mappedBy = "rabbitMQMessage", cascade = CascadeType.ALL)
    private List<FilterId> filterIds;

    private String jsonData;
    private LocalDateTime parsedDt;
    private String status;
}