package com.song.service;

import com.song.entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author wangzy
 * @date 2017-11-20
 */
public interface NoticeService {
    Object add(Notice notice);

    Page<Notice> findByNameLike(String title, Pageable pageable);
    List<Notice> getList();
    Notice addNotice( Notice notice);
    Notice saveOrUpdate( Notice notice);
    int deleteById(Long id);

    int publish(Long id);
    Notice getNoticeById(Long id);
    List<Notice> getPublishList();
}
