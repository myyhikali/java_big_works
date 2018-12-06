package fileinput;


//import java.io.File;
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.UUID;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.multipart.MultipartHttpServletRequest;
////
////import com.diecolor.utils.Constants;
////import com.diecolor.utils.FileUtil;
////import com.diecolor.utils.TimeUtils;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//
//@Controller
//@RequestMapping("/upload")
public class UploadController {

//    /**
//     * 上传文件
//     * @param request
//     * @param response
//     * @param file 上传的文件，支持多文件
//     * @throws Exception
//     */
//    @RequestMapping("/insert")
//    public void insert(HttpServletRequest request,HttpServletResponse response
//            ,@RequestParam("file") MultipartFile[] file) throws Exception{
//        if(file!=null&&file.length>0){
//            //组合image名称，“;隔开”
//            List<String> fileName =new ArrayList<String>();
//
//            try {
//                for (int i = 0; i < file.length; i++) {
//                    if (!file[i].isEmpty()) {
//
//                        //上传文件，随机名称，";"分号隔开
//                        fileName.add(FileUtil.uploadImage(request, "/upload/"+TimeUtils.formateString(new Date(), "yyyy-MM-dd")+"/", file[i], true));
//                    }
//                }
//
//                //上传成功
//                if(fileName!=null&&fileName.size()>0){
//                    System.out.println("上传成功！");
//                    Constants.printJson(response, fileName);;
//                }else {
//                    Constants.printJson(response, "上传失败！文件格式错误！");
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//                Constants.printJson(response, "上传出现异常！异常出现在：class.UploadController.insert()");
//            }
//        }else {
//            Constants.printJson(response, "没有检测到文件！");
//        }
//    }
//    /**
//     * 上传图片
//     *  原名称
//     * @param request 请求
//     * @param path_deposit 存放位置(路径)
//     * @param file 文件
//     * @param isRandomName 是否随机名称
//     * @return 完整文件路径
//     */
//    public static String uploadImage(HttpServletRequest request,String path_deposit,MultipartFile file,boolean isRandomName) {
//        //上传
//        try {
//            String[] typeImg={"gif","png","jpg"};
//
//            if(file!=null){
//                String origName=file.getOriginalFilename();// 文件原名称
//                System.out.println("上传的文件原名称:"+origName);
//                // 判断文件类型
//                String type=origName.indexOf(".")!=-1?origName.substring(origName.lastIndexOf(".")+1, origName.length()):null;
//                if (type!=null) {
//                    boolean booIsType=false;
//                    for (int i = 0; i < typeImg.length; i++) {
//                        if (typeImg[i].equals(type.toLowerCase())) {
//                            booIsType=true;
//                        }
//                    }
//                    //类型正确
//                    if (booIsType) {
//                        //存放图片文件的路径
//                        String path=request.getSession().getServletContext().getRealPath(path_deposit);
//                        //组合名称
//                        String fileSrc="";
//                        //是否随机名称
//                        if(isRandomName){
//                            origName=TimeUtils.formateString(new Date(), "yyyy-MM-dd-")+ UUID.randomUUID().toString()+origName.substring(origName.lastIndexOf("."));
//                        }
//                        //判断是否存在目录
//                        File targetFile=new File(path,origName);
//                        if(!targetFile.exists()){
//                            targetFile.mkdirs();//创建目录
//                        }
//                        //上传
//                        file.transferTo(targetFile);
//                        //完整路径
//                        fileSrc=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+path_deposit+origName;
//                        System.out.println("图片上传成功:"+fileSrc);
//                        return fileSrc;
//                    }
//                }
//            }
//            return null;
//        }catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//    public static String formateString(Date date,String pattern) {
//        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
//        String str = sdf.format(date);
//        return str;
//    }
//
//    public static void printJson(HttpServletResponse response,Object obj) {
//        response.setHeader("Access-Control-Allow-Origin", "*");
//        response.setContentType("text/json;charset=UTF-8");
//        String temp="";
//
//        Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
//
//        if (obj!=null) {
//            temp=gson.toJson(obj);
//        }
//        try {
//            response.getWriter().print(temp);
//            response.getWriter().flush();
//            response.getWriter().close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
