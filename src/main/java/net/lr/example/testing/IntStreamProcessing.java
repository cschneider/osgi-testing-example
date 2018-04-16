package net.lr.example.testing;

import java.util.stream.Stream;

/**
 * Just an interface to be implemented by an ad hoc class
 */
public interface IntStreamProcessing {
    Stream<Integer> process(Stream<Integer> stream);
}
