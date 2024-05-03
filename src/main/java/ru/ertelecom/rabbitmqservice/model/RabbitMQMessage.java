package ru.ertelecom.rabbitmqservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
