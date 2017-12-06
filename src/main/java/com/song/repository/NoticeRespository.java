package com.song.repository;

import com.song.entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author wangzy
 * @date 2017-11-20
 */
@Repository
public  interface NoticeRespository extends JpaRepository<Notice, Long> {

    @Query("select t from Notice t where 1=1 and t.deleted = 0 ")
    List<Notice> getList();

    @Query("select t from Notice t where 1=1 and t.deleted = 0 and t.title like  CONCAT('%',:title,'%') ")
    Page<Notice> findByNameLike(@Param("title") String title, Pageable pageable);

    @Transactional
    @Modifying
    @Query("update Notice t set t.isPub = 1 where t.id = ?1")
    int  publishNotice(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query("update  Notice t set t.deleted = 1 where t.id = ?1")
    int deleteById(Long id);

    Notice getNoticeById(Long id);

    List<Notice> getNoticesByDeletedAndIsPub(int deleted,int isPub);
}
