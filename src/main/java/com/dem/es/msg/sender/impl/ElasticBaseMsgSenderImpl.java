package com.dem.es.msg.sender.impl;

import com.dem.es.entity.constant.ExchangeConstant;
import com.dem.es.entity.constant.MsgEvent;
import com.dem.es.msg.ElasticMsgDto;
import com.dem.es.msg.sender.IElasticPersonSender;
import com.dem.es.util.GsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ElasticBaseMsgSenderImpl implements IElasticPersonSender {
    private static final Logger logger = LoggerFactory.getLogger(ElasticBaseMsgSenderImpl.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;



    protected String getIndex() {
        return "default";
    }

    protected String getType() {
        return "default";
    }

    @Override
    public void update(Long id) {
        if (id == null) {
            return;
        }
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(id.toString());
        ElasticMsgDto dto = new ElasticMsgDto();
        dto.setId(id.toString());
        dto.setMsg("更新消息");
        StringBuffer sb = new StringBuffer(30);
        sb.append(ExchangeConstant.EXCHANGE_ELASTIC)
                .append(".")
                .append(this.getIndex())
                .append(".")
                .append(this.getType())
                .append(".")
                .append(MsgEvent.UPDATE)
                .append(".")
                .append(id);
        rabbitTemplate.convertAndSend(ExchangeConstant.EXCHANGE_ELASTIC, sb.toString(), GsonUtils.obj2Json(dto), correlationData);

    }

    @Override
    public void del(Long id) {
        if (id == null) {
            return;
        }
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(id.toString());
        ElasticMsgDto dto = new ElasticMsgDto();
        dto.setId(id.toString());
        dto.setMsg("删除消息");
        StringBuffer sb = new StringBuffer(30);
        sb.append(ExchangeConstant.EXCHANGE_ELASTIC)
                .append(".")
                .append(this.getIndex())
                .append(".")
                .append(this.getType())
                .append(".")
                .append(MsgEvent.DEL)
                .append(".")
                .append(id);
        rabbitTemplate.convertAndSend(ExchangeConstant.EXCHANGE_ELASTIC, sb.toString(),GsonUtils.obj2Json(dto), correlationData);

    }
}
