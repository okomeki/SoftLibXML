package net.siisise.xml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * FileIOから分離する
 * @author okome
 */
public class XMLIO {
    
//    DocumentBuilder db;
    
    private static DocumentBuilder documentBuilder() throws ParserConfigurationException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setNamespaceAware(true);
        return dbFactory.newDocumentBuilder();
    }
    
    public static Document readXML(File file) throws SAXException, IOException, ParserConfigurationException {
        DocumentBuilder db = documentBuilder();
        return db.parse(file);
    }
    
    public static Document readXML(InputStream in) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilder db = documentBuilder();
        return db.parse(in);
    }

    public static Document readXML(URI uri) throws ParserConfigurationException, SAXException, IOException {
        //URLConnection con = uri.toURL().openConnection();
        //con.setRequestProperty("Accept", "*/*");
        //InputStream in = (InputStream)con.getContent();
        //byte[] data = FileIO.binRead(in);
        //Document doc = (Document) con.getContent(new Class[] { Document.class });
        //Document doc = readXML(in);
        //in.close();
        //System.out.println(new String(data,"utf-8"));
        //return doc;
        DocumentBuilder db = documentBuilder();
        return db.parse(uri.toString());
    }

    public static Document readXML(URL url) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilder db = documentBuilder();
        return db.parse(url.toString());
    }
}
