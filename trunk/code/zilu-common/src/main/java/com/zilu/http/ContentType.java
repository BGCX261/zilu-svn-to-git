package com.zilu.http;


public class ContentType {

	public static String getContentType(String fileType) {
		if (fileType == null) {
			return null;
		} else if ("abs".equalsIgnoreCase(fileType)) {
			return "audio/x-mpeg";
		} else if ("ai".equalsIgnoreCase(fileType)) {
			return "application/postscript";
		} else if ("aif".equalsIgnoreCase(fileType)) {
			return "audio/x-aiff";
		} else if ("aifc".equalsIgnoreCase(fileType)) {
			return "audio/x-aiff";
		} else if ("aiff".equalsIgnoreCase(fileType)) {
			return "audio/x-aiff";
		} else if ("aim".equalsIgnoreCase(fileType)) {
			return "application/x-aim";
		} else if ("art".equalsIgnoreCase(fileType)) {
			return "image/x-jg";
		} else if ("asf".equalsIgnoreCase(fileType)) {
			return "video/x-ms-asf";
		} else if ("asx".equalsIgnoreCase(fileType)) {
			return "video/x-ms-asf";
		} else if ("au".equalsIgnoreCase(fileType)) {
			return "audio/basic";
		} else if ("avi".equalsIgnoreCase(fileType)) {
			return "video/x-msvideo";
		} else if ("avx".equalsIgnoreCase(fileType)) {
			return "video/x-rad-screenplay";
		} else if ("bcpio".equalsIgnoreCase(fileType)) {
			return "application/x-bcpio";
		} else if ("bin".equalsIgnoreCase(fileType)) {
			return "application/octet-stream";
		} else if ("bmp".equalsIgnoreCase(fileType)) {
			return "image/bmp";
		} else if ("body".equalsIgnoreCase(fileType)) {
			return "text/html";
		} else if ("cdf".equalsIgnoreCase(fileType)) {
			return "application/x-netcdf";
		} else if ("cer".equalsIgnoreCase(fileType)) {
			return "application/x-x509-ca-cert";
		} else if ("class".equalsIgnoreCase(fileType)) {
			return "application/java";
		} else if ("cpio".equalsIgnoreCase(fileType)) {
			return "application/x-cpio";
		} else if ("csh".equalsIgnoreCase(fileType)) {
			return "application/x-csh";
		} else if ("css".equalsIgnoreCase(fileType)) {
			return "text/css";
		} else if ("dib".equalsIgnoreCase(fileType)) {
			return "image/bmp";
		} else if ("doc".equalsIgnoreCase(fileType)) {
			return "application/msword";
		} else if ("dtd".equalsIgnoreCase(fileType)) {
			return "application/xml-dtd";
		} else if ("dv".equalsIgnoreCase(fileType)) {
			return "video/x-dv";
		} else if ("dvi".equalsIgnoreCase(fileType)) {
			return "application/x-dvi";
		} else if ("eps".equalsIgnoreCase(fileType)) {
			return "application/postscript";
		} else if ("etx".equalsIgnoreCase(fileType)) {
			return "text/x-setext";
		} else if ("exe".equalsIgnoreCase(fileType)) {
			return "application/octet-stream";
		} else if ("gif".equalsIgnoreCase(fileType)) {
			return "image/gif";
		} else if ("gtar".equalsIgnoreCase(fileType)) {
			return "application/x-gtar";
		} else if ("gz".equalsIgnoreCase(fileType)) {
			return "application/x-gzip";
		} else if ("hdf".equalsIgnoreCase(fileType)) {
			return "application/x-hdf";
		} else if ("htc".equalsIgnoreCase(fileType)) {
			return "text/x-component";
		} else if ("htm".equalsIgnoreCase(fileType)) {
			return "text/html";
		} else if ("html".equalsIgnoreCase(fileType)) {
			return "text/html";
		} else if ("hqx".equalsIgnoreCase(fileType)) {
			return "application/mac-binhex40";
		} else if ("ico".equalsIgnoreCase(fileType)) {
			return "image/x-icon";
		} else if ("ief".equalsIgnoreCase(fileType)) {
			return "image/ief";
		} else if ("jad".equalsIgnoreCase(fileType)) {
			return "text/vnd.sun.j2me.app-descriptor";
		} else if ("jar".equalsIgnoreCase(fileType)) {
			return "application/java-archive";
		} else if ("java".equalsIgnoreCase(fileType)) {
			return "text/plain";
		} else if ("jnlp".equalsIgnoreCase(fileType)) {
			return "application/x-java-jnlp-file";
		} else if ("jpe".equalsIgnoreCase(fileType)) {
			return "image/jpeg";
		} else if ("jpeg".equalsIgnoreCase(fileType)) {
			return "image/jpeg";
		} else if ("jpg".equalsIgnoreCase(fileType)) {
			return "image/jpeg";
		} else if ("js".equalsIgnoreCase(fileType)) {
			return "text/javascript";
		} else if ("jsf".equalsIgnoreCase(fileType)) {
			return "text/plain";
		} else if ("jspf".equalsIgnoreCase(fileType)) {
			return "text/plain";
		} else if ("kar".equalsIgnoreCase(fileType)) {
			return "audio/midi";
		} else if ("latex".equalsIgnoreCase(fileType)) {
			return "application/x-latex";
		} else if ("m3u".equalsIgnoreCase(fileType)) {
			return "audio/x-mpegurl";
		} else if ("mac".equalsIgnoreCase(fileType)) {
			return "image/x-macpaint";
		} else if ("man".equalsIgnoreCase(fileType)) {
			return "application/x-troff-man";
		} else if ("mathml".equalsIgnoreCase(fileType)) {
			return "application/mathml+xml";
		} else if ("me".equalsIgnoreCase(fileType)) {
			return "application/x-troff-me";
		} else if ("mid".equalsIgnoreCase(fileType)) {
			return "audio/midi";
		} else if ("midi".equalsIgnoreCase(fileType)) {
			return "audio/midi";
		} else if ("mif".equalsIgnoreCase(fileType)) {
			return "application/vnd.mif";
		} else if ("mov".equalsIgnoreCase(fileType)) {
			return "video/quicktime";
		} else if ("movie".equalsIgnoreCase(fileType)) {
			return "video/x-sgi-movie";
		} else if ("mp1".equalsIgnoreCase(fileType)) {
			return "audio/x-mpeg";
		} else if ("mp2".equalsIgnoreCase(fileType)) {
			return "audio/mpeg";
		} else if ("mp3".equalsIgnoreCase(fileType)) {
			return "audio/mpeg";
		} else if ("mp4".equalsIgnoreCase(fileType)) {
			return "video/mp4";
		} else if ("mpa".equalsIgnoreCase(fileType)) {
			return "audio/x-mpeg";
		} else if ("mpe".equalsIgnoreCase(fileType)) {
			return "video/mpeg";
		} else if ("mpeg".equalsIgnoreCase(fileType)) {
			return "video/mpeg";
		} else if ("mpega".equalsIgnoreCase(fileType)) {
			return "audio/x-mpeg";
		} else if ("mpg".equalsIgnoreCase(fileType)) {
			return "video/mpeg";
		} else if ("mpv2".equalsIgnoreCase(fileType)) {
			return "video/mpeg2";
		} else if ("ms".equalsIgnoreCase(fileType)) {
			return "application/x-troff-ms";
		} else if ("nc".equalsIgnoreCase(fileType)) {
			return "application/x-netcdf";
		} else if ("oda".equalsIgnoreCase(fileType)) {
			return "application/oda";
		} else if ("odb".equalsIgnoreCase(fileType)) {
			return "application/vnd.oasis.opendocument.database";
		} else if ("odc".equalsIgnoreCase(fileType)) {
			return "application/vnd.oasis.opendocument.chart";
		} else if ("odf".equalsIgnoreCase(fileType)) {
			return "application/vnd.oasis.opendocument.formula";
		} else if ("odg".equalsIgnoreCase(fileType)) {
			return "application/vnd.oasis.opendocument.graphics";
		} else if ("odi".equalsIgnoreCase(fileType)) {
			return "application/vnd.oasis.opendocument.image";
		} else if ("odm".equalsIgnoreCase(fileType)) {
			return "application/vnd.oasis.opendocument.text-master";
		} else if ("odp".equalsIgnoreCase(fileType)) {
			return "application/vnd.oasis.opendocument.presentation";
		} else if ("ods".equalsIgnoreCase(fileType)) {
			return "application/vnd.oasis.opendocument.spreadsheet";
		} else if ("odt".equalsIgnoreCase(fileType)) {
			return "application/vnd.oasis.opendocument.text";
		} else if ("ogg".equalsIgnoreCase(fileType)) {
			return "application/ogg";
		} else if ("otg ".equalsIgnoreCase(fileType)) {
			return "application/vnd.oasis.opendocument.graphics-template";
		} else if ("oth".equalsIgnoreCase(fileType)) {
			return "application/vnd.oasis.opendocument.text-web";
		} else if ("otp".equalsIgnoreCase(fileType)) {
			return "application/vnd.oasis.opendocument.presentation-template";
		} else if ("ots".equalsIgnoreCase(fileType)) {
			return "application/vnd.oasis.opendocument.spreadsheet-template ";
		} else if ("ott".equalsIgnoreCase(fileType)) {
			return "application/vnd.oasis.opendocument.text-template";
		} else if ("pbm".equalsIgnoreCase(fileType)) {
			return "image/x-portable-bitmap";
		} else if ("pct".equalsIgnoreCase(fileType)) {
			return "image/pict";
		} else if ("pdf".equalsIgnoreCase(fileType)) {
			return "application/pdf";
		} else if ("pgm".equalsIgnoreCase(fileType)) {
			return "image/x-portable-graymap";
		} else if ("pic".equalsIgnoreCase(fileType)) {
			return "image/pict";
		} else if ("pict".equalsIgnoreCase(fileType)) {
			return "image/pict";
		} else if ("pls".equalsIgnoreCase(fileType)) {
			return "audio/x-scpls";
		} else if ("png".equalsIgnoreCase(fileType)) {
			return "image/png";
		} else if ("pnm".equalsIgnoreCase(fileType)) {
			return "image/x-portable-anymap";
		} else if ("pnt".equalsIgnoreCase(fileType)) {
			return "image/x-macpaint";
		} else if ("ppm".equalsIgnoreCase(fileType)) {
			return "image/x-portable-pixmap";
		} else if ("pps".equalsIgnoreCase(fileType)) {
			return "application/vnd.ms-powerpoint";
		} else if ("ppt".equalsIgnoreCase(fileType)) {
			return "application/vnd.ms-powerpoint";
		} else if ("ps".equalsIgnoreCase(fileType)) {
			return "application/postscript";
		} else if ("psd".equalsIgnoreCase(fileType)) {
			return "image/x-photoshop";
		} else if ("qt".equalsIgnoreCase(fileType)) {
			return "video/quicktime";
		} else if ("qti".equalsIgnoreCase(fileType)) {
			return "image/x-quicktime";
		} else if ("qtif".equalsIgnoreCase(fileType)) {
			return "image/x-quicktime";
		} else if ("ras".equalsIgnoreCase(fileType)) {
			return "image/x-cmu-raster";
		} else if ("rdf".equalsIgnoreCase(fileType)) {
			return "application/rdf+xml";
		} else if ("rgb".equalsIgnoreCase(fileType)) {
			return "image/x-rgb";
		} else if ("rm".equalsIgnoreCase(fileType)) {
			return "application/vnd.rn-realmedia";
		} else if ("roff".equalsIgnoreCase(fileType)) {
			return "application/x-troff";
		} else if ("rtf".equalsIgnoreCase(fileType)) {
			return "text/rtf";
		} else if ("rtx".equalsIgnoreCase(fileType)) {
			return "text/richtext";
		} else if ("sh".equalsIgnoreCase(fileType)) {
			return "application/x-sh";
		} else if ("shar".equalsIgnoreCase(fileType)) {
			return "application/x-shar";
		} else if ("smf".equalsIgnoreCase(fileType)) {
			return "audio/x-midi";
		} else if ("sit".equalsIgnoreCase(fileType)) {
			return "application/x-stuffit";
		} else if ("snd".equalsIgnoreCase(fileType)) {
			return "audio/basic";
		} else if ("src".equalsIgnoreCase(fileType)) {
			return "application/x-wais-source";
		} else if ("sv4cpio".equalsIgnoreCase(fileType)) {
			return "application/x-sv4cpio";
		} else if ("sv4crc".equalsIgnoreCase(fileType)) {
			return "application/x-sv4crc";
		} else if ("svg".equalsIgnoreCase(fileType)) {
			return "image/svg+xml";
		} else if ("svgz".equalsIgnoreCase(fileType)) {
			return "image/svg+xml";
		} else if ("swf".equalsIgnoreCase(fileType)) {
			return "application/x-shockwave-flash";
		} else if ("t".equalsIgnoreCase(fileType)) {
			return "application/x-troff";
		} else if ("tar".equalsIgnoreCase(fileType)) {
			return "application/x-tar";
		} else if ("tcl".equalsIgnoreCase(fileType)) {
			return "application/x-tcl";
		} else if ("tex".equalsIgnoreCase(fileType)) {
			return "application/x-tex";
		} else if ("texi".equalsIgnoreCase(fileType)) {
			return "application/x-texinfo";
		} else if ("texinfo".equalsIgnoreCase(fileType)) {
			return "application/x-texinfo";
		} else if ("tif".equalsIgnoreCase(fileType)) {
			return "image/tiff";
		} else if ("tiff".equalsIgnoreCase(fileType)) {
			return "image/tiff";
		} else if ("tr".equalsIgnoreCase(fileType)) {
			return "application/x-troff";
		} else if ("tsv".equalsIgnoreCase(fileType)) {
			return "text/tab-separated-values";
		} else if ("txt".equalsIgnoreCase(fileType)) {
			return "text/plain";
		} else if ("ulw".equalsIgnoreCase(fileType)) {
			return "audio/basic";
		} else if ("ustar".equalsIgnoreCase(fileType)) {
			return "application/x-ustar";
		} else if ("vrml".equalsIgnoreCase(fileType)) {
			return "model/vrml";
		} else if ("vsd".equalsIgnoreCase(fileType)) {
			return "application/x-visio";
		} else if ("vxml".equalsIgnoreCase(fileType)) {
			return "application/voicexml+xml";
		} else if ("wav".equalsIgnoreCase(fileType)) {
			return "audio/x-wav";
		} else if ("wbmp".equalsIgnoreCase(fileType)) {
			return "image/vnd.wap.wbmp";
		} else if ("wml".equalsIgnoreCase(fileType)) {
			return "text/vnd.wap.wml";
		} else if ("wmlc".equalsIgnoreCase(fileType)) {
			return "application/vnd.wap.wmlc";
		} else if ("wmls".equalsIgnoreCase(fileType)) {
			return "text/vnd.wap.wmlscript";
		} else if ("wmlscriptc".equalsIgnoreCase(fileType)) {
			return "application/vnd.wap.wmlscriptc";
		} else if ("wmv".equalsIgnoreCase(fileType)) {
			return "video/x-ms-wmv";
		} else if ("wrl".equalsIgnoreCase(fileType)) {
			return "model/vrml";
		} else if ("xbm".equalsIgnoreCase(fileType)) {
			return "image/x-xbitmap";
		} else if ("xht".equalsIgnoreCase(fileType)) {
			return "application/xhtml+xml";
		} else if ("xhtml".equalsIgnoreCase(fileType)) {
			return "application/xhtml+xml";
		} else if ("xls".equalsIgnoreCase(fileType)) {
			return "application/vnd.ms-excel";
		} else if ("xml".equalsIgnoreCase(fileType)) {
			return "application/xml";
		} else if ("xpm".equalsIgnoreCase(fileType)) {
			return "image/x-xpixmap";
		} else if ("xsl".equalsIgnoreCase(fileType)) {
			return "application/xml";
		} else if ("xslt".equalsIgnoreCase(fileType)) {
			return "application/xslt+xml";
		} else if ("xul".equalsIgnoreCase(fileType)) {
			return "application/vnd.mozilla.xul+xml";
		} else if ("xwd".equalsIgnoreCase(fileType)) {
			return "image/x-xwindowdump";
		} else if ("Z".equalsIgnoreCase(fileType)) {
			return "application/x-compress";
		} else if ("z".equalsIgnoreCase(fileType)) {
			return "application/x-compress";
		} else if ("zip".equalsIgnoreCase(fileType)) {
			return "application/zip";
		} else {
			return null;
		}
	}
	
	public static String getContentTypeByName(String filename) {
		int index = filename.lastIndexOf(".");
		if (index == -1) {
			return null;
		}
		else {
			return getContentType(filename.substring(index + 1));
		}
	}

}
