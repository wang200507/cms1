package com.song.repository;

import com.song.entity.MessageEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * ${DESCRIPTION}
 *
 * @author wangzy
 * @date 2017-11-28
 */
@Repository
public interface MessageRespository extends JpaRepository<MessageEntity, String> {


    @Query("select t from MessageEntity t where t.deleted = 0 and ( t.msgTitle like ?1 or t.msgContent like ?2  )")
    Page<MessageEntity> getMsgList( String msgTitle, String msgContent, Pageable pageable);

    Page<MessageEntity> getAllByMsgTitleLikeAndDeletedIs(String msgTitle ,Integer deleted,Pageable pageable);

    Page<MessageEntity> getAllByMsgContentLikeAndDeletedIs(String msgTitle,Integer deleted,Pageable pageable);

    Page<MessageEntity> findAllByDeletedIs(Integer deleted,Pageable pageable);

    @Transactional
    @Modifying
    @Query("update  MessageEntity t set t.deleted = 1 where t.id = ?1")
    int deleteById(String id);
}
