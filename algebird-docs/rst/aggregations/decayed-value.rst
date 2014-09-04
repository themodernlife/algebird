.. _decayed-value:

Decayed value
=============

A Decayed Value with a half-life of, say, 5-days is similar to a 5-day moving average.  However, instead of storing
the last :math:`N` points (which requires memory :math:`O(N)`) a decayed value only requires :math:`O(1)` memory.


To use DecayedValue:

.. includecode:: ../code/DecayedValue.scala#usage


Decayed value as a moving average
---------------------------------

Let's compare the cumulative, a simple 5-day moving average, and a decayed value with a half-life of 5 days.
We'll use the following data set of the S&P 500 daily closing prices:

==========  =====
Date	    Close
==========  =====
2014-04-01	99.74
2014-04-02	100.25
2014-04-03	99.81
2014-04-04	96.61
2014-04-07	95.09
2014-04-08	95.96
2014-04-09	98.42
2014-04-10	96.28
2014-04-11	96.2
2014-04-14	96.49
2014-04-15	97.36
2014-04-16	99.74
2014-04-17	99.94
2014-04-21	99.39
2014-04-22	99.8
2014-04-23	102.31
2014-04-24	101.1
2014-04-25	99.17
2014-04-28	97.81
2014-04-29	98.85
2014-04-30	101.84
==========  ======




Half-life
---------

To understand decayed values, we start with the observation that the *rate of change* of a substance (or quantity)
:math:`y` at a given time :math:`t` is proportional to the amount of the substance at time :math:`t`.  In math:

.. math::

  \frac{dy}{dt} = -ky

where :math:`k` is some constant.  How is :math:`k` related to the half-life?

Solving the above differential equation yields the solution :math:`y(t) = {y_0}{e^{-kt}}`.
Now we can go about solving for the half-life, :math:`\tau`.

.. math::

    \frac{1}{2}y_0 = y(\tau)

    \frac{1}{2}y_0 = {y_0}{e^{-k\tau}}

    \frac{1}{2}    = e^{-k\tau}

    2 = e^{k\tau}

    \ln(2) = \ln(e^{k\tau}) = k\tau

    \tau = \frac{\ln(2)}{k}

    k = \frac{\ln(2)}{\tau}

Said another way, the *rate constant* :math:`k` is related to the half-life :math:`\tau`.


Additional information
----------------------

You can read more about different kinds of averages at http://donlehmanjr.com/Science/03%20Decay%20Ave/032.htm.

