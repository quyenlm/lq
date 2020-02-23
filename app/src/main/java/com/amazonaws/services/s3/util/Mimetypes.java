package com.amazonaws.services.s3.util;

import com.amazonaws.regions.ServiceAbbreviations;
import com.amazonaws.util.StringUtils;
import com.tencent.component.net.download.multiplex.http.ContentType;
import com.tencent.imsdk.framework.request.HttpRequestParams;
import com.vk.sdk.api.model.VKAttachments;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public final class Mimetypes {
    public static final String MIMETYPE_GZIP = "application/x-gzip";
    public static final String MIMETYPE_HTML = "text/html";
    public static final String MIMETYPE_OCTET_STREAM = "application/octet-stream";
    public static final String MIMETYPE_XML = "application/xml";
    private static final Log log = LogFactory.getLog(Mimetypes.class);
    private static Mimetypes mimetypes = null;
    private final HashMap<String, String> extensionToMimetypeMap = new HashMap<>();

    Mimetypes() {
        this.extensionToMimetypeMap.put("3gp", "video/3gpp");
        this.extensionToMimetypeMap.put("ai", "application/postscript");
        this.extensionToMimetypeMap.put("aif", "audio/x-aiff");
        this.extensionToMimetypeMap.put("aifc", "audio/x-aiff");
        this.extensionToMimetypeMap.put("aiff", "audio/x-aiff");
        this.extensionToMimetypeMap.put("asc", "text/plain");
        this.extensionToMimetypeMap.put("atom", "application/atom+xml");
        this.extensionToMimetypeMap.put("au", "audio/basic");
        this.extensionToMimetypeMap.put("avi", "video/x-msvideo");
        this.extensionToMimetypeMap.put("bcpio", "application/x-bcpio");
        this.extensionToMimetypeMap.put("bin", "application/octet-stream");
        this.extensionToMimetypeMap.put("bmp", "image/bmp");
        this.extensionToMimetypeMap.put("cdf", "application/x-netcdf");
        this.extensionToMimetypeMap.put("cgm", "image/cgm");
        this.extensionToMimetypeMap.put("class", "application/octet-stream");
        this.extensionToMimetypeMap.put("cpio", "application/x-cpio");
        this.extensionToMimetypeMap.put("cpt", "application/mac-compactpro");
        this.extensionToMimetypeMap.put("csh", "application/x-csh");
        this.extensionToMimetypeMap.put(ContentType.SUBTYPE_CSS, "text/css");
        this.extensionToMimetypeMap.put("dcr", "application/x-director");
        this.extensionToMimetypeMap.put("dif", "video/x-dv");
        this.extensionToMimetypeMap.put("dir", "application/x-director");
        this.extensionToMimetypeMap.put("djv", "image/vnd.djvu");
        this.extensionToMimetypeMap.put("djvu", "image/vnd.djvu");
        this.extensionToMimetypeMap.put("dll", "application/octet-stream");
        this.extensionToMimetypeMap.put("dmg", "application/octet-stream");
        this.extensionToMimetypeMap.put("dms", "application/octet-stream");
        this.extensionToMimetypeMap.put(VKAttachments.TYPE_DOC, "application/msword");
        this.extensionToMimetypeMap.put("dtd", "application/xml-dtd");
        this.extensionToMimetypeMap.put("dv", "video/x-dv");
        this.extensionToMimetypeMap.put("dvi", "application/x-dvi");
        this.extensionToMimetypeMap.put("dxr", "application/x-director");
        this.extensionToMimetypeMap.put("eps", "application/postscript");
        this.extensionToMimetypeMap.put("etx", "text/x-setext");
        this.extensionToMimetypeMap.put("exe", "application/octet-stream");
        this.extensionToMimetypeMap.put("ez", "application/andrew-inset");
        this.extensionToMimetypeMap.put("flv", "video/x-flv");
        this.extensionToMimetypeMap.put(ContentType.SUBTYPE_GIF, "image/gif");
        this.extensionToMimetypeMap.put("gram", "application/srgs");
        this.extensionToMimetypeMap.put("grxml", "application/srgs+xml");
        this.extensionToMimetypeMap.put("gtar", "application/x-gtar");
        this.extensionToMimetypeMap.put("gz", MIMETYPE_GZIP);
        this.extensionToMimetypeMap.put("hdf", "application/x-hdf");
        this.extensionToMimetypeMap.put("hqx", "application/mac-binhex40");
        this.extensionToMimetypeMap.put("htm", MIMETYPE_HTML);
        this.extensionToMimetypeMap.put(ContentType.SUBTYPE_HTML, MIMETYPE_HTML);
        this.extensionToMimetypeMap.put("ice", "x-conference/x-cooltalk");
        this.extensionToMimetypeMap.put("ico", "image/x-icon");
        this.extensionToMimetypeMap.put("ics", "text/calendar");
        this.extensionToMimetypeMap.put("ief", "image/ief");
        this.extensionToMimetypeMap.put("ifb", "text/calendar");
        this.extensionToMimetypeMap.put("iges", "model/iges");
        this.extensionToMimetypeMap.put("igs", "model/iges");
        this.extensionToMimetypeMap.put("jnlp", "application/x-java-jnlp-file");
        this.extensionToMimetypeMap.put("jp2", "image/jp2");
        this.extensionToMimetypeMap.put("jpe", "image/jpeg");
        this.extensionToMimetypeMap.put(ContentType.SUBTYPE_JPEG, "image/jpeg");
        this.extensionToMimetypeMap.put("jpg", "image/jpeg");
        this.extensionToMimetypeMap.put("js", "application/x-javascript");
        this.extensionToMimetypeMap.put("kar", "audio/midi");
        this.extensionToMimetypeMap.put("latex", "application/x-latex");
        this.extensionToMimetypeMap.put("lha", "application/octet-stream");
        this.extensionToMimetypeMap.put("lzh", "application/octet-stream");
        this.extensionToMimetypeMap.put("m3u", "audio/x-mpegurl");
        this.extensionToMimetypeMap.put("m4a", "audio/mp4a-latm");
        this.extensionToMimetypeMap.put("m4p", "audio/mp4a-latm");
        this.extensionToMimetypeMap.put("m4u", "video/vnd.mpegurl");
        this.extensionToMimetypeMap.put("m4v", "video/x-m4v");
        this.extensionToMimetypeMap.put("mac", "image/x-macpaint");
        this.extensionToMimetypeMap.put("man", "application/x-troff-man");
        this.extensionToMimetypeMap.put("mathml", "application/mathml+xml");
        this.extensionToMimetypeMap.put("me", "application/x-troff-me");
        this.extensionToMimetypeMap.put("mesh", "model/mesh");
        this.extensionToMimetypeMap.put(HttpRequestParams.PUSH_XG_ID, "audio/midi");
        this.extensionToMimetypeMap.put("midi", "audio/midi");
        this.extensionToMimetypeMap.put("mif", "application/vnd.mif");
        this.extensionToMimetypeMap.put("mov", "video/quicktime");
        this.extensionToMimetypeMap.put("movie", "video/x-sgi-movie");
        this.extensionToMimetypeMap.put("mp2", "audio/mpeg");
        this.extensionToMimetypeMap.put("mp3", "audio/mpeg");
        this.extensionToMimetypeMap.put("mp4", "video/mp4");
        this.extensionToMimetypeMap.put("mpe", "video/mpeg");
        this.extensionToMimetypeMap.put("mpeg", "video/mpeg");
        this.extensionToMimetypeMap.put("mpg", "video/mpeg");
        this.extensionToMimetypeMap.put("mpga", "audio/mpeg");
        this.extensionToMimetypeMap.put("ms", "application/x-troff-ms");
        this.extensionToMimetypeMap.put("msh", "model/mesh");
        this.extensionToMimetypeMap.put("mxu", "video/vnd.mpegurl");
        this.extensionToMimetypeMap.put("nc", "application/x-netcdf");
        this.extensionToMimetypeMap.put("oda", "application/oda");
        this.extensionToMimetypeMap.put("ogg", "application/ogg");
        this.extensionToMimetypeMap.put("ogv", "video/ogv");
        this.extensionToMimetypeMap.put("pbm", "image/x-portable-bitmap");
        this.extensionToMimetypeMap.put("pct", "image/pict");
        this.extensionToMimetypeMap.put("pdb", "chemical/x-pdb");
        this.extensionToMimetypeMap.put("pdf", "application/pdf");
        this.extensionToMimetypeMap.put("pgm", "image/x-portable-graymap");
        this.extensionToMimetypeMap.put("pgn", "application/x-chess-pgn");
        this.extensionToMimetypeMap.put("pic", "image/pict");
        this.extensionToMimetypeMap.put("pict", "image/pict");
        this.extensionToMimetypeMap.put(ContentType.SUBTYPE_PNG, "image/png");
        this.extensionToMimetypeMap.put("pnm", "image/x-portable-anymap");
        this.extensionToMimetypeMap.put("pnt", "image/x-macpaint");
        this.extensionToMimetypeMap.put("pntg", "image/x-macpaint");
        this.extensionToMimetypeMap.put("ppm", "image/x-portable-pixmap");
        this.extensionToMimetypeMap.put("ppt", "application/vnd.ms-powerpoint");
        this.extensionToMimetypeMap.put("ps", "application/postscript");
        this.extensionToMimetypeMap.put("qt", "video/quicktime");
        this.extensionToMimetypeMap.put("qti", "image/x-quicktime");
        this.extensionToMimetypeMap.put("qtif", "image/x-quicktime");
        this.extensionToMimetypeMap.put("ra", "audio/x-pn-realaudio");
        this.extensionToMimetypeMap.put("ram", "audio/x-pn-realaudio");
        this.extensionToMimetypeMap.put("ras", "image/x-cmu-raster");
        this.extensionToMimetypeMap.put("rdf", "application/rdf+xml");
        this.extensionToMimetypeMap.put("rgb", "image/x-rgb");
        this.extensionToMimetypeMap.put("rm", "application/vnd.rn-realmedia");
        this.extensionToMimetypeMap.put("roff", "application/x-troff");
        this.extensionToMimetypeMap.put("rtf", "text/rtf");
        this.extensionToMimetypeMap.put("rtx", "text/richtext");
        this.extensionToMimetypeMap.put("sgm", "text/sgml");
        this.extensionToMimetypeMap.put("sgml", "text/sgml");
        this.extensionToMimetypeMap.put("sh", "application/x-sh");
        this.extensionToMimetypeMap.put("shar", "application/x-shar");
        this.extensionToMimetypeMap.put("silo", "model/mesh");
        this.extensionToMimetypeMap.put("sit", "application/x-stuffit");
        this.extensionToMimetypeMap.put("skd", "application/x-koan");
        this.extensionToMimetypeMap.put("skm", "application/x-koan");
        this.extensionToMimetypeMap.put("skp", "application/x-koan");
        this.extensionToMimetypeMap.put("skt", "application/x-koan");
        this.extensionToMimetypeMap.put("smi", "application/smil");
        this.extensionToMimetypeMap.put("smil", "application/smil");
        this.extensionToMimetypeMap.put("snd", "audio/basic");
        this.extensionToMimetypeMap.put("so", "application/octet-stream");
        this.extensionToMimetypeMap.put("spl", "application/x-futuresplash");
        this.extensionToMimetypeMap.put("src", "application/x-wais-source");
        this.extensionToMimetypeMap.put("sv4cpio", "application/x-sv4cpio");
        this.extensionToMimetypeMap.put("sv4crc", "application/x-sv4crc");
        this.extensionToMimetypeMap.put("svg", "image/svg+xml");
        this.extensionToMimetypeMap.put(ServiceAbbreviations.SimpleWorkflow, "application/x-shockwave-flash");
        this.extensionToMimetypeMap.put("t", "application/x-troff");
        this.extensionToMimetypeMap.put("tar", "application/x-tar");
        this.extensionToMimetypeMap.put("tcl", "application/x-tcl");
        this.extensionToMimetypeMap.put("tex", "application/x-tex");
        this.extensionToMimetypeMap.put("texi", "application/x-texinfo");
        this.extensionToMimetypeMap.put("texinfo", "application/x-texinfo");
        this.extensionToMimetypeMap.put("tif", "image/tiff");
        this.extensionToMimetypeMap.put("tiff", "image/tiff");
        this.extensionToMimetypeMap.put("tr", "application/x-troff");
        this.extensionToMimetypeMap.put("tsv", "text/tab-separated-values");
        this.extensionToMimetypeMap.put("txt", "text/plain");
        this.extensionToMimetypeMap.put("ustar", "application/x-ustar");
        this.extensionToMimetypeMap.put("vcd", "application/x-cdlink");
        this.extensionToMimetypeMap.put("vrml", "model/vrml");
        this.extensionToMimetypeMap.put("vxml", "application/voicexml+xml");
        this.extensionToMimetypeMap.put("wav", "audio/x-wav");
        this.extensionToMimetypeMap.put("wbmp", "image/vnd.wap.wbmp");
        this.extensionToMimetypeMap.put("wbxml", "application/vnd.wap.wbxml");
        this.extensionToMimetypeMap.put("webm", "video/webm");
        this.extensionToMimetypeMap.put("wml", "text/vnd.wap.wml");
        this.extensionToMimetypeMap.put("wmlc", "application/vnd.wap.wmlc");
        this.extensionToMimetypeMap.put("wmls", "text/vnd.wap.wmlscript");
        this.extensionToMimetypeMap.put("wmlsc", "application/vnd.wap.wmlscriptc");
        this.extensionToMimetypeMap.put("wmv", "video/x-ms-wmv");
        this.extensionToMimetypeMap.put("wrl", "model/vrml");
        this.extensionToMimetypeMap.put("xbm", "image/x-xbitmap");
        this.extensionToMimetypeMap.put("xht", "application/xhtml+xml");
        this.extensionToMimetypeMap.put("xhtml", "application/xhtml+xml");
        this.extensionToMimetypeMap.put("xls", "application/vnd.ms-excel");
        this.extensionToMimetypeMap.put("xml", MIMETYPE_XML);
        this.extensionToMimetypeMap.put("xpm", "image/x-xpixmap");
        this.extensionToMimetypeMap.put("xsl", MIMETYPE_XML);
        this.extensionToMimetypeMap.put("xslt", "application/xslt+xml");
        this.extensionToMimetypeMap.put("xul", "application/vnd.mozilla.xul+xml");
        this.extensionToMimetypeMap.put("xwd", "image/x-xwindowdump");
        this.extensionToMimetypeMap.put("xyz", "chemical/x-xyz");
        this.extensionToMimetypeMap.put("zip", "application/zip");
    }

    public static synchronized Mimetypes getInstance() {
        Mimetypes mimetypes2;
        synchronized (Mimetypes.class) {
            if (mimetypes != null) {
                mimetypes2 = mimetypes;
            } else {
                mimetypes = new Mimetypes();
                if (log.isDebugEnabled()) {
                    Map<String, String> map = mimetypes.extensionToMimetypeMap;
                    for (String extension : map.keySet()) {
                        log.debug("Setting mime type for extension '" + extension + "' to '" + map.get(extension) + "'");
                    }
                }
                mimetypes2 = mimetypes;
            }
        }
        return mimetypes2;
    }

    public void loadAndReplaceMimetypes(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is, StringUtils.UTF8));
        while (true) {
            String line = br.readLine();
            if (line != null) {
                String line2 = line.trim();
                if (!line2.startsWith("#") && line2.length() != 0) {
                    StringTokenizer st = new StringTokenizer(line2, " \t");
                    if (st.countTokens() > 1) {
                        String mimetype = st.nextToken();
                        while (st.hasMoreTokens()) {
                            String extension = st.nextToken();
                            this.extensionToMimetypeMap.put(StringUtils.lowerCase(extension), mimetype);
                            if (log.isDebugEnabled()) {
                                log.debug("Setting mime type for extension '" + StringUtils.lowerCase(extension) + "' to '" + mimetype + "'");
                            }
                        }
                    } else if (log.isDebugEnabled()) {
                        log.debug("Ignoring mimetype with no associated file extensions: '" + line2 + "'");
                    }
                }
            } else {
                return;
            }
        }
    }

    public String getMimetype(String fileName) {
        int lastPeriodIndex = fileName.lastIndexOf(".");
        if (lastPeriodIndex > 0 && lastPeriodIndex + 1 < fileName.length()) {
            String ext = StringUtils.lowerCase(fileName.substring(lastPeriodIndex + 1));
            if (this.extensionToMimetypeMap.containsKey(ext)) {
                String mimetype = this.extensionToMimetypeMap.get(ext);
                if (!log.isDebugEnabled()) {
                    return mimetype;
                }
                log.debug("Recognised extension '" + ext + "', mimetype is: '" + mimetype + "'");
                return mimetype;
            } else if (log.isDebugEnabled()) {
                log.debug("Extension '" + ext + "' is unrecognized in mime type listing, using default mime type: '" + "application/octet-stream" + "'");
            }
        } else if (log.isDebugEnabled()) {
            log.debug("File name has no extension, mime type cannot be recognised for: " + fileName);
        }
        return "application/octet-stream";
    }

    public String getMimetype(File file) {
        return getMimetype(file.getName());
    }

    public void registerMimetype(String extension, String mimetype) {
        this.extensionToMimetypeMap.put(StringUtils.lowerCase(extension), mimetype);
    }
}
