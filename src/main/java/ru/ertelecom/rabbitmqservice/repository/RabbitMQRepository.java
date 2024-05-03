package ru.ertelecom.rabbitmqservice.repository;

import ru.ertelecom.rabbitmqservice.model.RabbitMQMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface RabbitMQRepository extends JpaRepository<RabbitMQMessage, Long> {

    List<RabbitMQMessage> findByStatus(String status);
}