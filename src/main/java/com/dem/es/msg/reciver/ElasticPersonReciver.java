package com.dem.es.msg.reciver;

import com.dem.es.entity.constant.ElasticConstant;
import com.dem.es.entity.constant.ExchangeConstant;
import com.dem.es.msg.ElasticMsgDto;
import com.dem.es.service.ElasticPersonService;
import com.dem.es.util.GsonUtils;
import com.dem.es.util.StringUtil;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ElasticPersonReciver {
    private static final Logger logger = LoggerFactory.getLogger(ElasticPersonReciver.class);

    @Autowired
    private ElasticPersonService elasticPersonService;

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = ElasticConstant.TYPE_PERSON_PERSONINFO + "." + ElasticConstant.UPDATE, durable = "true"),
                    exchange = @Exchange(value = ExchangeConstant.EXCHANGE_ELASTIC, durable = "true", type = ExchangeConstant.TYPE_TOPIC),
                    key = ExchangeConstant.EXCHANGE_ELASTIC + "." + ElasticConstant.INDEX_PERESON + "." + ElasticConstant.TYPE_PERSON_PERSONINFO + "." + ElasticConstant.UPDATE + ".*"
            )
    )
    @RabbitHandler
    public void onUpdateMsg(Message message, Channel channel
    ) throws IOException {
        Long deliveryTag = (Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
        String msg = (String) message.getPayload();
        ElasticMsgDto dto = GsonUtils.getGson().fromJson(msg,ElasticMsgDto.class);
        if (dto == null) {
            return;
        }
        String recId = dto.getId();
        logger.info("收到更新消息,类型:{},Id:{}", ElasticConstant.TYPE_PERSON_PERSONINFO, recId);
        if (StringUtil.isEmpty(recId)) {
            channel.basicAck(deliveryTag, false);
            return;
        }
        try {
            Long id = Long.valueOf(recId);
            elasticPersonService.update(id);
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            channel.basicNack(deliveryTag, false, true);
        }


    }

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = ElasticConstant.TYPE_PERSON_PERSONINFO + "." + ElasticConstant.DEL, durable = "true"),
                    exchange = @Exchange(value = ExchangeConstant.EXCHANGE_ELASTIC, durable = "true", type = ExchangeConstant.TYPE_TOPIC),
                    key = ExchangeConstant.EXCHANGE_ELASTIC + "." + ElasticConstant.INDEX_PERESON + "." + ElasticConstant.TYPE_PERSON_PERSONINFO + "." + ElasticConstant.DEL + ".*"
            )
    )
    @RabbitHandler
    public void onDelMsg(Message message, Channel channel
    ) throws IOException {
        Long deliveryTag = (Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
        String msg = (String) message.getPayload();
        ElasticMsgDto dto = GsonUtils.getGson().fromJson(msg,ElasticMsgDto.class);
        if (dto == null) {
            return;
        }
        logger.info("收到删除消息,类型:{},消息体:{}", ElasticConstant.TYPE_PERSON_PERSONINFO, msg);
        if (StringUtil.isEmpty(msg)) {
            channel.basicAck(deliveryTag, false);
            return;
        }
        try {
            Long id = Long.valueOf(dto.getId());
            elasticPersonService.delete(id);
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            channel.basicNack(deliveryTag, false, true);
        }


    }
}
