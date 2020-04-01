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
ersion 1.0.0 supports Java 8 with JavaFX bundled in it. It has been tested with version 8.44.0.13 of the Zulu distribution of
OpenJDK on Windows, and with version 8.42.0.23 on 32-bit CentOS.
* [Javadoc](https://javadoc.io/doc/io.github.jmcleodfoss/voluminouspaginationskin)
* [Download from Sonatype OSS Maven Repository](https://repo1.maven.org/maven2/io/github/jmcleodfoss/voluminouspaginationskin/1.0.0T/)

### Future
A future version 2.0.0 will support Java 9 and later with JavaFX as an external library. If this is relatively easy to do (i.e. if the
default skin still works similarly to the Java 8 one), it should be available by May 2020.
