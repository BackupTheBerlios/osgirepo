// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   XmlUtils.java

package seguedev.misc;

import java.io.*;
import java.net.URL;
import java.util.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.apache.xml.utils.PrefixResolver;
import org.apache.xpath.XPathAPI;
import org.apache.xpath.objects.XObject;
import org.w3c.dom.*;
import org.xml.sax.InputSource;

public class XmlUtils
{
    static class NodeListImpl extends ArrayList
        implements NodeList
    {

        public Node item(int i)
        {
            return (Node)get(i);
        }

        public int getLength()
        {
            return size();
        }

        protected NodeListImpl()
        {
        }
    }

    static class GpbPrefixResolver
        implements PrefixResolver
    {

        private void scanAndPopulate(Element root)
        {
            String prfx = root.getPrefix();
            String ns = root.getNamespaceURI();
            if(!nsMap.containsKey(prfx))
                nsMap.put(prfx, ns);
            NodeList list = root.getChildNodes();
            XmlUtils.filterOutNonElements(list);
            for(int i = 0; i < list.getLength(); i++)
                if(list.item(i) instanceof Element)
                {
                    Element elem = (Element)list.item(i);
                    scanAndPopulate(elem);
                }

        }

        public String getNamespaceForPrefix(String prefix)
        {
            if(prefix == null || prefix.equals(""))
                return "";
            if(!nsMap.containsKey(prefix))
                scanAndPopulate(refDoc.getDocumentElement());
            return (String)nsMap.get(prefix);
        }

        public String getNamespaceForPrefix(String prefix, Node namespaceContext)
        {
            return getNamespaceForPrefix(prefix);
        }

        public boolean handlesNullPrefixes()
        {
            return true;
        }

        public String getBaseIdentifier()
        {
            return null;
        }

        HashMap nsMap;
        Document refDoc;

        protected GpbPrefixResolver(Document _refDoc, HashMap map)
        {
            nsMap = null;
            refDoc = null;
            refDoc = _refDoc;
            nsMap = map;
        }
    }


    public XmlUtils()
    {
    }

    public static void cleanWhiteSpaceNodes(Element node, boolean deep)
    {
        NodeList list = node.getChildNodes();
        ArrayList temp = new ArrayList();
        for(int i = 0; i < list.getLength(); i++)
        {
            Node n = list.item(i);
            short type = n.getNodeType();
            if(type == 1)
            {
                Element e = (Element)n;
                cleanWhiteSpaceNodes(e, deep);
            } else
            if(type == 3)
            {
                Text text = (Text)n;
                String val = text.getData();
                if(val.trim().equals(""))
                    temp.add(text);
            }
        }

        for(Iterator i = temp.iterator(); i.hasNext(); node.removeChild((Node)i.next()));
    }

    public static String convertToString(Node node)
        throws Exception
    {
        node.normalize();
        StringWriter out = new StringWriter();
        OutputFormat fmt = new OutputFormat();
        fmt.setIndent(4);
        fmt.setOmitXMLDeclaration(true);
        XMLSerializer ser = new XMLSerializer(out, fmt);
        ser.serialize((Element)node);
        return out.toString();
    }

    public static NodeList filterNodeList(NodeList list, short type)
    {
        NodeListImpl nlist = new NodeListImpl();
        for(int i = 0; i < list.getLength(); i++)
        {
            Node node = list.item(i);
            if(node.getNodeType() == type)
                nlist.add(node);
        }

        return nlist;
    }

    public static NodeList filterOutNonElements(NodeList list)
    {
        return filterNodeList(list, (short)1);
    }

    public static Node getNodeFromXpath(Node ctx, String xpath)
        throws Exception
    {
        return XPathAPI.selectSingleNode(ctx, xpath);
    }

    public static NodeList getNodesFromXpath(Node ctx, String xpath)
        throws Exception
    {
        return XPathAPI.selectNodeList(ctx, xpath);
    }

    public static String getValueFromXpath(Node ctx, String xpath)
        throws Exception
    {
        XObject xobj = XPathAPI.eval(ctx, xpath);
        return xobj.toString();
    }

    public static Node getNodeFromXpathNS(Node ctx, String xpath)
        throws Exception
    {
        GpbPrefixResolver res = new GpbPrefixResolver(ctx.getOwnerDocument(), nameSpacePrefixCache);
        XObject xobj = XPathAPI.eval(ctx, xpath, res);
        if(xobj != null)
        {
            NodeList list = xobj.nodelist();
            if(list.getLength() > 0)
                return list.item(0);
            else
                return null;
        } else
        {
            return null;
        }
    }

    public static NodeList getNodesFromXpathNS(Node ctx, String xpath)
        throws Exception
    {
        GpbPrefixResolver res = new GpbPrefixResolver(ctx.getOwnerDocument(), nameSpacePrefixCache);
        XObject xobj = XPathAPI.eval(ctx, xpath, res);
        if(xobj != null)
            return xobj.nodelist();
        else
            return null;
    }

    public static String getValueFromXpathNS(Node ctx, String xpath)
        throws Exception
    {
        GpbPrefixResolver res = new GpbPrefixResolver(ctx.getOwnerDocument(), nameSpacePrefixCache);
        XObject xobj = XPathAPI.eval(ctx, xpath, res);
        return xobj.toString();
    }

    public static Document loadDocument(Reader reader)
        throws Exception
    {
        DocumentBuilder parser = dbFactory.newDocumentBuilder();
        return parser.parse(new InputSource(reader));
    }

    public static Document loadDocument(String url)
        throws Exception
    {
        DocumentBuilder parser = dbFactory.newDocumentBuilder();
        return parser.parse(url);
    }

    public static Document loadDocument(URL url)
        throws Exception
    {
        DocumentBuilder parser = dbFactory.newDocumentBuilder();
        return parser.parse(new InputSource(url.openStream()));
    }

    public static Document newDocument()
        throws Exception
    {
        DocumentBuilder parser = dbFactory.newDocumentBuilder();
        return parser.newDocument();
    }

    public static Document loadDocumentNS(Reader reader)
        throws Exception
    {
        DocumentBuilder parser = dbFactoryForNameSpaceBuilders.newDocumentBuilder();
        return parser.parse(new InputSource(reader));
    }

    public static Document loadDocumentNS(String url)
        throws Exception
    {
        DocumentBuilder parser = dbFactoryForNameSpaceBuilders.newDocumentBuilder();
        return parser.parse(url);
    }

    public static Document loadDocumentNS(URL url)
        throws Exception
    {
        DocumentBuilder parser = dbFactoryForNameSpaceBuilders.newDocumentBuilder();
        return parser.parse(new InputSource(url.openStream()));
    }

    public static Document newDocumentNS()
        throws Exception
    {
        DocumentBuilder parser = dbFactoryForNameSpaceBuilders.newDocumentBuilder();
        return parser.newDocument();
    }

    public static byte[] transformToByte(Node node)
        throws Exception
    {
        return transformToString(node).getBytes();
    }

    public static String transformToString(Node node)
        throws Exception
    {
        ByteArrayOutputStream bOut;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();
        Node nodeImp = doc.importNode(node, true);
        doc.appendChild(nodeImp);
        TransformerFactory factory2 = TransformerFactory.newInstance();
        Transformer transformer = factory2.newTransformer();
        bOut = new ByteArrayOutputStream();
        transformer.transform(new DOMSource(doc), new StreamResult(bOut));
        return bOut.toString();
        Exception e;
        e;
        System.out.println("Exception in XML transformation " + e);
        throw new Exception("Error during XML Transformation ");
    }

    static DocumentBuilderFactory dbFactory = null;
    static DocumentBuilderFactory dbFactoryForNameSpaceBuilders;
    static HashMap nameSpacePrefixCache = new HashMap();

    static 
    {
        dbFactoryForNameSpaceBuilders = null;
        try
        {
            dbFactory = DocumentBuilderFactory.newInstance();
            dbFactoryForNameSpaceBuilders = DocumentBuilderFactory.newInstance();
            dbFactoryForNameSpaceBuilders.setNamespaceAware(true);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
