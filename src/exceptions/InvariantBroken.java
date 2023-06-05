package exceptions;

/**
 * Is an exception that is thrown when invariants of the program are violated:
 *          >>> coordinates are intended to be positive or zero.
 *
 *  Class was initially designed for development purposes but now its primarily redundant since all invariants are upheld.
 *  Is still kept in case application is extended.
 */
public class InvariantBroken extends Exception{

}

