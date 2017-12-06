package com.song.controller;

import com.song.entity.MessageEntity;
import com.song.entity.User;
import com.song.service.MessageService;
import com.song.system.controller.BaseController;
import com.song.util.Constants;
import com.song.util.DseResult;
import com.song.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;

/**
 * ${首页滚动消息controller}
 *
 * @author wangzy
 * @date 2017-11-28
 */
@Controller
@RequestMapping("/message")
public class MessageController  extends BaseController{

    @Autowired
    private MessageService messageService;

    @RequestMapping(value = "/getMsgList")
    @ResponseBody
    public Object getMsgList( String title, String content, Integer page,Integer size){
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        Pageable pageable = new PageRequest(page, size,sort);
        Page<MessageEntity> pages=messageService.getAllByMsgTitleLikeOrMsgContentLike(title,content,pageable);

        return pages;
    }

    @RequestMapping(value = "/listAll")
    @ResponseBody
    public Object listAll(Integer page,Integer size){
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        Pageable pageable = new PageRequest(page, size,sort);
        Page<MessageEntity> pages=messageService.listAll(pageable);

        return pages;
    }

    @PostMapping(value = "/addMessage")
    @ResponseBody
    public DseResult addMessage(@RequestBody MessageEntity messageEntity){
        User user = getUser();
        if(Utility.isNotEmpty(messageEntity) && Utility.isNotEmpty(messageEntity.getId())){
            messageEntity.setDeleted(Constants.DELETED_NO);
            messageEntity.setCreateUser(user.getUserName());
            messageEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
            return DseResult.success(messageService.addMessage(messageEntity));
        }else{
            return  DseResult.faild("添加失败！请检查参数是否完整！");
        }
    }

    @PostMapping(value = "/delete")
    @ResponseBody
    public DseResult delete(String id){
        if(Utility.isNotEmpty(id)){
            int i = messageService.deleteById(id);
            if(i>0){
                return DseResult.success("删除成功！");
            }else{
                return DseResult.faild("删除失败！");
            }
        }else{
            return  DseResult.faild("未获取到ID！");
        }
    }
}
