package com.info.back.controller;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.info.back.utils.SpringUtils;
import com.info.back.utils.UploadUtils;
import com.info.web.pojo.BackUser;

/**
 * @author ltq 批量上传图片和附件
 */
@SuppressWarnings("restriction")
@Controller
@RequestMapping("upload/")
public class FileUploadController extends BaseController {
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory
			.getLogger(FileUploadController.class);
	private static long maxSize = 1000000;
	private static HashMap<String, String> extMap = new HashMap<String, String>();
	static {
		extMap.put("image", "gif,jpg,jpeg,png,bmp");
		extMap.put("flash", "swf,flv");
		extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
		extMap.put("file",
				"doc,docx,xls,xlsx,ppt,pptx,pdf,htm,html,txt,zip,rar,gz,bz2");
	}

	@RequestMapping(value = "/uploadFiles")
	public void uploadFiles(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BackUser backUser = loginAdminUser(request);
		if (backUser == null) {
			SpringUtils.renderText(response, "请登录后操作！");
		} else {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			List<MultipartFile> files = multipartRequest.getFiles("Filedata");

			String reqPath = null;
			String realFileName = null;
			String suffix = null;
			for (MultipartFile file : files) {
				realFileName = file.getOriginalFilename();
				/** 构建图片保存的目录 **/
				String filePathDir = UploadUtils.getRelatedPath();
				/** 得到图片保存目录的真实路径 **/
				String fileRealPathDir = UploadUtils.getRealPath();
				/** 获取文件的后缀 **/
				suffix = realFileName.substring(realFileName.lastIndexOf("."));
				// /**使用UUID生成文件名称**/
				String fileImageName = UUID.randomUUID().toString() + suffix;// 构建文件名称
				// String fileImageName = multipartFile.getOriginalFilename();
				/** 拼成完整的文件保存路径加文件 **/
				String fileName = fileRealPathDir + File.separator
						+ fileImageName;

				String resultFilePath = filePathDir + "/" + fileImageName;
				File newFile = new File(fileName);
				try {
					FileCopyUtils.copy(file.getBytes(), newFile);
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (StringUtils.isNotBlank(resultFilePath)) {
					resultFilePath = resultFilePath.replaceAll("\\\\", "/");
				}
				reqPath = resultFilePath;
				// 返回路径给页面上传

			}
			String flag = request.getParameter("flag");
			// 返回路径给页面上传
			String ret = "[{\"filepath\":\"" + reqPath + "\",\"filename\":\""
					+ realFileName + "\",\"suffix\":\"" + suffix
					+ "\",\"flag\":\"" + flag + "\"}]";
			SpringUtils.renderJson(response, ret);
		}

	}

	/**
	 * 删除物理文件 2013-12-12 cjx
	 */
	@RequestMapping(value = "/removeImg", method = RequestMethod.POST)
	public void removeImg(HttpServletRequest request,
			HttpServletResponse response) {
		BackUser backUser = loginAdminUser(request);
		if (backUser == null) {
			SpringUtils.renderText(response, "请登录后操作！");
		} else {
			String msg = "0";
			try {
				String imgUrlString = request.getParameter("imgUrl");
				if (StringUtils.isNotBlank(imgUrlString)) {
					String fileRealPathDir = request.getSession()
							.getServletContext().getRealPath(imgUrlString);
					File file = new File(fileRealPathDir);
					if (file.exists()) {
						file.delete();
					}
					msg = "1";
				}
			} catch (Exception e) {
				log.error("removeImg error ", e);
			}
			SpringUtils.renderJson(response, msg);
		}
	}

	@RequestMapping(value = "/editorImg")
	public void attachUpload(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String ret = "";
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest
				.getFile("filedata");
		if (file.getSize() > maxSize) {
			ret = "{\"err\":\"1\",\"msg\":\"上传文件大小超过限制\"}";
			SpringUtils.renderJson(response, ret);
			return;
		}
		String realFileName = file.getOriginalFilename();
		/** 构建图片保存的目录 **/
		String filePathDir = UploadUtils.getRelatedPath();
		/** 得到图片保存目录的真实路径 **/
		String fileRealPathDir = UploadUtils.getRealPath();

		/** 获取文件的后缀 **/
		String suffix = realFileName.substring(realFileName.lastIndexOf("."));
		if (Arrays.<String> asList(extMap.get("image").split(",")).contains(
				realFileName.substring(realFileName.lastIndexOf(".") + 1)
						.toLowerCase())) {
			// /**使用UUID生成文件名称**/
			String fileImageName = UUID.randomUUID().toString() + suffix;// 构建文件名称
			// String fileImageName = multipartFile.getOriginalFilename();
			/** 拼成完整的文件保存路径加文件 **/
			String fileName = fileRealPathDir + File.separator + fileImageName;

			String resultFilePath = filePathDir + "/" + fileImageName;
			File newFile = new File(fileName);
			try {
				FileCopyUtils.copy(file.getBytes(), newFile);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (StringUtils.isNotBlank(resultFilePath)) {
				resultFilePath = resultFilePath.replaceAll("\\\\", "/");
			}
			String prefix = request.getContextPath();
			String reqPath = prefix + resultFilePath;
			// 返回路径给页面上传
			ret = "{\"err\":\"\",\"msg\":\"" + reqPath + "\"}";
		} else {
			ret = "{\"err\":\"\",\"msg\":\"上传文件格式错误\"}";
		}
		SpringUtils.renderText(response, ret);

	}
}
