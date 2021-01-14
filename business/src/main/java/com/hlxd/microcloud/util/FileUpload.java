package com.hlxd.microcloud.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class FileUpload {

    /**
     * @param file             //文件对象
     * @param filePath        //上传路径
     * @param fileName        //文件名
     * @return  文件名
     */
    public static String Upload(MultipartFile file, String filePath, String fileName){
        String extName = ""; // 扩展名格式：
        try {
            if (file.getOriginalFilename().lastIndexOf(".") >= 0){
                extName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            }
            copyFile(file.getInputStream(), filePath, fileName+extName).replaceAll("-", "");
        } catch (IOException e) {
            System.out.println(e);
        }
        return fileName+extName;
    }
    
    /**
     * 写文件到当前目录的目录中
     * @param in
     * @param fileName
     * @throws IOException
     */
    private static String copyFile(InputStream in, String dir, String realName)
            throws IOException {
        File file = new File(dir, realName);
        if (!file.exists()) {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            file.createNewFile();
        }
        FileUtils.copyInputStreamToFile(in, file);
        return realName;
    }
    
	/**
	 * 上传操作,支持多文件上传
	 * @param files 文件数组
	 * @param request 
	 */
	public static Map<String, String> batchUpload(MultipartFile[] files, HttpServletRequest request) {
		Map<String, String> filename = new HashMap<>(); 
		log.info("IP:{},进行文件上传——开始",request.getRemoteAddr());
		int count = 0; //文件数统计
		for(int i=0;i<files.length;i++) {
			if (!files[i].isEmpty()) {
				try {
					log.info("第{}个文件开始上传",i+1);
					
					File file=new File("/static");
					if(!file.exists()){//如果文件夹不存在
						file.mkdir();//创建文件夹
					}
					// 使用UUID确保上传文件不重复
					String uuid = "/static/"+UUID.randomUUID().toString().replaceAll("-", "")+files[i].getOriginalFilename().substring(files[i].getOriginalFilename().lastIndexOf("."));
					filename.put(uuid, files[i].getOriginalFilename());
					BufferedOutputStream out = new BufferedOutputStream(
							new FileOutputStream(new File(uuid)));
					//out.write(files[i].getBytes()); 
					BufferedInputStream bis = new BufferedInputStream(files[i].getInputStream());
					byte[] bytes= new byte[10240];
		            int length = 0;
		            while ((length=bis.read(bytes))!=-1){
		            	out.write(bytes,0,length);
		            }
					out.flush();
					out.close();
					bis.close();
					log.info("第{}个文件上传成功",i+1);
					count++;
				} catch (FileNotFoundException e) {
					log.error("IP:{},{}","拒绝访问");
					e.printStackTrace();
					return null;
				} catch (IOException e) {
					log.error("IP:{},{}","IO流异常");
					e.printStackTrace();
					return null;
				} catch (NullPointerException e) {
					log.error("IP:{},上传文件时获取根路径失败!",request.getRemoteAddr());
					e.printStackTrace();
					return null;
				}
			}
		}
		if(0==count) return null;
		log.info("IP:{},共{}个文件成功上传——结束",request.getRemoteAddr(),count);
		return filename;
	}
	
	/**
     * 删除单个文件
     *
     * @param fileName
     *            要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                System.out.println("删除文件" + fileName + "成功！");
                return true;
            } else {
                System.out.println("删除文件" + fileName + "失败！");
                return false;
            }
        } else {
            System.out.println("删除文件失败：" + fileName + "不存在！");
            return false;
        }
    }

    public static String   getFilename(String agent ,String filename) throws UnsupportedEncodingException {
        if (agent.contains("MSIE")) {
            // IE浏览器
            filename = URLEncoder.encode(filename, "utf-8");
            filename = filename.replace("+", " ");
        } else if (agent.contains("Firefox")) {
            // 火狐浏览器
            BASE64Encoder base64Encoder = new BASE64Encoder();
            filename = "=?utf-8?B?"
                    + base64Encoder.encode(filename.getBytes("utf-8")) + "?=";
        } else {
            // 其它浏览器
            filename = URLEncoder.encode(filename, "utf-8");
        }
        return filename;
    }
}