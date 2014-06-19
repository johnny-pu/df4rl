package com.yitong.ts.rl;

import org.apache.cxf.binding.soap.SoapHeader;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.helpers.XMLUtils;
import org.apache.cxf.interceptor.Fault;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.annotation.PostConstruct;
import javax.xml.namespace.QName;
import java.util.Hashtable;

/**
 * 调用拦截，注入soapheader
 *
 * @author puxiwu
 */
public class BankSoapHeader extends AbstractSoapInterceptor {
    private final static String header_el_name = "tns:RequestSOAPHeader";
    private final static String qnValue = "RequestSOAPHeader";

    public BankSoapHeader(String qname, Hashtable<String, String> attributes) {
        this.qname = qname;
        this.attributes = attributes;
    }


    private Hashtable<String, String> attributes;//头部信息属性列表
    private String qname;


    @Override
    public void handleMessage(SoapMessage soapMessage) throws Fault {
        QName qn = new QName(qnValue);

        //注入SoapHeader
        SoapHeader soapHeader = new SoapHeader(qn, this.createHeader());
        soapMessage.getHeaders().add(soapHeader);

    }


    protected Element createHeader() {
        Document doc = DOMUtils.createDocument();

        Element root = doc.createElementNS(this.qname, header_el_name);

        //创建soapheader节点
        for (String key : attributes.keySet()) {
            Element el = doc.createElement(key);
            el.setTextContent(attributes.get(key));
            root.appendChild(el);
        }
        XMLUtils.printDOM(root);
        return root;

    }

}
