# VoluminousPaginationSkin Introduction
A JavaFX skin for the Pagination control for use when there are too many pages to traverse easily using the
default skin. It augments the default skin with buttons to jump to the first and last entries, as well as
fast-forward and rewind buttons to jump forward as backward.

## Description
Here is an example that approximates how the VoluminousPaginationSkin compares to the default PaginationSkin
### Default PaginationSkin
:arrow_backward:|121|122|123|124|125|:arrow_forward:
### VoluminousPaginationSkin
:previous_track_button:|:rewind:|:arrow_backward:|121|122|123|124|125|:arrow_forward:|:fast_forward|:next_track_button:

## Important Note
If you are contemplating using this, you should consider reworking your design. The JavaFX Pagination control 
is not realy suitable for large quantities of data.

## Versions
### Version 1.0.0
Version 1.0.0 supports Java 8 with JavaFX bundled in it. It has been tested with version 1.8.0\_242 of the Zulu distribution of
OpenJDK on Windows, and with a similar version on 32-bit CentOS.
