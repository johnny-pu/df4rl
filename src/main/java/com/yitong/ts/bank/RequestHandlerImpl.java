package com.yitong.ts.bank;

import com.ctc.wstx.util.StringUtil;
import com.yitong.ts.rl.RLClient;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;

/**
 * 客户端请求处理接口实现
 *
 * @author puxiwu
 */
@Component
public class RequestHandlerImpl implements RequestHandler {
    private Logger logger = LoggerFactory.getLogger(RequestHandlerImpl.class);

    private final static String separator = "\\|";
    private final static String fun_getinvoiceNo = "GetInvoiceNo";

    @Autowired
    private RLClient rlClient;

    @Override
    public String handle(String message) {
        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            builderFactory.setNamespaceAware(true);
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            ByteArrayInputStream is = new ByteArrayInputStream(message.getBytes());
            Document doc = builder.parse(is);
            XPathFactory xPathFactory =XPathFactory.newInstance();
            XPath xPath = xPathFactory.newXPath();
            String functionName = (xPath.compile("/WEBSERVICE/BODY/FunctionName/text()").evaluate(doc, XPathConstants.STRING)).toString();
            if(StringUtils.isBlank(functionName))
                throw new Exception("客户端发送的报文未正确设置'FunctionName'");
            if(fun_getinvoiceNo.equals(functionName)){
                return this.getInvoiceNo();
            }
        } catch (Exception e) {
            return this.errorHandler(e);
        }
        return null;
    }

    //获取发票号
    private String getInvoiceNo() throws Exception {
        String retMsg = this.rlClient.getInvoiceNo();
        if (StringUtils.isBlank(retMsg))
            throw new Exception("[GetInvoiceNo]成功调用WebService但未获取到发票号。");
        String[] temp = retMsg.split(separator);
        if (temp.length != 4)
            throw new Exception("[GetInvoiceNo]成功调用WebService但获取到无法识别的返回结果:" + retMsg);
        String resultCode = temp[0]; //结果码
        String resultDesc = temp[1]; //结果描述
        this.validResult(resultCode, resultDesc, "[GetInvoiceNo]"); //结果校验
        String invoiceNo = temp[2];
        int remainInvoice = Integer.parseInt(temp[3]);
        //TODO 获取发票号
        return null;
    }

    //修改密码
    private String changePasswd(String nNewPasswd) throws Exception {
        String retMsg = this.rlClient.changePasswd(nNewPasswd);
        //TODO 修改密码
        return null;
    }

    //查询客户信息及欠费
    private String getCustInfo(String nCardId) {
        //TODO 查询客户信息及欠费
        return null;
    }

    //客户缴费
    private String custCharge(String nCustId, String nYear, String nInvoiceCode,
                              String nInvoiceName, double nMoney, String nMethod,
                              String nBankSerialNo) throws Exception {
        //TODO 客户缴费
        return null;
    }

    //已使用发票作废
    private String usedInvoiceCancel(String nInvoiceCode) throws Exception {
        //TODO 已使用发票作废
        return null;
    }

    //未使用发票作废
    private String freeInvoiceCancel(String nInvoiceCode) throws Exception {
        //TODO 未使用发票作废
        return null;
    }

    //收费员收款金额统计
    private String changeCaclute(String beginDate, String endDate) throws Exception {
        //TODO 收费员收款金额统计
        return null;
    }

    //收费员收款明细统计
    private String chargeDetailCaclute(String beginDate, String endDate) throws Exception {
        //TODO 收费员收款明细统计
        return null;
    }

    //结果校验
    private void validResult(String resultCode, String resultDesc, String method) throws Exception {
        method = StringUtils.isBlank(method) ? "" : "[" + method + "]";
        if ("0".equals(resultCode))
            throw new Exception(String.format("%s调用WebService但返回失败结果。%s",
                    method, StringUtils.isBlank(resultDesc) ? "" : ("失败原因: " + resultDesc)));
        if ("2".equals(resultCode))
            throw new Exception(String.format("%s调用WebService但返回异常结果。%s",
                    method, StringUtils.isBlank(resultDesc) ? "" : ("异常描述: " + resultDesc)));
    }

    //错误处理
    private String errorHandler(Exception e) {
        logger.error("客户端请求处理发生异常。", e);
        //TODO 异常响应返回
        return null;
    }

}
