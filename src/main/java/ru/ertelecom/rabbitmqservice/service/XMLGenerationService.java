package ru.ertelecom.rabbitmqservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import ru.ertelecom.rabbitmqservice.model.Action;
import ru.ertelecom.rabbitmqservice.model.Incident;
import ru.ertelecom.rabbitmqservice.model.RabbitMQMessage;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

@Slf4j
@Service
public class XMLGenerationService {

    public String generateXML(RabbitMQMessage message) {
        try {
            if (message == null) {
                throw new IllegalArgumentException("Message object cannot be null");
            }

            // Создаем построитель документов
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // Создаем новый документ XML
            Document doc = docBuilder.newDocument();

            // Создаем корневой элемент
            Element rootElement = doc.createElement("RabbitMQMessage");
            doc.appendChild(rootElement);

            // Добавляем поля в XML
            addElement(doc, rootElement, "id", String.valueOf(message.getId()));

            Action actionObject = message.getAction();
            if (actionObject != null) {
                addElement(doc, rootElement, "action", actionObject.getAction());
            }

            Incident incident = message.getIncident();
            if (incident != null) {
                Element incidentElement = doc.createElement("incident");
                rootElement.appendChild(incidentElement);

                addElement(doc, incidentElement, "incidentId", String.valueOf(incident.getIncidentId()));
                // Добавьте другие поля инцидента, если необходимо
            }

            // Преобразуем документ в строку XML
            StringWriter writer = new StringWriter();
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            String xmlString = writer.toString();

            log.info("XML успешно сгенерирован: {}", xmlString); // Логируем успешную генерацию XML
            return xmlString;
        }catch (IllegalArgumentException e) {
            throw e;
        }
        catch (Exception e) {
            log.error("Ошибка при генерации XML: {}", e.getMessage(), e); // Логируем ошибку при генерации XML
            // Бросаем RuntimeException вместо возврата null
            throw new RuntimeException("Ошибка при генерации XML", e);
        }
    }

    private void addElement(Document doc, Element parentElement, String tagName, String textContent) {
        Element element = doc.createElement(tagName);
        element.appendChild(doc.createTextNode(textContent));
        parentElement.appendChild(element);
    }
}
