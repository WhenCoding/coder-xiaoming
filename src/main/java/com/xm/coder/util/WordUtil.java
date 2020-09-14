package com.xm.coder.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Decoder;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

@Slf4j
@Component
public class WordUtil {
    // 初始化配置
    private static Configuration configuration;

    // 分隔符
    private static String separator = "/";
    // 文件后缀
    private static String fileSuffix = ".docx";


    public static Configuration getConfiguration(){
        //创建配置实例
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_28);
        //设置编码
        configuration.setDefaultEncoding("utf-8");
        configuration.setClassForTemplateLoading(WordUtil.class, "/templates");
        return configuration;
    }

    @PostConstruct
    public void init(){
        configuration = getConfiguration();
    }


    /**
     * 生成doc文件
     *
     * @param ftlFileName 模板ftl文件的名称
     * @param params      动态传入的数据参数
     * @param outFilePath 生成的最终doc文件的保存完整路径
     */
    public void ftlToDoc(String ftlFileName, Map params, String outFilePath) {
        try {
            /** 加载模板文件 **/
            Template template = configuration.getTemplate(ftlFileName);
            /** 指定输出word文件的路径 **/
            File docFile = new File(outFilePath);
            FileOutputStream fos = new FileOutputStream(docFile);
            Writer bufferedWriter = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
            template.process(params, bufferedWriter);
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
        } catch (TemplateException e) {
           log.error("export doc error",e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     *
     * @param dataMap 传入的数据
     * @param docTemplate docx模板文件名称，该文件可以直接使用解压软件打开docx文件，复制word/document.xml文件内容进行修改
     * @param documentXmlRels 图片引用配置文件名
     */
    public static void createDocx(Map dataMap, String docTemplate, String documentXmlRels) throws FileNotFoundException {
        ZipOutputStream zipout = null;
        int len = -1;
        byte[] buffer = new byte[1024];
        int year = LocalDate.now().getYear();
        int monthValue = LocalDate.now().getMonthValue();
        StringBuilder sb = new StringBuilder();
        sb.append(separator).append(year).append(separator).append(monthValue).append(separator).append(Instant.now().toEpochMilli()).append(fileSuffix);
        String path = sb.toString();
        OutputStream os = new FileOutputStream("/Users/xin/Desktop/test.docx");
        zipout = new ZipOutputStream(os);
        try(InputStream inputStream = WordUtil.class.getClassLoader().getResourceAsStream("templates/docxTemplate.docx");) {
            File docxFile = new File("docxTemplate.docx");
            FileUtils.copyToFile(inputStream,docxFile);
            if (!docxFile.exists()) {
                docxFile.createNewFile();
            }
            ZipFile zipFile = new ZipFile(docxFile);
            Enumeration<? extends ZipEntry> zipEntrys = zipFile.entries();
            // 写入并处理图片
            List<Map<String, Object>> picList = (List<Map<String, Object>>) dataMap.get("picList");
            for (Map<String, Object> pic : picList) {
                String imageName = pic.get("name").toString().split("\\.")[0];
                ZipEntry next = new ZipEntry("word" + separator + "media" + separator + pic.get("name"));
                zipout.putNextEntry(new ZipEntry(next.toString()));
                BASE64Decoder decoder = new BASE64Decoder();
                byte[] bytes1 = decoder.decodeBuffer(pic.get("code").toString());
                InputStream in  = new ByteArrayInputStream(bytes1);
                while ((len = in.read(buffer)) != -1) {
                    zipout.write(buffer, 0, len);
                }
                in.close();
                // 处理图片宽高
                InputStream imageImputStream  = new ByteArrayInputStream(bytes1);
                BufferedImage a = ImageIO.read(imageImputStream);
                int width = a.getWidth();
                int height = a.getHeight();
                dataMap.put(imageName+"Width",width);
                dataMap.put(imageName+"Height",height);
                log.info("{}:{},{}:{}",imageName+"Width",width,imageName+"Height",height);
                imageImputStream.close();
            }
            //图片配置文件模板
            ByteArrayInputStream documentXmlRelsInput =getFreemarkerContentInputStream(dataMap, documentXmlRels);
            //内容模板
            ByteArrayInputStream documentInput = getFreemarkerContentInputStream(dataMap, docTemplate);
            //开始覆盖文档------------------
            while (zipEntrys.hasMoreElements()) {
                ZipEntry next = zipEntrys.nextElement();
                InputStream is = zipFile.getInputStream(next);
                if (next.toString().indexOf("media") < 0) {
                    zipout.putNextEntry(new ZipEntry(next.getName()));
                    if (next.getName().indexOf("document.xml.rels") > 0) { //如果是document.xml.rels由我们输入
                        if (documentXmlRelsInput != null) {
                            while ((len = documentXmlRelsInput.read(buffer)) != -1) {
                                zipout.write(buffer, 0, len);
                            }
                            documentXmlRelsInput.close();
                        }
                    } else if ("word/document.xml".equals(next.getName())) {//如果是word/document.xml由我们输入
                        if (documentInput != null) {
                            while ((len = documentInput.read(buffer)) != -1) {
                                zipout.write(buffer, 0, len);
                            }
                            documentInput.close();
                        }
                    } else {
                        while ((len = is.read(buffer)) != -1) {
                            zipout.write(buffer, 0, len);
                        }
                        is.close();
                    }
                }
            }
        } catch (Exception e) {
            log.error("word导出失败:",e);
        }finally {
            if(zipout!=null){
                try {
                    zipout.close();
                } catch (IOException e) {
                    log.error("io异常");
                }
            }
            if(os!=null){
                try {
                    os.close();
                } catch (IOException e) {
                    log.error("io异常");
                }
            }
        }
    }


    /**
     * 处理转义字符
     * @param str
     * @return
     */
    public static String transform(String str) {

        if (str.contains("<") || str.contains(">") || str.contains("&")) {
            str = str.replaceAll("&", "&amp;");
            str = str.replaceAll("<", "&lt;");
            str = str.replaceAll(">", "&gt;");
        }
        return str;
    }



        /**
         * base64转inputStream
         * @param base64string
         * @return
         */
    private static InputStream toInputStream(String base64string){
        ByteArrayInputStream stream = null;
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] bytes1 = decoder.decodeBuffer(base64string);
            stream = new ByteArrayInputStream(bytes1);
        } catch (Exception e) {
            log.error("base64 to inputstream error",e);
        }
        return stream;
    }

    /**
     * 获取模板字符串输入流
     * @param dataMap   参数
     * @param templateName  模板名称
     * @return
     */
    public static ByteArrayInputStream getFreemarkerContentInputStream(Map dataMap, String templateName) {
        ByteArrayInputStream in = null;
        try {
            //获取模板
            Template template = configuration.getTemplate(templateName);
            StringWriter swriter = new StringWriter();
            //生成文件
            template.process(dataMap, swriter);
            in = new ByteArrayInputStream(swriter.toString().getBytes("utf-8"));//这里一定要设置utf-8编码 否则导出的word中中文会是乱码
        } catch (Exception e) {
            log.error("模板生成错误！",e);
        }
        return in;
    }

    /**
     * 删除所有的HTML标签
     * e.g. <div>“3人伪造老干妈印章与腾讯签合同”事件</div> 去掉 div
     *
     * @param source 需要进行除HTML的文本
     * @return
     */
    public static String deleteAllHTMLTag(String source) {
        if(source == null) {
            return "";
        }
        String s = source;
        /** 删除普通标签  */
        s = s.replaceAll("<(S*?)[^>]*>.*?|<.*? />", "");
        /** 删除转义字符 */
//        s = s.replaceAll("&.{2,6}?;", "");
        return s;
    }

    public static void main(String[] args) {
        String s = deleteAllHTMLTag("<div>“3人伪造老干妈印章与腾讯签合同”事件，自2020年07月01日00:00至2020年08月14日13:55期间，互联网上共监测到相关舆情<span>2359</span>条。其中微博声量最大，为<span>1269</span>条，其次是网媒，为<span>457</span>条。该事件负面舆情占比为18.4%，正面舆情占比为0.04%。</div><div>事件首发文章于2020年07月01日16:14发布在徽网，题名为《伪造老干妈印章与腾讯签合同，律师：或涉嫌犯伪造印章罪与诈骗罪》。全网声量最高峰出现在2020年07月02日，共产生<span>938</span>篇相关信息。后续报道主要来源于新浪微博、微信、搜狐新闻、看点快报、今日头条-科技等几大站点。具体分析如下。</div>");
        System.out.println(s);
    }
}
