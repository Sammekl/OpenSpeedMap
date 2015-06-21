package com.sammekl.openspeedmap.helpers;

import android.util.Log;

import com.sammekl.openspeedmap.utils.Constants;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * Created by Samme on 21-6-2015.
 */
public class XMLHelper {

    /**
     * Parse the XML and add the maxspeed attribute
     * @param xmlToParse xml to parse
     * @param maxspeed the maxspeed to add
     * @return The new XML containing a way
     * @throws Exception
     */
    public static String parseDom(String xmlToParse, int maxspeed) throws Exception {
        String output = "";
        DocumentBuilder newDocumentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = newDocumentBuilder.parse(new ByteArrayInputStream(xmlToParse.getBytes()));

        NodeList nodes = doc.getElementsByTagName("way");

        // Add element
        String toInsert = String.format(Constants.PREF_API_MAX_SPEED_UPDATE, "" + maxspeed);
        Document doc2 = newDocumentBuilder.parse(new ByteArrayInputStream(toInsert.getBytes()));
        Node node = doc.importNode(doc2.getDocumentElement(), true);

        nodes.item(0).appendChild(node);

        //Set up the transformer to write the output string
        TransformerFactory tFactory = TransformerFactory.newInstance();
        Transformer transformer = tFactory.newTransformer();
        transformer.setOutputProperty("indent", "yes");
        StringWriter sw = new StringWriter();
        StreamResult result = new StreamResult(sw);

        //Find the first child node - this could be done with xpath as well
        NodeList nl = doc.getDocumentElement().getChildNodes();
        DOMSource source = null;
        for(int x = 0;x < nl.getLength();x++)
        {
            Node e = nl.item(x);
            if(e instanceof Element)
            {
                source = new DOMSource(e);
                break;
            }
        }

        //Do the transformation and output
        transformer.transform(source, result);
        Log.i("XMLHelper", sw.toString());

        return sw.toString();
    }
}
