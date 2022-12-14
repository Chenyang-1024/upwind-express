package com.upwind.service.impl;

import com.upwind.DTO.ConsumerExpressDTO;
import com.upwind.DTO.CourierExpressDTO;
import com.upwind.DTO.DetailExpressDTO;
import com.upwind.DTO.OutletExpressDTO;
import com.upwind.mapper.ExpressMapper;
import com.upwind.pojo.*;
import com.upwind.service.*;
import com.upwind.utils.ExpressStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ExpressServiceImpl implements ExpressService {

    @Autowired
    private ExpressMapper expressMapper;
    @Autowired
    private SendwiseService sendwiseService;
    @Autowired
    private ReceivewiseService receivewiseService;
    @Autowired
    private ConsumerService consumerService;
    @Autowired
    private CourierService courierService;


    @Override
    public Integer insertExpress(DetailExpressDTO detailExpressDTO, Integer send_outletId, Integer receive_outletId) {
        Sendwise sendwise = detailExpressDTO.getSendwise();
        Receivewise receivewise = detailExpressDTO.getReceivewise();
        Express express = detailExpressDTO.getExpress();

        SecureRandom secureRandom = new SecureRandom();
        // 网点随机安排收寄快递员，存储寄件信息
        List<Courier> sendCourierList = courierService.getCourierByOutletId(send_outletId);
        if (sendCourierList.size() == 0)
            return null;
        Courier sendCourier = sendCourierList.get(secureRandom.nextInt(sendCourierList.size()));
        sendwise.setCourier_id(sendCourier.getId());
        if (sendwiseService.insertSendwise(sendwise) == null)
            return null;
        express.setSendwise_id(sendwise.getId());
        // 网点随机安排派件快递员，存储收件信息
        List<Courier> receiveCourierList = courierService.getCourierByOutletId(receive_outletId);
        if (receiveCourierList.size() == 0)
            return null;
        Courier receiveCourier = receiveCourierList.get(secureRandom.nextInt(receiveCourierList.size()));
        receivewise.setCourier_id(receiveCourier.getId());
        if (receivewiseService.insertReceivewise(receivewise) == null)
            return null;
        express.setReceivewise_id(receivewise.getId());

        // 初始化物流状态（待揽收）
        express.setStatus(ExpressStatus.status_1.getStatus());

        // 初始化下单时间
        express.setOrder_time(new Date());

        if (expressMapper.insert(express) > 0)
            return express.getId();
        else
            return null;
    }

    @Override
    public boolean deleteExpressById(Integer id) {
        Integer sendwise_id = expressMapper.selectByPrimaryKey(id).getSendwise_id();
        Integer receivewise_id = expressMapper.selectByPrimaryKey(id).getReceivewise_id();
        if (!sendwiseService.deleteSendwiseById(sendwise_id))
            return false;
        if (!receivewiseService.deleteReceivewiseById(receivewise_id))
            return false;
        return expressMapper.deleteByPrimaryKey(id) > 0;
    }

    @Override
    public boolean updateExpress(DetailExpressDTO detailExpressDTO) {
        if (!sendwiseService.updateSendwise(detailExpressDTO.getSendwise()))
            return false;
        if (!receivewiseService.updateReceivewise(detailExpressDTO.getReceivewise()))
            return false;
        return expressMapper.updateByPrimaryKeySelective(detailExpressDTO.getExpress()) > 0;
    }

    @Override
    public Express getExpressById(Integer id) {
        return expressMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<ConsumerExpressDTO> getConsumerExpressByOrder(Integer consumer_id, int flag, String order_no) {
        List<ConsumerExpressDTO> consumerExpressDTOList = new ArrayList<>();

        // flag = 0 时，查询当前用户寄出订单
        if (flag == 0) {
            List<Sendwise> sendwiseList = sendwiseService.getSendwiseBySenderId(consumer_id);
            for (Sendwise sendwise : sendwiseList) {
                ExpressExample expressExample = new ExpressExample();
                ExpressExample.Criteria criteria = expressExample.createCriteria();
                if (order_no != null)
                    criteria.andOrder_noEqualTo(order_no);
                criteria.andSendwise_idEqualTo(sendwise.getId());
                Express express = expressMapper.selectByExample(expressExample).get(0);
                Receivewise receivewise = receivewiseService.getReceivewiseById(express.getReceivewise_id());
                consumerExpressDTOList.add(
                        new ConsumerExpressDTO(
                                express,
                                sendwise,
                                consumerService.getConsumerById(sendwise.getSender_id()),
                                receivewise,
                                consumerService.getConsumerById(receivewise.getReceiver_id())
                        )
                );
            }
        }
        else {
            List<Receivewise> receivewiseList = receivewiseService.getReceivewiseByReceiverId(consumer_id);
            for (Receivewise receivewise : receivewiseList) {
                ExpressExample expressExample = new ExpressExample();
                ExpressExample.Criteria criteria = expressExample.createCriteria();
                if (order_no != null)
                    criteria.andOrder_noEqualTo(order_no);
                criteria.andReceivewise_idEqualTo(receivewise.getId());
                Express express = expressMapper.selectByExample(expressExample).get(0);
                Sendwise sendwise = sendwiseService.getSendwiseById(express.getSendwise_id());
                consumerExpressDTOList.add(
                        new ConsumerExpressDTO(
                                express,
                                sendwise,
                                consumerService.getConsumerById(sendwise.getSender_id()),
                                receivewise,
                                consumerService.getConsumerById(receivewise.getReceiver_id())
                        )
                );
            }
        }
        return consumerExpressDTOList;
    }

    @Override
    public List<ConsumerExpressDTO> getConsumerExpressByUnpaid(Integer consumer_id, String order_no) {
        List<ConsumerExpressDTO> consumerExpressDTOList = getConsumerExpressByOrder(consumer_id, 0, order_no);
        if (consumerExpressDTOList == null || consumerExpressDTOList.size() == 0)
            return null;
        consumerExpressDTOList.removeIf(consumerExpressDTO -> !consumerExpressDTO.getExpress().getStatus().equals(ExpressStatus.status_2.getStatus()));
        return consumerExpressDTOList;
    }

    @Override
    public List<CourierExpressDTO> getCourierExpressByStatus(Integer courier_id, String status, String order_no) {
        // 查询经该快递员收寄的快递订单寄件信息
        SendwiseExample sendwiseExample = new SendwiseExample();
        sendwiseExample.createCriteria().andCourier_idEqualTo(courier_id);
        List<Sendwise> sendwiseList = sendwiseService.getSendwiseByCourierId(courier_id);
        // 查询经该快递员投递的快递订单收件信息
        ReceivewiseExample receivewiseExample = new ReceivewiseExample();
        receivewiseExample.createCriteria().andCourier_idEqualTo(courier_id);
        List<Receivewise> receivewiseList = receivewiseService.getReceivewiseByCourierId(courier_id);

        List<CourierExpressDTO> courierExpressDTOList_send = new ArrayList<>();
        List<CourierExpressDTO> courierExpressDTOList_receive = new ArrayList<>();

        for (Sendwise sendwise : sendwiseList) {
            ExpressExample expressExample = new ExpressExample();
            ExpressExample.Criteria criteria = expressExample.createCriteria();
            if (order_no != null)
                criteria.andOrder_noEqualTo(order_no);
            criteria.andSendwise_idEqualTo(sendwise.getId());
            Express express = expressMapper.selectByExample(expressExample).get(0);
            Receivewise receivewise = receivewiseService.getReceivewiseById(express.getReceivewise_id());
            Consumer sender = consumerService.getConsumerById(sendwise.getSender_id());
            Consumer receiver = consumerService.getConsumerById(receivewise.getReceiver_id());
            courierExpressDTOList_send.add(
                    new CourierExpressDTO(
                            express,
                            sendwise,
                            sender,
                            receivewise,
                            receiver,
                            "经收寄"
                    )
            );
        }
        for (Receivewise receivewise : receivewiseList) {
            ExpressExample expressExample = new ExpressExample();
            ExpressExample.Criteria criteria = expressExample.createCriteria();
            if (order_no != null)
                criteria.andOrder_noEqualTo(order_no);
            criteria.andReceivewise_idEqualTo(receivewise.getId());
            Express express = expressMapper.selectByExample(expressExample).get(0);
            Sendwise sendwise = sendwiseService.getSendwiseById(express.getSendwise_id());
            Consumer sender = consumerService.getConsumerById(sendwise.getSender_id());
            Consumer receiver = consumerService.getConsumerById(receivewise.getReceiver_id());
            courierExpressDTOList_receive.add(
                    new CourierExpressDTO(
                            express,
                            sendwise,
                            sender,
                            receivewise,
                            receiver,
                            "经投递"
                    )
            );
        }

        // 查询全部
        if (status == null) {
            List<CourierExpressDTO> courierExpressDTOList = new ArrayList<>();
            courierExpressDTOList.addAll(courierExpressDTOList_send);
            courierExpressDTOList.addAll(courierExpressDTOList_receive);
            return courierExpressDTOList;
        }
        // 待揽收状态
        else if (status.equals(ExpressStatus.status_1.getStatus())) {
            if (courierExpressDTOList_send.size() != 0)
                courierExpressDTOList_send.removeIf(courierExpressDTO -> !courierExpressDTO.getExpress().getStatus().equals(status));
            return courierExpressDTOList_send;
        }
        // 待用户付款状态
        else if (status.equals(ExpressStatus.status_2.getStatus())) {
            if (courierExpressDTOList_send.size() != 0)
                courierExpressDTOList_send.removeIf(courierExpressDTO -> !courierExpressDTO.getExpress().getStatus().equals(status));
            return courierExpressDTOList_send;
        }
        // 待妥投状态（正在派件）
        else if (status.equals(ExpressStatus.status_4.getStatus())) {
            if (courierExpressDTOList_receive.size() != 0)
                courierExpressDTOList_receive.removeIf(courierExpressDTO -> !courierExpressDTO.getExpress().getStatus().equals(status));
            return courierExpressDTOList_receive;
        }
        // 用户已签收状态
        else if (status.equals(ExpressStatus.status_5.getStatus())) {
            if (courierExpressDTOList_receive.size() != 0)
                courierExpressDTOList_receive.removeIf(courierExpressDTO -> !courierExpressDTO.getExpress().getStatus().equals(status));
            return courierExpressDTOList_receive;
        }
        return null;
    }

    @Override
    public List<OutletExpressDTO> getExpressByOutletId(Integer outlet_id, String order_no) {
        List<OutletExpressDTO> outletExpressDTOList = new ArrayList<>();
        List<Courier> courierList = courierService.getCourierByOutletId(outlet_id);
        if (courierList == null || courierList.size() == 0)
            return null;
        for (Courier courier : courierList) {
            // List<CourierExpressDTO> courierExpressDTOList = getCourierExpressByStatus(courier.getId(), null, null);
            List<CourierExpressDTO> courierExpressDTOList = getCourierExpressByStatus(courier.getId(), null, order_no);
            for (CourierExpressDTO courierExpressDTO : courierExpressDTOList) {
                outletExpressDTOList.add(
                        new OutletExpressDTO(
                                courierExpressDTO.getExpress(),
                                courierExpressDTO.getSendwise(),
                                courierExpressDTO.getSender(),
                                courierExpressDTO.getReceivewise(),
                                courierExpressDTO.getReceiver(),
                                courier
                        )
                );
            }
        }
        return outletExpressDTOList;
    }

    @Override
    public List<Express> getAllExpress() {
        return expressMapper.selectByExample(null);
    }

    @Override
    public DetailExpressDTO getDetailExpressById(Integer id) {
        Express express = expressMapper.selectByPrimaryKey(id);
        Sendwise sendwise = sendwiseService.getSendwiseById(express.getSendwise_id());
        Receivewise receivewise = receivewiseService.getReceivewiseById(express.getReceivewise_id());
        return new DetailExpressDTO(
                express,
                sendwise,
                receivewise
        );
    }
}
