package com.song.service.impl;

import com.song.entity.Notice;
import com.song.repository.NoticeRespository;
import com.song.service.NoticeService;
import com.song.util.BeanUtils;
import com.song.util.Constants;
import com.song.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author wangzy
 * @date 2017-11-20
 */
@Service
public class NoticeServiceImpl implements NoticeService {


    @Autowired
    NoticeRespository noticeRespository;

    @Override
    public Object add(Notice notice) {
        return noticeRespository.save(notice);
    }

    @Override
    public Page<Notice> findByNameLike(String title, Pageable pageable) {
        return noticeRespository.findByNameLike(title,pageable);
    }

    @Override
    public List<Notice> getList() {
        return noticeRespository.getList();
    }

    @Override
    public Notice addNotice(Notice notice) {
        return noticeRespository.save(notice);
    }

    @Override
    public Notice saveOrUpdate(Notice notice) {
        Long id = notice.getId();
        Notice oldNotice = noticeRespository.findOne(id);
        BeanUtils.copyNotNullField(notice,oldNotice);
        oldNotice.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return  noticeRespository.save(notice);
    }

    @Override
    public int deleteById(Long id) {
        try{
           return noticeRespository.deleteById(id);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }


    }

    @Override
    public int publish(Long id) {
        try{
            return noticeRespository.publishNotice(id);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public Notice getNoticeById(Long id) {
        return noticeRespository.getNoticeById(id);
    }

    @Override
    public List<Notice> getPublishList() {
        return noticeRespository.getNoticesByDeletedAndIsPub(Constants.DELETED_NO,Constants.PUBLISH_YES);
    }
}
