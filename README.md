![MIT License](https://img.shields.io/badge/license-MIT-green) 
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/8df0d3a8d5ba44feb54f8cab722d04ab)](https://www.codacy.com/gh/Jmcleodfoss/VoluminousPaginationSkin/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Jmcleodfoss/VoluminousPaginationSkin&amp;utm_campaign=Badge_Grade)
![Codacy Security Scan](https://github.com/Jmcleodfoss/VoluminousPaginationSkin/workflows/Codacy%20Security%20Scan/badge.svg) 
![CodeQL](https://github.com/Jmcleodfoss/VoluminousPaginationSkin/workflows/CodeQL/badge.svg)
![Xanitizer Security Analysis](https://github.com/Jmcleodfoss/VoluminousPaginationSkin/workflows/Xanitizer%20Security%20Analysis/badge.svg) 

# VoluminousPaginationSkin Introduction
A JavaFX skin for the Pagination control for use when there are too many pages to traverse easily using the
default skin. It augments the default skin with buttons to jump to the first and last entries, as well as
fast-forward and rewind buttons to jump forward as backward.

## Description
Here is an example that approximates how the VoluminousPaginationSkin compares to the default PaginationSkin
### Default PaginationSkin
:arrow_backward: 121 122 123 124 125 :arrow_forward:
### VoluminousPaginationSkin
:previous_track_button: :rewind: :arrow_backward: 121 122 123 124 125 :arrow_forward: :fast_forward: :next_track_button:

## Important Note
If you are contemplating using this, you should consider reworking your design. The JavaFX Pagination control 
is not really suitable for large quantities of data, even with this hack to help navigate.

## Versions
### Version 1.0.0T (the "T" is an accident; the next version on this branch will be 1.0.1 with no "T")
Version 1.0.0 supports Java 8 with JavaFX bundled in it. It has been tested with version 8.44.0.13 of the Zulu distribution of
OpenJDK on Windows, and with version 8.42.0.23 on 32-bit CentOS.
*   [Javadoc](https://javadoc.io/doc/io.github.jmcleodfoss/voluminouspaginationskin/1.0.0T)
*   [Download from Sonatype OSS Maven Repository](https://repo1.maven.org/maven2/io/github/jmcleodfoss/voluminouspaginationskin/1.0.0T/)

### Version 1.0.1
This version is for Java 8.
Fix typos in version name and in package description; clean up build dependencies.
*   [Javadoc](https://javadoc.io/doc/io.github.jmcleodfoss/voluminouspaginationskin/1.0.1)
*   [Download from Sonatype OSS Maven Repository](https://repo1.maven.org/maven2/io/github/jmcleodfoss/voluminouspaginationskin/1.0.1/)

### Version 2.0.0
This version is for JavaFX 9 and higher
*   [Javadoc](https://javadoc.io/doc/io.github.jmcleodfoss/voluminouspaginationskin/2.0.0)
*   [Download from Sonatype OSS Maven Repository](https://repo1.maven.org/maven2/io/github/jmcleodfoss/voluminouspaginationskin/2.0.0/)
