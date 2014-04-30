.. _countmin-sketch:

Count-min sketch
================

A Count-min sketch is a probabilistic data structure used for summarizing
streams of data in sub-linear space.

It works as follows: let (eps, delta) be two parameters that describe the
confidence in our error estimates, and let d = ceil(ln 1/delta)
and w = ceil(e / eps). Then:

- Take d pairwise independent hash functions h_i, each of which maps
  onto the domain [0, w - 1].
- Create a 2-dimensional table of counts, with d rows and w columns,
  initialized with all zeroes.
- When a new element x arrives in the stream, update the table of counts
  by setting counts[i, h_i[x]] += 1, for each 1 <= i <= d.
- (Note the rough similarity to a Bloom filter.)

As an example application, suppose you want to estimate the number of
times an element x has appeared in a data stream so far.
The Count-Min sketch estimate of this frequency is

.. math::

  min_i { counts[i, h_i[x]] }

With probability at least 1 - delta, this estimate is within eps * N
of the true frequency (i.e., true frequency <= estimate <= true frequency + eps * N),
where N is the total size of the stream so far.

See http://www.eecs.harvard.edu/~michaelm/CS222/countmin.pdf for technical details,
including proofs of the estimates and error bounds used in this implementation.


Using Count-min sketch
----------------------

Usage of the CMS is similar to using the HyperLogLog or BloomFilter.  Basically you need to create an implicit monoid,
wrap your values in a ``CMS`` object and then add the ``CMS`` instances together.  You can query the final ``CMS``
for things like the set of "heavy-hitters" (most frequently occurring items).

.. includecode:: ../code/CountMinSketch.scala#example


Choosing eps and delta
----------------------

The CMS monoid takes 2 parameters, ``eps`` and ``delta``, which bound the error of each query estimate:

* **eps** bound on the query error
* **delta** bound on the probability that a query estimate does not lie within some small interval (an interval that
  depends on *eps*) around the truth

Errors in answering queries (e.g., how often has element x appeared in the stream described by the sketch?)
are often of the form **with probability p >= 1 - delta, the estimate is close to the truth by some factor depending on eps**.
