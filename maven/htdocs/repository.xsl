<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<!-- 
  This is a naive OBR-XML-to-HTML tranformation style sheet 
  It should really be built from the HTML templates, instead
  of using hard-coded HTML.
  -->

<xsl:key name="category"  match="bundle" use="bundle-category"/>
<xsl:key name="apivendor" match="bundle" use="bundle-apivendor"/>
<xsl:key name="vendor"    match="bundle" use="bundle-vendor"/>

<xsl:template match="/">

<html>


<head>
<meta http-equiv = "Content-Type"
      content    = "text/html; charset=ISO-8859-1"/>

<meta http-equiv = "CACHE-CONTROL"
      content    = "NO-CACHE"/>

<meta name       = "description"
      content    = "{bundles/repository-bundle-name}"/>

<meta name       = "title"
      content    = "{bundles/repository-bundle-name}"/>

<title><xsl:value-of select="bundles/repository-bundle-name"/></title>
<LINK href="osgirepo.css" rel="stylesheet" type="text/css"/>

</head>

<body>
    

<a name="top"></a>
<table border="0" cellpadding="0" cellspacing="0">

  <tr>
      <!--
    <td width="0%">
      <div style="text-align:left; margin-right: 10px; margin-left: 10px; margin-top:10 px;">
      <p>
      <a class="navigation_enabled" href="/index.html">OSGi-Repository</a><br/>
      </p>
      <br/>
      </div>
    </td>
    -->
    <td width="100%">
      <table class="bodytable">
	<tr>
	  <td class="pageheader">
	    <div style="margin-left: 5px; margin-top: 5px; margin-bottom: 5px;">
	    <h2><xsl:value-of select="bundles/repository-bundle-name"/></h2>
	    
	    <table>
	        <tr>
	        <td>
	     <div style="font-style: italic;">
	    
	    This is an OBR <b>backward</b> compatible bundle repository.
	    </div>
	        </td>
	        <td width="50%">
    This repository is generated on <xsl:value-of select="bundles/date"/></td>
	        </tr>
	    </table>
	    </div>
	  </td>
	</tr>
        <tr><td height="5"></td></tr>
	<tr>
	  <td class="mainblock">
	    
	    <div style="margin-left: 5px; margin-top: 5px; margin-bottom: 5px; margin-right: 5px;">

<!-- start -->

<!--<a href="http://www.knopflerfish.org/images/repo_desktop_host.gif"><img src="http://www.knopflerfish.org/images/repo_desktop_host_320.gif" border="0" align="right" alt="Screenshot of the KF bundlerepository, running as a plugin to the desktop"/></a>
-->
<p>
OBR is a bundle repository format introduced by the 
<a href="http://oscar-osgi.sourceforge.net/repo/repository.xml">Oscar</a> 
OSGi project. The format consists
of an XML file describing all available bundles and an OSGi OBR bundle which
should be installed on an OSGi framework. When started, this OBR bundle can
read the XML file, list bundles, and install bundles and their dependencies.
</p>

<p>
The repository URL is <a href="http://osgirepo.berlios.de/repository.xml">
http://osgirepo.berlios.de/repository.xml</a>
</p>

<p>
Further Repositories:
<ul>
    <li>OSCAR: <a href="http://oscar-osgi.sourceforge.net/repo/repository.xml">http://oscar-osgi.sourceforge.net/repo/repository.xml</a></li>
    <li>Knopflerfish: <a href="http://www.knopflerfish.org/repo/repository.xml">http://www.knopflerfish.org/repo/repository.xml</a></li>
</ul>
</p>

<p>
<b>Important</b>: You need one of the following bundle loaders:
<ul>
    <li>Jadabs - comming up</li>
    <li>OSCAR - bundlerepository.jar</li>
    <li><a href="http://www.knopflerfish.org/repo/jars/bundlerepository/bundlerepository_all-1.1.0.jar">Knopflerfish - bundlerepository</a> </li>
</ul>

</p>

<br clear="all"/>

<h4>Available bundles, sorted by API specification</h4>

<table>

 <tr>
  <th>Bundle</th>
  <th>Description</th>
  <th>Version</th>
  <th>Links<br/></th>
  <th>Vendor</th>
 </tr>

 <xsl:for-each select="/bundles/bundle/bundle-apivendor[not(. = preceding::bundle-apivendor)]">
 
 <!--<xsl:for-each select="bundles/bundle">-->
 <xsl:sort select="." order="descending"/>

 <tr>

  <td style="background-color: #eeeeee;" colspan="5">
   <xsl:choose>
    <xsl:when test=". = ''">
     Other
    </xsl:when>
    <xsl:otherwise>
     <xsl:value-of select="."/>
    </xsl:otherwise>
   </xsl:choose>
  </td>
 </tr>
 
  <xsl:for-each select="key('apivendor',.)">
  <xsl:sort select="."/>

 <tr>

  <td>
   <a href="{bundle-updatelocation}"><xsl:value-of select="bundle-name"/></a>
  </td>

  <td style="width: 300px;">
   <xsl:value-of select="bundle-description"/>
  </td>

  <td>
   <xsl:value-of select="bundle-version"/>
  </td>

  <td>
   <xsl:if test="bundle-docurl != ''">
    <a href="{bundle-docurl}">docs</a>
   </xsl:if>

  </td>
  <td>
   <xsl:value-of select="bundle-vendor"/>
  </td>

 </tr>
 </xsl:for-each>
 </xsl:for-each>

</table>


<!-- stop -->

<p>
Site template taken from Knopflerfish.org.
</p>
	    <a class="top" href="#top">top</a>
	    </div>
	  </td>
	</tr>
      </table>
    </td>
  </tr>
</table>

</body>
</html>

</xsl:template>

</xsl:stylesheet>
