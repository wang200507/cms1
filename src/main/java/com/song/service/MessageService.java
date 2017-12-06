package com.song.service;

import com.song.entity.MessageEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 * ${DESCRIPTION}
 *
 * @author wangzy
 * @date 2017-11-28
 */
public interface MessageService {
    Page<MessageEntity> getAllByMsgTitleLikeOrMsgContentLike(String msgTitle, String msgContent, Pageable pageable);

    Page<MessageEntity> listAll(Pageable page);

    MessageEntity addMessage(MessageEntity messageEntity);

    int deleteById(String id);
}
