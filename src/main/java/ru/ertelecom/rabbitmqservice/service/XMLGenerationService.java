package ru.ertelecom.rabbitmqservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import ru.ertelecom.rabbitmqservice.model.Action;
import ru.ertelecom.rabbitmqservice.model.RabbitMQMessage;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

@Service
public class XMLGenerationService {

    private static final Logger logger = LoggerFactory.getLogger(DataParserService.class);

    public String generateXML(RabbitMQMessage message) {
        try {
            // Создаем построитель документов
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // Создаем новый документ XML
            Document doc = docBuilder.newDocument();

            // Создаем корневой элемент
            Element rootElement = doc.createElement("RabbitMQMessage");
            doc.appendChild(rootElement);

            // Добавляем поля в XML
            Element id = doc.createElement("id");
            id.appendChild(doc.createTextNode(String.valueOf(message.getId())));
            rootElement.appendChild(id);

            Element action = doc.createElement("action");
            Action actionObject = message.getAction();
            action.appendChild(doc.createTextNode(actionObject.getAction()));
            rootElement.appendChild(action);

            Element incident = doc.createElement("incident");
            rootElement.appendChild(incident);

            // Добавляем поля инцидента
            Element incidentId = doc.createElement("incidentId");
            incidentId.appendChild(doc.createTextNode(String.valueOf(message.getIncident().getIncidentId())));
            incident.appendChild(incidentId);

            // Преобразуем документ в строку XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));

            return writer.toString();
        } catch (Exception e) {
            logger.error("Error generating XML: {}", e.getMessage(), e);
            // Вернуть null или какое-то сообщение об ошибке
            return null;
        }
    }
}
