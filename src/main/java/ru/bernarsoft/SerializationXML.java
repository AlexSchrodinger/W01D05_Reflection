package ru.bernarsoft;


import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class SerializationXML {

    private People people;

    public SerializationXML(People people) {
        this.people = people;
    }

    public Document getSerializedDoc() throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = dbf.newDocumentBuilder();
        Document doc = builder.newDocument();

        Element element = doc.createElement(people.getClass().getSimpleName());
        doc.appendChild(element);

        Element fieldsElement = doc.createElement("Fields");
        element.appendChild(fieldsElement);

        for (Field field : people.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Element itemField = doc.createElement(field.getName());
            fieldsElement.appendChild(itemField);
            itemField.setAttribute("type", field.getType().getName());
            itemField.insertBefore(doc.createTextNode(String.valueOf(field.get(people))), itemField.getLastChild());
        }

        Element methodsElement = doc.createElement("Methods");
        element.appendChild(methodsElement);

        for (Method method : people.getClass().getDeclaredMethods()) {
            method.setAccessible(true);
            Element itemMethod = doc.createElement(method.getName());
            methodsElement.appendChild(itemMethod);

            itemMethod.setAttribute("return", method.getReturnType().getName());
            itemMethod.setAttribute("return", method.getReturnType().getName());
            Class[] paramTypes = method.getParameterTypes();

            for (Class paramType : paramTypes) {
                itemMethod.setAttribute("paramType", paramType.getName());
            }
        }
        return doc;
    }
}
