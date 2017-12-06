package com.song.service.impl;

import com.song.entity.MessageEntity;
import com.song.repository.MessageRespository;
import com.song.service.MessageService;
import com.song.util.Constants;
import com.song.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;



/**
 * ${DESCRIPTION}
 *
 * @author wangzy
 * @date 2017-11-28
 */
@Service
public class MessageServiceImpl implements MessageService{

    @Autowired
    MessageRespository messageRespository;

    @Override
    public Page<MessageEntity> getAllByMsgTitleLikeOrMsgContentLike(String msgTitle, String msgContent, Pageable pageable) {
        if(Utility.isNotEmpty(msgTitle) && Utility.isEmpty(msgContent)){
            msgTitle = "%"+msgTitle+"%";
            return messageRespository.getAllByMsgTitleLikeAndDeletedIs(msgTitle,Constants.DELETED_NO,pageable);
        }else if(Utility.isEmpty(msgTitle) && Utility.isNotEmpty(msgContent)){
            msgContent = "%"+msgContent+"%";
            return messageRespository.getAllByMsgContentLikeAndDeletedIs(msgContent,Constants.DELETED_NO,pageable);
        }else if(Utility.isNotEmpty(msgTitle) && Utility.isNotEmpty(msgContent)){
            msgTitle = "%"+msgTitle+"%";
            msgContent = "%"+msgContent+"%";
            return messageRespository.getMsgList(msgTitle,msgContent,pageable);
        }else{
            return messageRespository.findAllByDeletedIs(Constants.DELETED_NO,pageable);
        }


    }

    @Override
    public Page<MessageEntity> listAll(Pageable page) {
        return messageRespository.findAllByDeletedIs(Constants.DELETED_NO,page);
    }

    @Override
    public MessageEntity addMessage(MessageEntity messageEntity) {
        return messageRespository.save(messageEntity);
    }

    @Override
    public int deleteById(String id) {
        try{
            return messageRespository.deleteById(id);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }
}
