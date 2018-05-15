package com.bax.schema;

import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.testng.annotations.*;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLFilterImpl;
import org.xml.sax.helpers.XMLReaderFactory;


import com.sun.org.apache.xerces.internal.jaxp.SAXParserFactoryImpl;

import static org.testng.Assert.*;

public class CarTest {

    public CarTest() throws JAXBException, SAXException, ParserConfigurationException {
//        saxParserFactory.setNamespaceAware(false);
    }

    SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    Schema schema = schemaFactory.newSchema(new StreamSource("src/main/resources/schema.xsd"));
    JAXBContext jaxbContext = JAXBContext.newInstance(Car.class);
    Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
    static SAXParserFactory saxParserFactory = SAXParserFactory.newInstance("com.sun.org.apache.xerces.internal.jaxp.SAXParserFactoryImpl", null);

    @Test
    public void unmarshall() throws Exception {
//    org.apache.xerces.impl.Version.main(new String[]{});
//        com.sun.org.apache.xerces.internal.impl.Version.main(new String[]{});
        unmarshaller.setSchema(schema);
        SAXSource source = new SAXSource(new XMLNamespaceFilter(),
                new InputSource("src/main/resources/car.xml"));
        JAXBElement<Car> car = unmarshaller.unmarshal(source, Car.class);
        assertEquals(car.getValue().getModel(), "Skoda");
    }

    @Test
    public void unmarshall2() throws Exception {
//Prepare JAXB objects
        JAXBContext jc = JAXBContext.newInstance(Car.class);
        Unmarshaller u = jc.createUnmarshaller();
        u.setSchema(schema);

//Create an XMLReader to use with our filter
        XMLReader reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();// XMLReaderFactory.createXMLReader();

//Create the filter (to add namespace) and set the xmlReader as its parent.
        NamespaceFilter inFilter = new NamespaceFilter("http://www.bax.com/xsd", true);
        inFilter.setParent(reader);

//Prepare the input, in this case a java.io.File (output)
        InputSource is = new InputSource(new FileInputStream("src/main/resources/car.xml"));

//Create a SAXSource specifying the filter
        SAXSource source = new SAXSource(inFilter, is);

//Do unmarshalling
        JAXBElement<Car> car = u.unmarshal(source, Car.class);
        System.out.println();
    }

    @Test
    public void validate() throws IOException, SAXException, ParserConfigurationException {
        Validator validator = schema.newValidator();
        validator.validate(new SAXSource(new XMLNamespaceFilter(),
                new InputSource("src/main/resources/car.xml")));
    }

    static class XMLNamespaceFilter extends XMLFilterImpl {

        private boolean addedNamespace = false;

        XMLNamespaceFilter() throws ParserConfigurationException, SAXException {
            super(saxParserFactory.newSAXParser().getXMLReader());
        }


        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            super.startElement("http://www.bax.com/xsd", localName, qName, attributes);
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            super.endElement("http://www.bax.com/xsd", localName, qName);
        }
    }

    public class NamespaceFilter extends XMLFilterImpl {

        private String usedNamespaceUri;
        private boolean addNamespace;

        //State variable
        private boolean addedNamespace = false;

        public NamespaceFilter(String namespaceUri,
                               boolean addNamespace) {
            super();

            if (addNamespace)
                this.usedNamespaceUri = namespaceUri;
            else
                this.usedNamespaceUri = "";
            this.addNamespace = addNamespace;
        }



        @Override
        public void startDocument() throws SAXException {
            super.startDocument();
            if (addNamespace) {
                startControlledPrefixMapping();
            }
        }



        @Override
        public void startElement(String arg0, String arg1, String arg2,
                                 Attributes arg3) throws SAXException {

            super.startElement(this.usedNamespaceUri, arg1, arg2, arg3);
        }

        @Override
        public void endElement(String arg0, String arg1, String arg2)
                throws SAXException {

            super.endElement(this.usedNamespaceUri, arg1, arg2);
        }

        @Override
        public void startPrefixMapping(String prefix, String url)
                throws SAXException {


            if (addNamespace) {
                this.startControlledPrefixMapping();
            } else {
                //Remove the namespace, i.e. don´t call startPrefixMapping for parent!
            }

        }

        private void startControlledPrefixMapping() throws SAXException {

            if (this.addNamespace && !this.addedNamespace) {
                //We should add namespace since it is set and has not yet been done.
                super.startPrefixMapping("", this.usedNamespaceUri);

                //Make sure we dont do it twice
                this.addedNamespace = true;
            }
        }

    }
}