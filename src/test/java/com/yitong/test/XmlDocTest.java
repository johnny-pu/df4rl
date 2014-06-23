package com.yitong.test;


import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import java.io.ByteArrayInputStream;

public class XmlDocTest {
    @Test
    public void testDoc(){
        Document doc = DocumentFactory.getInstance().createDocument();
        Element root = doc.addElement("root");
        root.addAttribute("id", "a1");
        Element child = root.addElement("child");
        child.setText("This is Child");
        Element aEl = root.addElement("AEL");
        aEl.addAttribute("id", "ael");
        aEl.addAttribute("name", "中文编码");
        System.out.println(doc.asXML());
    }

    @Test
    public void testXPath() throws DocumentException {
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>";
        xml = xml+"<root>";
        xml=xml+"<a>1</a>";
        xml = xml+"</root>";
        SAXReader reader = new SAXReader();
        ByteArrayInputStream is = new ByteArrayInputStream(xml.getBytes());
        Document doc = reader.read(is);
        Node node = doc.selectSingleNode("/root/a");
        System.out.println(node.getText());
    }
}
