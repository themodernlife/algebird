.. _hyperloglog:

HyperLogLog
===========

The HyperLogLog is data structure capable of estimating the cardinality (number of distinct values) of a list of elements.
It can do this in a very space efficient way.

You will need to interact with a few different objects

- ``HyperLogLogMonoid`` - the monoid
- ``HLL`` - the actual instances that wrap a given value


Creating the monoid
-------------------

To get started, create a new HyperLogLog monoid.

.. includecode:: ../code/HyperLogLog.scala#create-monoid

The HyperLogLog monoid adds instances of ``HLL``. ``HLL`` is basially a wrapper type
that you can create using either the ``apply`` or ``create`` method on the monoid (``create``
is just an alias for ``apply``).  Let's say we want to count the distinct number of user IDs we've seen so far.
We would use something like:

.. includecode:: ../code/HyperLogLog.scala#create-hll

Note that in the above we take advantage of an implicit view from ``String`` to  ``Array[Byte]`` becuase ``HLL`` instances
can only be created over ``Array[Byte]``.

Estimating cardinalities
------------------------

Once we've added up a few ``HLL`` instances, we can get an estimate over the number of *distinct* elements we've seen
so far.

.. includecode:: ../code/HyperLogLog.scala#distinct

If you're interested in additional details, such as error bounds, you can use

.. includecode:: ../code/HyperLogLog.scala#approximate



Miscellaneous information
-------------------------


Choosing the number of bits
~~~~~~~~~~~~~~~~~~~~~~~~~~~

HyperLogLog cardinality estimates are based on probability and include an error estimate.  You can tune the expected error
by adjusting the number of bits used when hashing each element. More bits means larger object storage size.

The below example demonstrates how different HyperLogLog configurations give different results depending on their
configured error rate.

.. includecode:: ../code/HyperLogLog.scala#num-bits


The error is :math:`\frac{1.04}{\sqrt{2^{bits}}}`, so you want something like 12 bits for a 1% error.  See the table below
for information on trade-off between error and object size.

====  =============  =========================
Bits  Error percent  Object size
====  =============  =========================
8     6.5%           :math:`2^{8}` = 256 bytes
9     5.0%           :math:`2^{9}` = 512 bytes
10    3.3%           :math:`2^{10}` = 1 kb
11    2.3%           :math:`2^{11}` = 2 kb
12    1.6%           :math:`2^{12}` = 4 kb
13    1.1%           :math:`2^{13}` = 8 kb
14    0.8%           :math:`2^{14}` = 16 kb
====  =============  =========================


Serializing to/from Array[Bytes]
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

You can serialize HyperLogLog instances to and from ``Array[Byte``.  This can be useful for e.g. storing objects in an
online database to keep track of the number of distinct values seen over a given time window.

.. includecode:: ../code/HyperLogLog.scala#serialize

