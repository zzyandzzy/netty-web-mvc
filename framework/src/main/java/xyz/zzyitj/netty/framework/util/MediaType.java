package xyz.zzyitj.netty.framework.util;

import java.io.Serializable;

/**
 * @author intent
 * @version 1.0
 * @date 2020/3/25 1:23 下午
 * @email zzy.main@gmail.com
 */
public class MediaType implements Serializable {
    private static final long serialVersionUID = 8818748751217183011L;
    public static final String ALL_VALUE = "*/*";
    public static final String APPLICATION_ALL_VALUE = "application/*";
    public static final String APPLICATION_ATOM_XML_VALUE = "application/atom+xml";
    public static final String APPLICATION_CBOR_VALUE = "application/cbor";
    public static final String APPLICATION_FORM_URLENCODED_VALUE = "application/x-www-form-urlencoded";
    public static final String APPLICATION_JSON_VALUE = "application/json";
    @Deprecated
    public static final String APPLICATION_JSON_UTF8_VALUE = "application/json;charset=UTF-8";
    public static final String APPLICATION_OCTET_STREAM_VALUE = "application/octet-stream";
    public static final String APPLICATION_PDF_VALUE = "application/pdf";
    public static final String APPLICATION_PROBLEM_JSON_VALUE = "application/problem+json";
    @Deprecated
    public static final String APPLICATION_PROBLEM_JSON_UTF8_VALUE = "application/problem+json;charset=UTF-8";
    public static final String APPLICATION_PROBLEM_XML_VALUE = "application/problem+xml";
    public static final String APPLICATION_RSS_XML_VALUE = "application/rss+xml";
    public static final String APPLICATION_STREAM_JSON_VALUE = "application/stream+json";
    public static final String APPLICATION_XHTML_XML_VALUE = "application/xhtml+xml";
    public static final String APPLICATION_XML_VALUE = "application/xml";
    public static final String IMAGE_GIF_VALUE = "image/gif";
    public static final String IMAGE_JPEG_VALUE = "image/jpeg";
    public static final String IMAGE_PNG_VALUE = "image/png";
    public static final String MULTIPART_FORM_DATA_VALUE = "multipart/form-data";
    public static final String MULTIPART_MIXED_VALUE = "multipart/mixed";
    public static final String TEXT_EVENT_STREAM_VALUE = "text/event-stream";
    public static final String TEXT_HTML_VALUE = "text/html";
    public static final String TEXT_MARKDOWN_VALUE = "text/markdown";
    public static final String TEXT_PLAIN_VALUE = "text/plain";
    public static final String TEXT_XML_VALUE = "text/xml";
    private static final String PARAM_QUALITY_FACTOR = "q";
    public static final String CHARSET_UTF8_VALUE = "charset=UTF-8";

}
