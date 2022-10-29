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

import java.util.ArrayList;
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
    // TODO
    public Integer insertExpress(DetailExpressDTO detailExpressDTO, Integer outlet_id) {
        Sendwise sendwise = detailExpressDTO.getSendwise();
        Receivewise receivewise = detailExpressDTO.getReceivewise();
        Express express = detailExpressDTO.getExpress();
        Integer sendwise_id = sendwiseService.insertSendwise(sendwise);
        Integer receivewise_id = receivewiseService.insertReceivewise(receivewise);
        if (sendwise_id == null || receivewise_id == null)
            return null;
        // 安排收寄快递员，存储寄件信息
        express.setSendwise_id(sendwise_id);

        // 安排派件快递员，存储收件信息
        express.setReceivewise_id(receivewise_id);

        // 更新物流状态
        express.setStatus(ExpressStatus.status_1.getStatus());

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
        // 待妥投状态（正在派件）
        else if (status.equals(ExpressStatus.status_3.getStatus())) {
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
            List<CourierExpressDTO> courierExpressDTOList = getCourierExpressByStatus(courier.getId(), null, null);
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
