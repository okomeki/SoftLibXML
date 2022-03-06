package net.siisise.xml;

import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.w3c.dom.Document;

/**
 *
 */
public class TrXML {

    /**
     * XSLTを通過させるだけのテキスト化
     * @param doc
     * @return
     * @throws TransformerException 
     */
    public static String plane(Document doc) throws TransformerException {
        TransformerFactory trafactory = TransformerFactory.newInstance();
        Transformer convert = trafactory.newTransformer(); // XSLT変換しない
        convert.setOutputProperty(OutputKeys.INDENT,"yes");
        convert.setOutputProperty(OutputKeys.METHOD, "xml");
        //convert.setOutputProperty("OutputPropertiesFactory.S_KEY_INDENT_AMOUNT, "2");
        // src
        DOMSource src = new DOMSource(doc);
        // out
        StringWriter out = new StringWriter();
        StreamResult result = new StreamResult(out);
        convert.transform(src, result);
        return out.toString();
    }
    
    /**
     * Transformer を使ったパターン
     */
    static Document toDoc(String xml) throws ParserConfigurationException, TransformerConfigurationException, TransformerException {
        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = db.newDocument();
        
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer convert = tf.newTransformer();
        
        StreamSource src = new StreamSource();
        src.setReader(new StringReader(xml));
        DOMResult result = new DOMResult(doc);
        convert.transform(src, result);
        return (Document) result.getNode();
    }
}
