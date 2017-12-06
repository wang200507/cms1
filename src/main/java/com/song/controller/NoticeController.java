package com.song.controller;

import com.song.entity.Notice;
import com.song.service.NoticeService;
import com.song.util.Constants;
import com.song.util.DateUtil;
import com.song.util.DseResult;
import com.song.util.Utility;
import org.aspectj.weaver.StaticJoinPointFactory;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ${DESCRIPTION}
 *
 * @author wangzy
 * @date 2017-11-20
 */
@Controller
@RequestMapping(value = "/notice")
public class NoticeController {
    @Autowired
    protected NoticeService noticeService;

   /* @RequestMapping(value = "/list")
    public String index(){
        return "notice/list";
    }
*/
    /*@RequestMapping(value = "/list",method = RequestMethod.GET)
    @ResponseBody
    public String show(@RequestParam(value = "name")String name){
        User user = userService.findUserByName(name);
        if(null != user)
            return user.getId()+"/"+user.getUserCode()+"/"+user.getUserName();
        else return "null";
    }*/

    @RequestMapping(value = "/getList",produces = "application/json;charset=utf-8")
    @ResponseBody
    public Object getList( HttpServletRequest request, String title, Integer page,Integer size) throws UnsupportedEncodingException {
        //解决乱码问题
//        String title = new String(request.getParameter("title").getBytes("iso-8859-1"), "utf-8");
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(page, size,sort);
        Page<Notice> pages=noticeService.findByNameLike(title,pageable);

        return pages;
    }

    @PostMapping(value = "/addNotice")
    @ResponseBody
    public DseResult addNotice(@RequestBody Notice notice){
        if(Utility.isNotEmpty(notice)){
            notice.setDeleted(Constants.DELETED_NO);
            notice.setIsPub(Constants.PUBLISH_NO);
            return DseResult.success(noticeService.addNotice(notice));
        }else{
            return  DseResult.faild("添加失败！");
        }

    }

    @PostMapping(value = "/saveOrUpdate")
    @ResponseBody
    public DseResult saveOrUpdate(@RequestBody Notice notice){
        if(Utility.isNotEmpty(notice)){
            return DseResult.success(noticeService.saveOrUpdate(notice));
        }else{
            return  DseResult.faild("修改失败！");
        }
    }

    @PostMapping(value = "/delete")
    @ResponseBody
    public DseResult delete(String id){
        if(Utility.isNotEmpty(id)){
            int i = noticeService.deleteById(Long.valueOf(id));
            return DseResult.success("删除成功");
        }else{
            return  DseResult.faild("删除失败！");
        }
    }

    @PostMapping(value = "/publish")
    @ResponseBody
    public DseResult publish(String id){
        if(Utility.isNotEmpty(id)){
            int i = noticeService.publish(Long.valueOf(id));
            return DseResult.success("删除成功");
        }else{
            return  DseResult.faild("删除失败！");
        }
    }

    @RequestMapping("/publishNotice")
    @ResponseBody
    public DseResult publishNotice(String id){
        try{
            if(Utility.isNotEmpty(id)){

                Notice notice = noticeService.getNoticeById(Long.valueOf(id));
                if(Utility.isNotEmpty(notice)){
                    noticeService.publish(Long.valueOf(id));                     //将通知改为已发布
                }
                /*List<Notice> noticeList = noticeService.getPublishList();     //获取已发布列表
//                String fileName = renderFileName(Constants.NOTICE_PATH,Constants.SUFFIX_HTML);
                String outPath = Constants.NOTICE_PATH+File.separator+"notice.html";
                PrintStream printStream = new PrintStream(new FileOutputStream(
                        new File(outPath)));
                StringBuilder sb = new StringBuilder();
                sb.append("<html>\n");
                if(Utility.isNotEmpty(noticeList) && noticeList.size() > 0 ){
                    for(Notice notice1:noticeList){
                        sb.append(" <div class=\"_main\">\n");
                        sb.append("   <div class=\"title\">");
                        sb.append(notice1.getTitle());
                        sb.append("   </div>\n");
                        sb.append("   <div class=\"time\">");
                        sb.append(Utility.isNotEmpty(notice1.getCreateTime()) ?DateUtil.TimestampToString(notice1.getCreateTime(),DateUtil.DATETIME_FORMAT) : DateUtil.getCurrentDateString(DateUtil.DATETIME_FORMAT));
                        sb.append("   </div>\n");

                        sb.append("   <div class=\"content\">\n");
                        sb.append(notice1.getContent()).append("\n");
                        sb.append("   </div>\n");
                        sb.append(" </div>\n\n");
                    }
                }
                sb.append("</html>\n");

                printStream.println(sb.toString());
                printStream.close();*/
                return  DseResult.success("发布成功！");
            }else{
                return DseResult.faild("发布失败!");
            }
        }catch (Exception e){
           return DseResult.faild("发布失败!");
        }

    }

    public  Object publis(){
        try{
            String fileName = renderFileName(Constants.NOTICE_PATH,Constants.SUFFIX_HTML);
            String outPath = Constants.NOTICE_PATH+File.separator+fileName;

            List<Notice> noticeList = noticeService.getPublishList();
            StringBuilder sb = new StringBuilder();
            PrintStream printStream = new PrintStream(new FileOutputStream(
                    new File(outPath)));
            sb.append("<html>");
            sb.append("<head>");
            sb.append("<title>每日运营报表</title>");
            sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
            sb.append("<style type=\"text/css\">");
            sb.append("TABLE{border-collapse:collapse;border-left:solid 1 #000000; border-top:solid 1 #000000;padding:5px;}");
            sb.append("TH{border-right:solid 1 #000000;border-bottom:solid 1 #000000;}");
            sb.append("TD{font:normal;border-right:solid 1 #000000;border-bottom:solid 1 #000000;}");
            sb.append("</style></head>");
            sb.append("<body bgcolor=\"#FFF8DC\">");
            sb.append("<div align=\"center\">");
            sb.append("<br/>");
            sb.append("<br/>");

            sb.append("<br/><br/>");
            sb.append("</div></body></html>");
            printStream.println(sb.toString());

        }catch (FileNotFoundException e){
            DseResult.faild("文件生成失败!");
        }
        return null;
    }



    private static void getFile(String path){
        File file = new File(path);
        if(file.isFile() ){
            System.out.println("^^^^^" + file.getName());
            return;
        }else if(file.isDirectory()){
            // get the folder list
            File[] array = file.listFiles();

            for(int i=0;i<array.length;i++){
                if(array[i].isFile()){
                    // only take file name
                    System.out.println("^^^^^" + array[i].getName());
                    // take file path and name
                    System.out.println("#####" + array[i]);
                    // take file path and name
                    System.out.println("*****" + array[i].getPath());
                }else if(array[i].isDirectory()){
                    getFile(array[i].getPath());
                }
            }
        }

    }

    public static String renderFileName(String path,String suffix){
        File file = new File(path);
        String fileName = "";
        List<Long> nameList = new ArrayList<>();
        if(file.isFile() ){
            fileName = file.getName();
            nameList.add(Long.valueOf(fileName));
        }else if(file.isDirectory()){
            File[] array = file.listFiles();
            if(array.length > 0){
                for(int i=0;i<array.length;i++){
                    if(array[i].isFile()){
                        String name = array[i].getName() ;
                        name = name.split("\\.")[0];
                        if(Utility.isNotEmpty(name) && Utility.isNotEmpty(Long.valueOf(name))){
                            nameList.add(Long.valueOf(name));
                        }
                    }else if(array[i].isDirectory()){
                        getFile(array[i].getPath());
                    }
                }
            }else{
                fileName ="0";
            }
        }else {
            file.mkdir();
            fileName ="0";
        }
        if(Utility.isNotEmpty(nameList) && nameList.size() > 0){
            fileName =  String.valueOf(Collections.max(nameList)+1);
        }
        return  fileName+suffix;
    }


    public static void main(String[] args) {
//        publis();
//        getFile(Constants.NOTICE_PATH);
//        getFile("D:/test/0.html");
//        System.out.println( renderFileName(Constants.NOTICE_PATH,Constants.SUFFIX_HTML));

    }

}
