package com.neu.edu.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayResponse;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.demo.trade.config.Configs;
import com.alipay.demo.trade.model.ExtendParams;
import com.alipay.demo.trade.model.GoodsDetail;
import com.alipay.demo.trade.model.builder.AlipayTradePrecreateRequestBuilder;
import com.alipay.demo.trade.model.result.AlipayF2FPrecreateResult;
import com.alipay.demo.trade.service.AlipayTradeService;
import com.alipay.demo.trade.service.impl.AlipayTradeServiceImpl;
import com.alipay.demo.trade.utils.ZxingUtils;
import com.neu.edu.pojo.OmtOrder;
import com.neu.edu.service.IOmtOrderService;
import com.neu.edu.util.ResultJson;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author HSK
 * @date 2021/7/21 16:58
 */
@RestController
@RequestMapping("/alipay")
public class AliPayController {

    @Resource
    IOmtOrderService iOmtOrderService;

    private static AlipayTradeService tradeService;

    private static final Logger log = LoggerFactory.getLogger(AliPayController.class);

    @RequestMapping("/pay")
    public Object pay(HttpServletRequest request, HttpServletResponse response){
        return test_trade_precreate(request, response);
//        return "ok";
    }

    static {
        /** 一定要在创建AlipayTradeService之前调用Configs.init()设置默认参数
         *  Configs会读取classpath下的zfbinfo.properties文件配置信息，如果找不到该文件则确认该文件是否在classpath目录
         */
        Configs.init("zfbinfo.properties");

        /** 使用Configs提供的默认参数
         *  AlipayTradeService可以使用单例或者为静态成员对象，不需要反复new
         */
        tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();
    }

    public Object test_trade_precreate(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        // (必填) 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线，
        // 需保证商户系统端不能重复，建议通过数据库sequence生成，
        String outTradeNo = httpServletRequest.getParameter("outTradeNo");
        System.out.println(outTradeNo);
        if (outTradeNo.equals("")){
            return ResultJson.error("out_trade_no should not be NULL!");

        }
        // (必填) 订单标题，粗略描述用户的支付目的。如“xxx品牌xxx门店当面付扫码消费”
        String subject = "e-shop_Payment";

        // (必填) 订单总金额，单位为元，不能超过1亿元
        // 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
        String totalAmount = httpServletRequest.getParameter("totalAmount");
        System.out.println(totalAmount);

        // (可选) 订单不可打折金额，可以配合商家平台配置折扣活动，如果酒水不参与打折，则将对应金额填写至此字段
        // 如果该值未传入,但传入了【订单总金额】,【打折金额】,则该值默认为【订单总金额】-【打折金额】
//        String undiscountableAmount = "0";

        // 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)
        // 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
        String sellerId = "";

        // 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品2件共15.00元"
//        String body = "购买商品3件共20.00元";

        // 商户操作员编号，添加此参数可以为商户操作员做销售统计
//        String operatorId = "test_operator_id";

        // (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
        String storeId = "e-shop";

        // 业务扩展参数，目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法)，详情请咨询支付宝技术支持
//        ExtendParams extendParams = new ExtendParams();
//        extendParams.setSysServiceProviderId("2088100200300400500");

        // 支付超时，定义为120分钟
        String timeoutExpress = "120m";
//
//        // 商品明细列表，需填写购买商品详细信息，
//        List<GoodsDetail> goodsDetailList = new ArrayList<GoodsDetail>();
//        // 创建一个商品信息，参数含义分别为商品id（使用国标）、名称、单价（单位为分）、数量，如果需要添加商品类别，详见GoodsDetail
//        GoodsDetail goods1 = GoodsDetail.newInstance("goods_id001", "xxx小面包", 1000, 1);
//        // 创建好一个商品后添加至商品明细列表
//        goodsDetailList.add(goods1);
//
//        // 继续创建并添加第一条商品信息，用户购买的产品为“黑人牙刷”，单价为5.00元，购买了两件
//        GoodsDetail goods2 = GoodsDetail.newInstance("goods_id002", "xxx牙刷", 500, 2);
//        goodsDetailList.add(goods2);

        // 创建扫码支付请求builder，设置请求参数
        AlipayTradePrecreateRequestBuilder builder = new AlipayTradePrecreateRequestBuilder()
                .setSubject(subject)
                .setTotalAmount(totalAmount)
                .setOutTradeNo(outTradeNo)
//                .setUndiscountableAmount(undiscountableAmount)
                .setSellerId(sellerId)
//                .setBody(body)
//                .setOperatorId(operatorId)
                .setStoreId(storeId)
//                .setExtendParams(extendParams)
                .setTimeoutExpress(timeoutExpress)
                //支付宝服务器主动通知商户服务器里指定的页面http路径,根据需要设置
                .setNotifyUrl("http://52wtps.natappfree.cc/alipay/callback?charset=UTF-8"+
                        "&out_trade_no="+outTradeNo+
                        "&subject="+subject+
                        "&sellerId="+sellerId+
                        "&total_amount="+totalAmount+
                        "&timeout_express="+timeoutExpress+
//                        "&extend_params="+extendParams+
                        "&store_id="+storeId);
//                .setGoodsDetailList(goodsDetailList);

        AlipayF2FPrecreateResult result = tradeService.tradePrecreate(builder);
        switch (result.getTradeStatus()) {
            case SUCCESS:
                log.info("支付宝预下单成功: )");

                AlipayTradePrecreateResponse response = result.getResponse();
                dumpResponse(response);

                // 需要修改为运行机器上的路径
                log.info("out_trade_no:"+ response.getOutTradeNo());
                String filePath = String.format("E:\\e-shop\\zfb\\qr-%s.png", response.getOutTradeNo());
                File[] list = new File("E:\\e-shop\\zfb\\").listFiles();
                for (File file : list) {
                    file.delete();
                }
                log.info("filePath:" + filePath);
                ZxingUtils.getQRCodeImge(response.getQrCode(), 256, filePath);

                String imageString = null;
                FileInputStream in = null;
                ServletOutputStream out = null;
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(new File("E:\\e-shop\\zfb\\qr-"+response.getOutTradeNo()+".png"));
                    int count = 0;
                    while (count == 0) {
                        count = fis.available();
                    }
                    byte[] read = new byte[count];
                    System.err.println(read);
                    fis.read(read);
                    imageString = Base64.getEncoder().encodeToString(read);

                }catch (Exception e){
                    log.error("下载文件异常!", e);
                }finally {
                    try {
//                        in.close();
//                        out.flush();
//                        out.close();
                            fis.close();
                        return ResultJson.success(imageString,"success") ;
                    }catch (Exception e){
                        log.error("流关闭异常!");
                    }
                }
//                break;

            case FAILED:
                log.error("支付宝预下单失败!!!");
                return ResultJson.error("支付宝预下单失败!!!") ;
//                break;

            case UNKNOWN:
                log.error("系统异常，预下单状态未知!!!");
                    return ResultJson.error("系统异常，预下单状态未知!!!") ;
//                break;

            default:
                log.error("不支持的交易状态，交易返回异常!!!");
                return ResultJson.error("不支持的交易状态，交易返回异常!!!") ;
//                break;
        }
//        return ResultJson.error("null");
    }

    // 简单打印应答
    private void dumpResponse(AlipayResponse response) {
        if (response != null) {
            log.info(String.format("code:%s, msg:%s", response.getCode(), response.getMsg()));
            if (StringUtils.isNotEmpty(response.getSubCode())) {
                log.info(String.format("subCode:%s, subMsg:%s", response.getSubCode(),
                        response.getSubMsg()));
            }
            log.info("body:" + response.getBody());
        }
    }

    @RequestMapping("/callback")
    public Object callback(String charset, String out_trade_no,
                           String subject, String sellerId,
                           String total_amount, String timeout_express,
                           String store_id, String version,
                           String app_id, String sign_type, String timestamp) {
        out_trade_no = out_trade_no.split(",")[0];
        System.out.println("\n\n\n\n"+out_trade_no);
        List<OmtOrder> list = iOmtOrderService.getOrderListByOrderNo(out_trade_no);
        for (OmtOrder order : list) {
            order.setStatus(2);
            iOmtOrderService.updateById(order);
        }

        //验证各种数据
        //业务代码
        return "success";
    }

    @RequestMapping("/check")
    public Object check(String orderNo){
        boolean result = iOmtOrderService.check(orderNo);
        if (result) {
            return ResultJson.success(result,"支付成功");
        }else {
            return ResultJson.success(result,"支付失败");
        }

    }


//    @RequestMapping("/alipay-callback")
//    public Object alipayCallback(HttpServletRequest request) {
//        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n+++++++++++++++++++++++++++++++++++++++");
//        Map<String, String> params = new HashMap<>();
//        Map requestParams = request.getParameterMap();
//        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
//            String name = (String) iter.next();
//            String[] values = (String[]) requestParams.get(name);
//            String valueStr = "";
//            for (int i = 0; i < values.length; i++) {
//                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
//            }
//            params.put(name, valueStr);
//        }
//        if(params == null || params.size() == 0){
//            return "failed";
//        }
//        log.info("支付宝回调,sign: {}, trade_status: {}, 参数: {}", params.get("sign"), params.get("trade_status"), params.toString());
//
//        //验证回调的正确性
//        params.remove("sign_type");
//        try {
//            boolean alipayRSACheck = AlipaySignature.rsaCheckV2(params, Configs.getAlipayPublicKey(), "utf-8", Configs.getSignType());
//            if (!alipayRSACheck) {
//                //非法请求
//                return null;
//            }
//        } catch (AlipayApiException e) {
//            log.error("支付宝验证回调异常", e);
//            e.printStackTrace();
//        }
//
//        //验证各种数据
//        //业务代码
//        return "success";
//    }

}

