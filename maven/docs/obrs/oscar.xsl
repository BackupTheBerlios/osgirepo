<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="/">

<html>
<body> 
<font face="sans-serif">

<div style="text-align: center;">

<img src="obr-logo.jpg" border="0"/>
<br/>
<font size="+3">Oscar Bundle Repository</font>

<hr/>
<a href="http://oscar.objectweb.org">Where is Oscar?</a> |
<a href="#obr">What is OBR?</a> |
<a href="#bundles">Bundles</a> |
<a href="#submit">Submitting Bundles</a> |
<a href="#contact">Contact</a>
<hr/>
                                                                                
</div>

<i>
                                                                                
<h1>News</h1>
                                                                                
<ul>
                                                                                
<li>
Added JMX-based Jmood Management Agent bundle to the repository
meta-data. (December 16th, 2004)
</li>

<li>
Released another update to the HTTP Service bundle that improves
servlet URL handling and adds HTTPS support;
see the <a href="http/changes.html">change log</a>
for details. (December 13th, 2004)
</li>

<li>
Released an update to the HTTP Service bundle that fixes
two bugs; see the <a href="http/changes.html">change log</a>
for details. (December 2nd, 2004)
</li>

<li>
Released an update to the OSGi Service bundle, because it was
missing the UPnP package in its manifest export header.
(October 11th, 2004)
</li>

<li>
Released an update to URL Handlers service that potentially impacts Oscar
users trying to use legacy libraries in bundles; check the
<a href="urlhandlers/index.html">documentation</a> for details
and update to the new version using the <tt>obr update</tt>
command. NOTE: To fully function, this bundle requires Oscar 1.0.3,
which is not yet available, but a snapshot can be obtained from the
Oscar subversion repository. (October 8th, 2004)
</li>

</ul>
                                                                                
                                                                                
<small>
(<a href="../news.html">News archive</a>)
</small>
</i>
                                                                                
<hr/>

<a name="obr"></a>
<h2>What is OBR?</h2>
                                                                                
<p>
OBR is an incubator and repository for OSGi bundles. For those who are
unaware, the <a href="http://www.osgi.org">OSGi Alliance</a> is a
consortium working on defining standards for delivering and managing
dynamically downloadable services into networked environments. The OSGi
Alliance has <a href="http://www.osgi.org/resources/spec_download.asp">defined</a>
a dynamically extensible framework for this purpose, which supports the
dynamic deployment and execution of components, called bundles. The
OSGi framework provides an excellent platform for building dynamically
extensible applications.
</p>
                                                                                
<p>
OBR has two two main goals:
</p>
                                                                                
<ul>
  <li>Provide a repository of useful and/or didactic bundles that can
      be easily deployed into existing OSGi frameworks.
  </li>
  <li>Promote a community effort around bundle creation by increasing
      the visibility of individual bundles.
  </li>
</ul>
                                                                                
<p>
The main approach that OBR uses to achieve these goals is to provide
simple access mechanisms for the bundles in the repository. Consequently,
there are multiple ways to access the repository bundles:
</p>
                                                                                
<ul>
  <li><b><i>Web access</i></b> - all bundles in the OBR repository are
      accessible via this web page.
  </li>
  <li><b><i>Programmatic access</i></b> - the
      <a href="http://oscar-osgi.sf.net/repo/bundlerepository/">Bundle
      Repository</a> bundle provides an OSGi service for dynamically
      deploying repository bundles and the transitive closure of their
      deployment dependencies into an executing OSGi framework.
  </li>
  <li><b><i>Interactive access</i></b> - the
      <a href="http://oscar-osgi.sf.net/repo/bundlerepository/">Bundle
      Repository</a> bundle also provides a shell command for Oscar's
      <a href="http://oscar-osgi.sf.net/repo/shell/">Shell Service</a>
      bundle, so if you are using Oscar you can interactively type
      OBR commands at Oscar's shell prompt. Additionally, the
      <a href="http://oscar-osgi.sf.net/repo/shellplugin/">Shell Plugin</a>
      bundle provides a GUI interface for the
      <a href="http://oscar-osgi.sf.net/repo/shellgui/">Shell GUI</a> bundle.
  </li>
</ul>
                                                                                
<p>
While many of the bundles were developed for the
<a href="http://oscar.objectweb.org">Oscar</a> OSGi
framework implementation, they should work with other implementations,
such as <a href="http://www.knopflerfish.org">Knopflerfish</a>.
</p>

<a name="bundles"></a>
<h2>Bundles</h2>
                                                                                
<p>
The following is a complete list of all bundles in the OBR
repository. Not all bundles are hosted on this site and individual
bundles have varying licenses; please consult the individual bundle
documentation for details. The source for these bundles is an excellent
way to learn how to implement bundles, but you may also want
to review the <a href="http://oscar-osgi.sf.net/tutorial/">bundle
tutorial</a>.
</p>
                                                                                
<ul>
  <xsl:for-each select="bundles/bundle">
  <xsl:sort select="bundle-name"/>
    <li><b><xsl:value-of select="bundle-name"/></b>
        ( <a href="{bundle-docurl}">docs</a> |
          <a href="{bundle-updatelocation}">bundle</a> |
          <a href="{bundle-sourceurl}">source</a> )
        - <xsl:value-of select="bundle-description"/>
    </li>
  </xsl:for-each>
</ul>

<a name="submit"></a>
<h2>Submitting Bundles</h2>
                                                                                
<p>
Currently, there is no automated way to submit a bundle to OBR. To
submit a bundle to OBR, send its meta-data, encoded in the XML
format used by OBR, to <a href="mailto:heavy@ungoverned.org">Richard S. Hall</a>;
see the <a href="http://oscar-osgi.sf.net/repo/repository.xml">repository.xml</a>
for examples. Refer to the
<a href="http://oscar-osgi.sourceforge.net/repo/bundlerepository/#packaging">bundle
source packaging</a> section of the Bundle Repository bundle documentation.
In the future, tools could be developed to simplify this
process and make it possible to generate bundle manifest and OBR meta-data
formats.
</p>
                                                                                
<p>
<b><i>Note:</i></b> It is not necessary to physically host your bundle
on this site. It is possible to just add your bundle's meta-data to OBR
and have the meta-data URLs point to your own host.
</p>

<a name="contact"></a>
<h2>Contact</h2>
                                                                                
<p>
Any questions? Want to include your bundle? Want to contribute?
Contact <a href="mailto:heavy@ungoverned.org">Richard S. Hall</a>.
</p>
                                                                                
<h2>Contributors</h2>
                                                                                
<p>
<a href="mailto:robw@softsell.com">Rob Walker</a> from <a href="http://www.ascert.com/">Ascert</a>.
<br/>
<a href="mailto:stephane.frenot@insa-lyon.fr">Stephane Frenot</a> from <a href="http://ares.insa-lyon.fr/~sfrenot/">l'INSA de Lyon</a>.
<br/>
<a href="mailto:humberto.cervantes@imag.fr">Humberto Cervantes</a> from <a href="http://www-adele.imag.fr/~cervante">LSR-IMAG</a>.
<br/>
<a href="mailto:didier.donsez@imag.fr">Didier Donsez</a> from <a href="http://www-adele.imag.fr/~donsez">LSR-IMAG</a>.
<br/>
<a href="mailto:stephane.chomat@imag.fr">Stephane Chomat</a> from LSR-IMAG.
</p>

<p>
<a href="http://sourceforge.net">
<img src="http://sourceforge.net/sflogo.php?group_id=30578" border="0" alt="SourceForge Logo"/>
</a>
</p>

</font>
</body>
</html>

</xsl:template>

</xsl:stylesheet>
