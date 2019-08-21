# OSGi testing tips and tricks

[![Build Status](https://travis-ci.org/cschneider/osgi-testing-example.svg?branch=master)](https://travis-ci.org/cschneider/osgi-testing-example)

In this repo I show some techniques to make testing your OSGi projects a lot simpler.

## Import into eclipse

Use the m2e plugin that is already preinstalled in eclipse.

```
File -> Import -> Existing Maven Projects -> <top of the git checkout>
```

## Mock based tests outside OSGi using mockito

Mockito provides some very nice features for testing DS components that use the popular field injection pattern

```
@Reference
private Mydependency;
```

See the package [net.lr.example.testing](src/test/java/net/lr/example/testing) for some hints how to use mockito 
to inject dependencies into DS components. This covers mock, spy and capturing arguments.

## Creating type safe configs on the fly

In declarative services type safe configs are defined as annotations. Unfortunately it is quite hard 
to instantiate or mock an annotation inside a test.

[net.lr.example.testing.PrimeCalculatorServletTest](src/test/java/net/lr/example/testing/PrimeCalculatorServletTest.java) shows how to use the new OSGi converter spec to create the config
annotation while retaining defaults defined in the annotation.


## Add hamcrest matchers and awaitility to pax exam tests

Hamcrest provides a lot of nice and powerful matchers to junit. The example below shows how to compare
elements of a complete array. Similarly you can assert over whole java beans.

```
Stream<Integer> primes = new PrimeCalculator().until(3);
assertThat(primes.toArray(), arrayContaining(2,3));
```

Awaitility is another gem you should not miss. It allows to repeat an operation until it succeed or a timeout occurs.
It also can nicely ignore exceptions and only return the last exception. This is ideal if you e.g. have an external rest service that will come up asynchronously to your test. Often this is covered with a sleep that has to be timed carefully to not be too short (causes test failures) or too long (wastes time). Awaitility solves this in a very nice way.

See [BaseTest.java](src/test/java/net/lr/example/testing/osgi/BaseTest.java) for how to add hamcrest and Awaitility
as bundles.

See [net.lr.example.testing.osgi.PrimeServletTest](src/test/java/net/lr/example/testing/osgi/PrimeServletTest.java) for
an example of how to use Awaitility.

## Create your OSGi test setup seamlessly using pax exam

Most pax exam based tests require the project to first be built using maven or even need to run in their own project.
Additionally in many cases the tests run in an additional project. So you need to setup remote debugging to step through your code.

I show a way to run pax exam tests inside the same maven project in a way that does not require the build tool to run.
The tests also run inside the junit process.

So your workflow simply is edit, save, debug - like in plain java code. 

This works by doing two things:

1. Use the bnd-maven-plugin which is integrated with the m2e build and will update the OSGi meta data on the fly
2. Use bundle("reference:file:target/classes/") to refer to the exploded view of your bundle contents

See [BaseTest.java](src/test/java/net/lr/example/testing/osgi/BaseTest.java) for how to setup the pax exam config.

Try this by setting a breakpoint and running debug in eclipse from any of the tests in [src/test/java/net/lr/example/testing/osgi].

## Create bundles including DS components on the fly using TinyBundles

It is an old story that TinyBundles can create bundles on the fly but now it learned some new tricks.

Use [BndDSOptions.dsBundle] to create a bundle using bnd by just adding some annotated classes. Bnd will auto create imports, exports and DS component meta data. 

The only "limitation" is that you can not simply @Inject such a component into your test as pax exam tends to embed this class inside the test probe. So it will see a different class. So this approach works best if your ds component either does not need to be directly injected into the test or if it implements an interface that is already defined in the main code.

## Switch the pax exam logging to logback

Some people do not like pax logging. Luckily it is not very difficult to switch the pax exam logging backend to logback.

See [BaseTest.java](src/test/java/net/lr/example/testing/osgi/BaseTest.java) for how to exchange pax logging with logback.
