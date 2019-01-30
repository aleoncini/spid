package it.redhat.spid.common;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

public class Util {
    private static final Logger logger = LoggerFactory.getLogger("it.redhat.spid");

    public static Document newEmptyDocument() {
        DocumentBuilderFactory factory = null;
        DocumentBuilder builder = null;
        Document ret;

        try {
            factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ret = builder.newDocument();

        return ret;
    }

    public Node getAuthnRequestNode(String authNRequest){
        Node root = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            Document document = documentBuilder.parse(new ByteArrayInputStream(authNRequest.getBytes("UTF-8")));
            root = document.getElementsByTagName("samlp:AuthnRequest").item(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return root;
    }

    /** (Based on the Google reference implementation, with some modifications suggested in the Google Api's group
     *  -Nate- to avoid buffer size problems in the original code)
     * Retrieves the AuthnRequest from the encoded and compressed String extracted
     * from the URL. The AuthnRequest XML is retrieved in the following order: <p>
     * 1. URL decode <br> 2. Base64 decode <br> 3. Inflate <br> Returns the String
     * format of the AuthnRequest XML.
     *
     * @param encodedRequestXmlString the encoded request xml
     * @return the string format of the authentication request XML.
     *
     */
    public String decodeAuthnRequestXML(String encodedRequestXmlString){
        String uncompressed = null;
        // URL decode
        // No need to URL decode: auto decoded by request.getParameter() method
        // Base64 decode
        try {
            Base64 base64Decoder = new Base64();
            byte[] xmlBytes = encodedRequestXmlString.getBytes("UTF-8");
            byte[] base64DecodedByteArray = base64Decoder.decode(xmlBytes);
            try {
                uncompressed = new String(inflate(base64DecodedByteArray, true));
            } catch (Exception e) {
                uncompressed = new String(inflate(base64DecodedByteArray, false));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        // Uncompress the AuthnRequest data using a stream decompressor, as suggested in discussions
        // of the Google Apps Api's group.
        return uncompressed;
    }


    /** This version is based in the reference implementation, with a modification that avoids using a
     * fixed buffer size during uncompression. (in its place uses an expandable ByteArrayOutputStream).
     * NOTE: not sure whether it is still necessary to call this method with the boolean flag.
     * @param bytes
     * @param nowrap
     * @return an array of bytes with the inflated content
     * @throws IOException
     */
    private static byte[] inflate(byte[] bytes, boolean nowrap) throws IOException {
        logger.info("Calling inflate with flag: " + nowrap + " and a byte array that is " + bytes.length + " long");
        Inflater decompressor = null;
        ByteArrayOutputStream out = null;
        try {
            decompressor = new Inflater(nowrap);
            decompressor.setInput(bytes);
            out = new ByteArrayOutputStream(bytes.length);
            byte[] buf = new byte[1024];
            while (!decompressor.finished()) {
                try {
                    int count = decompressor.inflate(buf); // PROBLEM
                    out.write(buf, 0, count);
                    // added check to avoid loops
                    if (count == 0) {
                        logger.info("Warning: Inflater not being able to process any bytes - will try alternative method");
                        return altInflate(bytes);
                    }
                } catch (DataFormatException e) {
                    logger.info("DataFormatException while inflating " + e.getMessage());
                    return altInflate(bytes);
                } catch (Exception e) {
                    logger.info("Unexpected Exception while inflating " + e.getMessage());
                    return altInflate(bytes);
                } catch (Throwable e) {
                    logger.info("Unexpected Throwable while inflating " + e.getMessage());
                    return altInflate(bytes);
                }
            }
            return out.toByteArray();
        } finally {
            if (decompressor != null) decompressor.end();
            try {
                if (out != null) out.close();
            } catch (IOException ioe) {
			  /*ignore*/
            }
        }
    }

    /** Alternative method for inflating the content, used when the default method is not successful.
     *
     * @param bytes
     * @return an array of bytes containing the inflated content
     * @throws IOException
     */
    protected static byte[] altInflate(byte[] bytes) throws IOException {
        logger.info("AltInflate Processing... ");
        ByteArrayInputStream bais = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        InflaterInputStream iis = null;
        byte[] buf = new byte[1024];
        try {
            logger.info("Proceeding with creation of InflaterInputStream...");
            //	if DEFLATE fails, then attempt to unzip the byte array according to
            //zlib (rfc 1950)
            bais = new ByteArrayInputStream(bytes);
            iis = new InflaterInputStream(bais);
            buf = new byte[1024];
            int count = iis.read(buf); // PROBLEM
            while (count != -1) {
                baos.write(buf, 0, count);
                count = iis.read(buf);
            }
            return baos.toByteArray();
        }  catch (IOException ex) {
            logger.info("Unexpected Exception during altInflate.. ABORTING", ex);
            throw ex;
        } finally {
            if (iis != null) try {
                iis.close();
            } catch (IOException ex2) {}
            if (baos != null) {
                try { baos.close();
                } catch (IOException ex2) {}
            }
        }
    }
}
