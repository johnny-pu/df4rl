import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.dynamic.DynamicClientFactory;
import org.apache.cxf.frontend.ClientProxyFactoryBean;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.junit.Assert;
import org.junit.Test;
import service.MethodRetObjResponse;
import service.WebServiceSoap;

import javax.xml.namespace.QName;
import javax.xml.ws.Holder;

public class WebServiceTest {

    @Test
    public void testSimpleService() throws Exception {
        ClientProxyFactoryBean factoryBean=new JaxWsProxyFactoryBean();
        factoryBean.setServiceClass(WebServiceSoap.class);
        factoryBean.setAddress("http://localhost:13971/WebService.asmx?wsdl");
        factoryBean.setServiceName(new QName("http://tempuri.org/","WebService"));
        WebServiceSoap client = (WebServiceSoap) factoryBean.create();
        System.out.println(client.simpleService());

//        DynamicClientFactory clientFactory = JaxWsDynamicClientFactory.newInstance();
//        Client client = clientFactory.createClient("http://localhost:13971/WebService.asmx?wsdl");
//        Object[] obj = client.invoke("SimpleService");
//        System.out.println(obj.length+obj[0].toString());
//        Assert.assertTrue("This is a Simple WebService".equals(obj[0]));
    }

    @Test
    public void testServiceWithParam() throws Exception{
        DynamicClientFactory clientFactory = JaxWsDynamicClientFactory.newInstance();
        Client client = clientFactory.createClient("http://localhost:13971/WebService.asmx?wsdl");
        Object[] obj = client.invoke("MethodWithParam","string",1);
        System.out.println(obj.length+obj[0].toString());
    }

    @Test
    public void testServiceWithOutParam() throws Exception{
        ClientProxyFactoryBean factoryBean=new JaxWsProxyFactoryBean();
        factoryBean.setServiceClass(WebServiceSoap.class);
        factoryBean.setAddress("http://localhost:13971/WebService.asmx?wsdl");
        factoryBean.setServiceName(new QName("http://tempuri.org/","WebService"));
        WebServiceSoap client = (WebServiceSoap) factoryBean.create();
        Holder<String> stringHolder = new Holder<String>();
        Holder<Integer> integerHolder = new Holder<Integer>();
        client.methodWithOutParam(stringHolder,integerHolder);
        System.out.println(stringHolder.value+"/"+integerHolder.value);

    }

    @Test
    public void testMethodRetObj() throws Exception{
        ClientProxyFactoryBean factoryBean=new JaxWsProxyFactoryBean();
        factoryBean.setServiceClass(WebServiceSoap.class);
        factoryBean.setAddress("http://localhost:13971/WebService.asmx?wsdl");
        factoryBean.setServiceName(new QName("http://tempuri.org/","WebService"));
        WebServiceSoap client = (WebServiceSoap) factoryBean.create();
        MethodRetObjResponse.MethodRetObjResult result = client.methodRetObj();
        for(Object obj:result.getContent())
            System.out.println(obj);
    }

}
